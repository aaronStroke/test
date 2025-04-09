package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.loal.pharmacy_admin_api.utils.enums.PayType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Sale}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto implements Serializable {
    private Long id;
    private LocalDateTime date;
    @NotNull(message = "El total de la venta es requerido")
    @DecimalMax(value = "99999999.99", message = "El total de la venta no puede ser mayor a {value}")
    @DecimalMin(value = "0.00", message = "El total de la venta no puede ser menor a {value}")
    private BigDecimal total;
    @DecimalMax(value = "99999999.99", message = "El descuento no puede ser mayor a {value}")
    @DecimalMin(value = "0.00", message = "El descuento no puede ser menor a {value}")
    private BigDecimal discount;
    @DecimalMax(value = "99999999.99", message = "El descuento no puede ser mayor a {value}")
    @DecimalMin(value = "0.00", message = "El descuento no puede ser menor a {value}")
    private BigDecimal cashReturn;
    @NotNull(message = "El tipo de pago es requerido")
    private PayType payType;
    @NotNull(message = "La caja que realizó la venta es requerida")
    private CashRegisterDto cashRegister;
    private List<SaleDetailDto> salesDetail;
}
