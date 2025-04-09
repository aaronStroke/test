package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SaleDto;

import java.math.BigDecimal;

public interface SaleService {

    SaleDto registerSale(SaleDto saleDto);

    Pagination<SaleDto> findSalesByDate(String date, int page, int size);

    Long getTotalSalesByDate(String date);

    BigDecimal getTotalSalesAmountByDate(String date);

}
