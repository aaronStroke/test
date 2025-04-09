package mx.loal.pharmacy_admin_api.payload.request;

import java.math.BigDecimal;

public record OpenCashRegister(
    BigDecimal initialAmount,
    Long userId
) {
}
