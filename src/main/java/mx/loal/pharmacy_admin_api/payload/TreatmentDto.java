package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Treatment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto implements Serializable {
    private Long id;
    private Boolean assortment;
    @Size(message = "Las indicaciones deben contener entre {min} y {max} carácteres", min = 1, max = 200)
    @NotBlank(message = "Las indicaciones son requeridas")
    private String indications;
    private ProductDto product;
}
