package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.api.SubstanceAPI;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SubstanceDto;
import mx.loal.pharmacy_admin_api.service.SubstanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SubstanceController implements SubstanceAPI {

    private final SubstanceService substanceService;

    @Override
    public ResponseEntity<Pagination<SubstanceDto>> findAll(int page, int size) {
        var response = substanceService.findAll(page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SubstanceDto>> search(String query) {
        var response = substanceService.search(query);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SubstanceDto> findById(Long substanceId) {
        var response = substanceService.findById(substanceId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SubstanceDto> save(SubstanceDto substance) {
        var response = substanceService.save(substance);
        return ResponseEntity
            .status(CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<SubstanceDto> update(Long substanceId, SubstanceDto substance) {
        var response = substanceService.update(substanceId, substance);
        return ResponseEntity.ok(response);
    }
}
