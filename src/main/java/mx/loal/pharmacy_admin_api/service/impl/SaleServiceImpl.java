package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.NotFoundException;
import mx.loal.pharmacy_admin_api.model.AntibioticSale;
import mx.loal.pharmacy_admin_api.model.Sale;
import mx.loal.pharmacy_admin_api.model.SaleDetail;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SaleDetailDto;
import mx.loal.pharmacy_admin_api.payload.SaleDto;
import mx.loal.pharmacy_admin_api.repository.*;
import mx.loal.pharmacy_admin_api.service.SaleService;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {

    private final AntibioticSaleRepository antibioticSaleRepository;
    private final CashRegisterRepository cashRegisterRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public SaleDto registerSale(SaleDto saleDto) {

        var cashRegister = cashRegisterRepository.findById(saleDto.getCashRegister().getId())
                .orElseThrow(() -> new NotFoundException("El registro de la caja registradora no existe"));

        var salesDetail = saleDto
            .getSalesDetail()
            .stream()
            .map(this::convertSaleDetailToEntity)
            .toList();

        var sale = Sale
            .builder()
            .cashRegister(cashRegister)
            .date(LocalDateTime.now())
            .payType(saleDto.getPayType())
            .total(saleDto.getTotal())
            .discount(saleDto.getDiscount())
            .cashReturn(saleDto.getCashReturn())
            .build();

        // Se recorre el detalle de la venta
        salesDetail.forEach(sd -> {

            sd.setSale(sale);

            // Se valida que en el detalle, sea un producto en la venta
            if (Objects.nonNull(sd.getProduct())) {
                // Se toma el id del producto
                var productId = sd.getProduct().getId();
                // Se toma realiza la resta del stock del producto
                var remainingStock = sd.getProduct().getStock() - sd.getQuantity();
                // Se realiza la actualización del stock del producto
                productRepository.updateStockByProductId(productId, remainingStock);

                // Se valída si el producto vendido es un medicamento y es antibiótico
                if (sd.getProduct().getCategory().equals(ProductCategory.MEDICINE) &&
                        sd.getProduct().getAntibiotic()) {

                    var antibioticSale = AntibioticSale
                        .builder()
                        .saleDateTime(LocalDateTime.now())
                        .product(sd.getProduct())
                        .quantity(sd.getQuantity())
                        .user(cashRegister.getUser())
                        .build();

                    antibioticSaleRepository.save(antibioticSale);
                }
            }
        });

        sale.setSalesDetail(salesDetail);

        var savedSale = saleRepository.save(sale);

        cashRegister.setFinalAmount(cashRegister.getFinalAmount().add(sale.getTotal()));
        cashRegisterRepository.save(cashRegister);

        return convertToDTO(savedSale);
    }

    @Override
    public Pagination<SaleDto> findSalesByDate(String date, int page, int size) {

        var startDate = LocalDateTime.of(
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalTime.MIN
        );

        var endDate = LocalDateTime.of(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.MAX
        );

        var result = saleRepository.findAllByDateBetween(startDate, endDate, PageRequest.of(page, size));

        var sales = result
            .stream()
            .map(this::convertToDTO)
            .toList();

        return Pagination
            .<SaleDto>builder()
            .empty(result.isEmpty())
            .first(result.isFirst())
            .last(result.isLast())
            .last(result.isLast())
            .number(result.getNumber())
            .numberOfElements(result.getNumberOfElements())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .content(sales)
            .build();
    }

    @Override
    public Long getTotalSalesByDate(String date) {

        var startDate = LocalDateTime.of(
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalTime.MIN
        );

        var endDate = LocalDateTime.of(
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalTime.MAX
        );

        return saleRepository.countSaleByDateBetween(startDate, endDate);
    }

    @Override
    public BigDecimal getTotalSalesAmountByDate(String date) {

        var startDate = LocalDateTime.of(
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalTime.MIN
        );

        var endDate = LocalDateTime.of(
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalTime.MAX
        );

        return saleRepository.getTotalSalesAmountByDate(startDate, endDate);
    }

    private Sale convertToEntity(SaleDto saleDto) {
        return modelMapper.map(saleDto, Sale.class);
    }

    private SaleDto convertToDTO(Sale sale) {
        return modelMapper.map(sale, SaleDto.class);
    }

    private SaleDetail convertSaleDetailToEntity(SaleDetailDto saleDetailDto) {
        return modelMapper.map(saleDetailDto, SaleDetail.class);
    }

    private SaleDetailDto convertSaleDetailToDTO(SaleDetail saleDetail) {
        return modelMapper.map(saleDetail, SaleDetailDto.class);
    }
}
