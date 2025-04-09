package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.TurnDto;
import mx.loal.pharmacy_admin_api.payload.request.ChangeTurnStatus;
import mx.loal.pharmacy_admin_api.utils.enums.TurnStatus;

import java.time.LocalDate;
import java.util.List;

public interface TurnService {

    TurnDto addTurn(TurnDto turn);

    TurnDto updateTurnStatus(Long turnId, ChangeTurnStatus status);

    List<TurnDto> getAllTurns();

    List<TurnDto> allTurns(Long typeConsultId);

    List<TurnDto> getHistoryTurns(LocalDate date);

    TurnDto getCurrentAttendingTurnNullSafe();

    TurnDto getCurrentAttendingTurn();

}
