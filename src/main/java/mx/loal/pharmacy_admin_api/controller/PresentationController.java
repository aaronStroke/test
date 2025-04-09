package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.PresentationAPI;
import mx.loal.pharmacy_admin_api.payload.PresentationDto;
import mx.loal.pharmacy_admin_api.service.PresentationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PresentationController implements PresentationAPI {

    private final PresentationService presentationService;

    @Override
    public ResponseEntity<List<PresentationDto>> findAll() {
        var response = presentationService.findAll();
        return ResponseEntity.ok(response);
    }
}
