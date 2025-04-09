package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.loal.pharmacy_admin_api.utils.enums.Role;

import java.io.Serializable;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.User}
 */
@Getter
@Setter
public class UserDto implements Serializable {
    private Long id;
    private String username;
    @Size(message = "El primero nombre debe contener entre {min} y {max} carácteres", min = 1, max = 50)
    @NotBlank(message = "El primer nombre es requerido")
    private String firstName;
    @Size(message = "El segundo nombre debe contener máximo {max} carácteres", max = 50)
    private String secondName;
    @Size(message = "El apellido paterno debe contener entre {min} y {max} carácteres", min = 1, max = 50)
    @NotBlank(message = "El apellido paterno es requerido")
    private String lastName;
    @Size(message = "El apellido materno debe contener entre {min} y {max} carácteres", min = 1, max = 50)
    @NotBlank(message = "El apellido materno es requerido")
    private String secondLastName;
    @Size(message = "El correo debe contener máximo {max} carácteres", max = 75)
    @Email(message = "El correo no tiene un formato válido")
    private String email;
    @Size(message = "El teléfono debe contener máximo {max} carácteres", max = 15)
    private String phoneNumber;
    @Size(message = "La cédula profesional debe contener máximo {max} carácteres", max = 50)
    private String professionalLicense;
    private Role role;
}
