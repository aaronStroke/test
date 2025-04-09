package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.api.MedicalConsultTypeAPI;
import mx.loal.pharmacy_admin_api.payload.MedicalConsultTypeDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.service.MedicalConsultTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MedicalConsultTypeController implements MedicalConsultTypeAPI {

    private final MedicalConsultTypeService medicalConsultTypeService;

    @Override
    public ResponseEntity<Pagination<MedicalConsultTypeDto>> findAll(int page, int size) {
        var response = medicalConsultTypeService.findAll(page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<MedicalConsultTypeDto>> getCatalog() {
        var response = medicalConsultTypeService.getMedicalConsultTypes();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MedicalConsultTypeDto> findById(Long medicalConsultTypeId) {
        var response = medicalConsultTypeService.findById(medicalConsultTypeId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MedicalConsultTypeDto> save(MedicalConsultTypeDto medicalConsultType) {
        var response = medicalConsultTypeService.save(medicalConsultType);
        return ResponseEntity
            .status(CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<MedicalConsultTypeDto> update(Long medicalConsultTypeId, MedicalConsultTypeDto medicalConsultType) {
        var response = medicalConsultTypeService.update(medicalConsultTypeId, medicalConsultType);
        return ResponseEntity.ok(response);
    }
}
