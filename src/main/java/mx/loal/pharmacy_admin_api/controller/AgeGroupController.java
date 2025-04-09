package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.AgeGroupAPI;
import mx.loal.pharmacy_admin_api.payload.AgeGroupDto;
import mx.loal.pharmacy_admin_api.service.AgeGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AgeGroupController implements AgeGroupAPI {

    private final AgeGroupService ageGroupService;

    @Override
    public ResponseEntity<List<AgeGroupDto>> findAll() {
        var response = ageGroupService.getAllAgeGroups();
        return ResponseEntity.ok(response);
    }
}
