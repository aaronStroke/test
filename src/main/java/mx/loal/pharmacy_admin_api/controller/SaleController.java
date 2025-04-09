package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.SaleAPI;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SaleDto;
import mx.loal.pharmacy_admin_api.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class SaleController implements SaleAPI {

    private final SaleService saleService;

    @Override
    public ResponseEntity<SaleDto> registerSale(SaleDto saleDto) {
        var response = saleService.registerSale(saleDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<Pagination<SaleDto>> findSalesByDate(String date, int page, int size) {
        var response = saleService.findSalesByDate(date, page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BigDecimal> getTotalSalesAmountByDate(String date) {
        var response = saleService.getTotalSalesAmountByDate(date);
        return ResponseEntity.ok(response);
    }
}
