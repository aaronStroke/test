package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.CashRegister}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashRegisterDto implements Serializable {
    private Long id;
    private LocalDateTime openingDate;
    private LocalDateTime closeDate;
    @NotNull(message = "El saldo inicial es requerido")
    private BigDecimal initialAmount;
    private BigDecimal finalAmount;
    private UserDto user;
}
