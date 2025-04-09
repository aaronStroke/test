package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.loal.pharmacy_admin_api.model.Customer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Customer}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.secondName = customer.getSecondName();
        this.lastName = customer.getLastName();
        this.secondLastName = customer.getSecondLastName();
        this.bornDate = customer.getBornDate();
    }

    private Long id;
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
    private LocalDate bornDate;
    private LocalDate createdAt;
}
