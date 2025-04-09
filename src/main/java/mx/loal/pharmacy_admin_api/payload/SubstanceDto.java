package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Substance}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstanceDto implements Serializable {
    private Long id;
    @Size(message = "El nombre debe contener entre {min} y {max} carácteres", min = 1, max = 100)
    @NotBlank(message = "El nombre es requerido")
    private String name;
    private Boolean active;
}
