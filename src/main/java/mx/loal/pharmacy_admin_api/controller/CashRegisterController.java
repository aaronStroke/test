package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.CashRegisterAPI;
import mx.loal.pharmacy_admin_api.payload.CashRegisterDto;
import mx.loal.pharmacy_admin_api.payload.request.OpenCashRegister;
import mx.loal.pharmacy_admin_api.service.CashRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CashRegisterController implements CashRegisterAPI {

    private final CashRegisterService cashRegisterService;

    @Override
    public ResponseEntity<CashRegisterDto> getCurrentCashRegister() {
        var response = cashRegisterService.getCurrentCashRegister();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CashRegisterDto> openCashRegister(OpenCashRegister openCashRegister) {
        var response = cashRegisterService.openCashRegister(openCashRegister);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }
}
