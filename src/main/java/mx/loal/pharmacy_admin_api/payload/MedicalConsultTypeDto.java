package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.MedicalConsultType}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalConsultTypeDto implements Serializable {
    private Long id;
    @Size(message = "El nombre debe contener entre {min} y {max} carácteres", min = 1, max = 100)
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @Size(min = 1, max = 10, message = "El identificador debe contener entre {min} y {max} carácteres")
    @NotBlank(message = "El identificador es requerido")
    private String identifier;
    @Size(message = "La descripción debe contener entre {min} y {max} carácteres", min = 1, max = 200)
    @NotBlank(message = "La descripción es requerida")
    private String description;
    @NotNull(message = "El costo del tipo de consulta es requerido")
    private BigDecimal cost;
    @NotNull(message = "La prioridad es requerida")
    private Integer priority;
}
