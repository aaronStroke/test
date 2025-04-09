package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.loal.pharmacy_admin_api.model.AgeGroup;

import java.io.Serializable;

/**
 * DTO for {@link AgeGroup}
 */
@Setter
@Getter
public class AgeGroupDto implements Serializable {
    private Long id;
    @Size(message = "El nombre debe contener entre {min} y {max} carácteres", min = 1, max = 50)
    @NotBlank(message = "El nombre es requerido")
    private String name;
}
