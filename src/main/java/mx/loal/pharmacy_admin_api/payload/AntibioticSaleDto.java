package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.AntibioticSale}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AntibioticSaleDto implements Serializable {
    private Long id;
    @NotNull(message = "El producto es requerido")
    private ProductDto product;
    @NotNull(message = "La cantidad es requerida")
    private Integer quantity;
    @NotNull(message = "La fecha y hora de la venta es requerida")
    private LocalDateTime saleDateTime;
    private UserDto user;
}
