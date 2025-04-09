package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.MeasurementUnitDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;

import java.util.List;

public interface MeasurementUnitService {

    Pagination<MeasurementUnitDto> findAll(int page, int size);

    List<MeasurementUnitDto> search(String query);

    MeasurementUnitDto findById(Long measurementUnitId);

    MeasurementUnitDto save(MeasurementUnitDto measurementUnit);

    MeasurementUnitDto update(Long measurementUnitId, MeasurementUnitDto measurementUnit);

}
