package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.MeasurementUnitAPI;
import mx.loal.pharmacy_admin_api.payload.MeasurementUnitDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.service.MeasurementUnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MeasurementUnitController implements MeasurementUnitAPI {

    private final MeasurementUnitService measurementUnitService;

    @Override
    public ResponseEntity<Pagination<MeasurementUnitDto>> findAll(int page, int size) {
        var response = measurementUnitService.findAll(page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<MeasurementUnitDto>> search(String query) {
        var response = measurementUnitService.search(query);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MeasurementUnitDto> findById(Long measurementUnitId) {
        var response = measurementUnitService.findById(measurementUnitId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MeasurementUnitDto> save(MeasurementUnitDto measurementUnit) {
        var response = measurementUnitService.save(measurementUnit);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<MeasurementUnitDto> update(Long measurementUnitId, MeasurementUnitDto measurementUnit) {
        var response = measurementUnitService.update(measurementUnitId, measurementUnit);
        return ResponseEntity.ok(response);
    }
}
