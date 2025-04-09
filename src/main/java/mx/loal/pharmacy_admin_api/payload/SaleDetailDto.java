package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.SaleDetail}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetailDto implements Serializable {
    private Long id;
    private ProductDto product;
    private MedicalConsultDto medicalConsult;
    @NotNull(message = "La cantidad es requerida")
    @Min(message = "El valor mínimo de la cantidad es {value}", value = 0)
    @Max(message = "El valor máximo de la cantidad es {value}", value = 999)
    private Integer quantity;
    @NotNull(message = "El precio unitario es requerido")
    private BigDecimal unitPrice;
    @NotNull(message = "El subtotal es requerido")
    private BigDecimal subtotal;
}
