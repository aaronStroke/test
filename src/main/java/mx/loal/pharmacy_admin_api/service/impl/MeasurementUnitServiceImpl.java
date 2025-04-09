package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.DuplicateKeyException;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.model.MeasurementUnit;
import mx.loal.pharmacy_admin_api.payload.MeasurementUnitDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.repository.MeasurementUnitRepository;
import mx.loal.pharmacy_admin_api.service.MeasurementUnitService;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    private final MeasurementUnitRepository measurementUnitRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Pagination<MeasurementUnitDto> findAll(int page, int size) {

        var result = measurementUnitRepository
                .findAllByOrderByNameAsc(PageRequest.of(page, size));

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.MEASUREMENT_UNIT_NOT_FOUND);

        var measurementsUnit = result
                .stream()
                .map(this::convertToDTO)
                .toList();

        return Pagination
            .<MeasurementUnitDto>builder()
            .empty(result.isEmpty())
            .first(result.isFirst())
            .last(result.isLast())
            .last(result.isLast())
            .number(result.getNumber())
            .numberOfElements(result.getNumberOfElements())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .content(measurementsUnit)
            .build();
    }

    @Override
    public List<MeasurementUnitDto> search(String query) {

        var result = measurementUnitRepository.searchMeasurementUnits(query);

        if (result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.MEASUREMENT_UNITS_NOT_FOUND);

        return result
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MeasurementUnitDto findById(Long measurementUnitId) {
        var measurementUnit = measurementUnitRepository.findById(measurementUnitId)
            .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.MEASUREMENT_UNIT_NOT_FOUND));
        return convertToDTO(measurementUnit);
    }

    @Override
    @Transactional
    public MeasurementUnitDto save(MeasurementUnitDto measurementUnit) {

        var exists = measurementUnitRepository.existsByNameIgnoreCase(measurementUnit.getName());

        if (exists)
            throw new DuplicateKeyException(ExceptionMessageConstants.MEASUREMENT_UNIT_ALREADY_EXISTS);

        var newMeasurement = convertToEntity(measurementUnit);

        return convertToDTO(measurementUnitRepository.save(newMeasurement));
    }

    @Override
    @Transactional
    public MeasurementUnitDto update(Long measurementUnitId, MeasurementUnitDto measurementUnit) {

        var existentMeasurementUnit = measurementUnitRepository.findById(measurementUnitId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.MEASUREMENT_UNIT_NOT_FOUND));

        if (!existentMeasurementUnit.getId().equals(measurementUnit.getId()))
            throw new NoContentException(ExceptionMessageConstants.MEASUREMENT_UNIT_UPDATE_CONFLICT);

        existentMeasurementUnit.setName(measurementUnit.getName());
        existentMeasurementUnit.setIdentifier(measurementUnit.getIdentifier());

        measurementUnitRepository.save(existentMeasurementUnit);

        return measurementUnit;
    }

    private MeasurementUnit convertToEntity(MeasurementUnitDto measurementUnitDto) {
        return modelMapper.map(measurementUnitDto, MeasurementUnit.class);
    }

    private MeasurementUnitDto convertToDTO(MeasurementUnit measurementUnit) {
        return modelMapper.map(measurementUnit, MeasurementUnitDto.class);
    }
}
