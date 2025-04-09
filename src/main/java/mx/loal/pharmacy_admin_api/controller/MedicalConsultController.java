package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.MedicalConsultAPI;
import mx.loal.pharmacy_admin_api.payload.MedicalConsultDto;
import mx.loal.pharmacy_admin_api.service.MedicalConsultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MedicalConsultController implements MedicalConsultAPI {

    private final MedicalConsultService medicalConsultService;

    @Override
    public ResponseEntity<MedicalConsultDto> save(Long customerId, MedicalConsultDto medicalConsult) {
        var response = medicalConsultService.save(customerId, medicalConsult);
        return ResponseEntity.ok(response);
    }
}
