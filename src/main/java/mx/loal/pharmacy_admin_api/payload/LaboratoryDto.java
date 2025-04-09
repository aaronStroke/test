package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.loal.pharmacy_admin_api.model.Laboratory;

import java.io.Serializable;

/**
 * DTO for {@link Laboratory}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryDto implements Serializable {
    private Long id;
    @Size(message = "El nombre debe contener entre {min} y {max} carácteres", min = 5, max = 100)
    @NotBlank(message = "El nombre es requerido")
    private String name;
    private Boolean active;

}
