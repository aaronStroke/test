package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.LaboratoryAPI;
import mx.loal.pharmacy_admin_api.payload.LaboratoryDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.service.LaboratoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LaboratoryController implements LaboratoryAPI {

    private final LaboratoryService laboratoryService;

    @Override
    public ResponseEntity<Pagination<LaboratoryDto>> findAll(int page, int size, String query) {
        var response = laboratoryService.findAll(page, size, query);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LaboratoryDto>> search(String query) {
        var response = laboratoryService.search(query);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LaboratoryDto> findById(Long laboratoryId) {
        var response = laboratoryService.findById(laboratoryId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LaboratoryDto> save(LaboratoryDto laboratory) {
        var response = laboratoryService.save(laboratory);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<LaboratoryDto> update(Long laboratoryId, LaboratoryDto laboratory) {
        var response = laboratoryService.update(laboratoryId, laboratory);
        return ResponseEntity.ok(response);
    }
}
