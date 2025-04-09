package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.TurnAPI;
import mx.loal.pharmacy_admin_api.payload.TurnDto;
import mx.loal.pharmacy_admin_api.payload.request.ChangeTurnStatus;
import mx.loal.pharmacy_admin_api.service.TurnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class TurnController implements TurnAPI {

    private final TurnService turnService;

    @Override
    public ResponseEntity<List<TurnDto>> getAllTurns() {
        var response = turnService.getAllTurns();
        return ResponseEntity
            .ok(response);
    }

    @Override
    public ResponseEntity<List<TurnDto>> allTurns(Long typeConsultId) {
        var response = turnService.allTurns(typeConsultId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TurnDto> getAttendingTurn() {
        var response = turnService.getCurrentAttendingTurnNullSafe();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TurnDto> addTurn(TurnDto turn) {
        var response = turnService.addTurn(turn);
        return ResponseEntity
            .status(CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<TurnDto> updateStatus(Long turnId, ChangeTurnStatus status) {
        var response = turnService.updateTurnStatus(turnId, status);
        return ResponseEntity
            .ok(response);
    }

}
