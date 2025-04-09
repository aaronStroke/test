package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.exceptions.RequestException;
import mx.loal.pharmacy_admin_api.model.ActiveSubstance;
import mx.loal.pharmacy_admin_api.model.Product;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.ProductDto;
import mx.loal.pharmacy_admin_api.payload.response.MissingStockProductsCount;
import mx.loal.pharmacy_admin_api.repository.MeasurementUnitRepository;
import mx.loal.pharmacy_admin_api.repository.ProductRepository;
import mx.loal.pharmacy_admin_api.repository.SubstanceRepository;
import mx.loal.pharmacy_admin_api.service.ProductService;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;
import mx.loal.pharmacy_admin_api.utils.validations.NumberValidations;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final SubstanceRepository substanceRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> searchProducts(String query, String barCode) {

        if (StringUtils.isBlank(query) && StringUtils.isBlank(barCode))
            throw new RequestException("Query or bar code is mandatory");

        List<Product> products;

        if (StringUtils.isBlank(query) &&
                NumberValidations.checkIfIsNumber(barCode))
            products = productRepository.searchProductsByBarCode(barCode);
        else
            products = productRepository.searchProductsByQuery(query);

        if (products.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.PRODUCTS_NOT_FOUND);

        return products
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<ProductDto> findAll(int page, int size, String query, ProductCategory category) {

        var result = productRepository.findProducts(PageRequest.of(page, size), query, category);

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.PRODUCTS_NOT_FOUND);

        var products = result
            .stream()
            .map(this::convertToDTO)
            .toList();

        return Pagination
            .<ProductDto>builder()
            .empty(result.isEmpty())
            .first(result.isFirst())
            .last(result.isLast())
            .last(result.isLast())
            .number(result.getNumber())
            .numberOfElements(result.getNumberOfElements())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .content(products)
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.PRODUCT_NOT_FOUND));
        return convertToDTO(product);
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto product) {

        var newProduct = convertToEntity(product);

        var substanceIds = newProduct
            .getActiveSubstances()
            .stream()
            .map(activeSubstance -> activeSubstance.getSubstance().getId())
            .toList();

        var measurementUnitIds = newProduct
            .getActiveSubstances()
            .stream()
            .map(activeSubstance -> activeSubstance.getMeasurementUnit().getId())
            .toList();

        var measurementUnits = measurementUnitRepository.findAllByIds(measurementUnitIds);
        var substances = substanceRepository.findAllByIds(substanceIds);

        for (var activeSubstance : newProduct.getActiveSubstances()) {

            var substance = substances
                .stream()
                .filter(s -> s.getId().equals(activeSubstance.getSubstance().getId()))
                .findAny();

            substance.ifPresent(activeSubstance::setSubstance);

            var measurementUnit = measurementUnits
                .stream()
                .filter(mu -> mu.getId().equals(activeSubstance.getMeasurementUnit().getId()))
                .findAny();

            measurementUnit.ifPresent(activeSubstance::setMeasurementUnit);

            activeSubstance.setProduct(newProduct);
//            newProduct.addActiveSubstance(activeSubstance);
        }

        return convertToDTO(productRepository.save(newProduct));
    }

    @Override
    @Transactional
    public ProductDto update(Long productId, ProductDto product) {

        var existentProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.PRODUCT_NOT_FOUND));

        if (!existentProduct.getId().equals(product.getId()))
            throw new RequestException(ExceptionMessageConstants.LABORATORY_UPDATE_CONFLICT);

        BeanUtils.copyProperties(product, existentProduct);

        existentProduct.getActiveSubstances().forEach((activeSubstance) -> {

            var existsSubstance = existentProduct
                .getActiveSubstances()
                .stream()
                .map(ActiveSubstance::getSubstance)
                .filter(s -> s.getId().equals(activeSubstance.getSubstance().getId()))
                .findAny();

            if (existsSubstance.isEmpty())
                substanceRepository
                    .findById(activeSubstance.getSubstance()
                    .getId())
                    .ifPresent(activeSubstance::setSubstance);

            var existsMeasurementUnit = existentProduct
                    .getActiveSubstances()
                    .stream()
                    .map(ActiveSubstance::getMeasurementUnit)
                    .filter(mu -> mu.getId().equals(activeSubstance.getMeasurementUnit().getId()))
                    .findAny();

            if (existsMeasurementUnit.isEmpty())
                measurementUnitRepository
                    .findById(activeSubstance.getSubstance()
                    .getId())
                    .ifPresent(activeSubstance::setMeasurementUnit);

            activeSubstance.setProduct(existentProduct);
        });

        productRepository.save(existentProduct);

        return product;
    }

    @Override
    public MissingStockProductsCount getMissingStockProductsCount(Integer count) {
        return productRepository.countAllByStockIsLessThanEqual(count);
    }

    private Product convertToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    private ProductDto convertToDTO(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

}
