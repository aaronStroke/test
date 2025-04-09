package mx.loal.pharmacy_admin_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import mx.loal.pharmacy_admin_api.utils.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table
@Entity(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @NotBlank(message = "El primer nombre es requerido")
    @Size(min = 1, max = 50, message = "El primero nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 50)
    private String firstName;

    @Size(max = 50, message = "El segundo nombre debe contener máximo {max} carácteres")
    @Column(length = 50)
    private String secondName;

    @NotBlank(message = "El apellido paterno es requerido")
    @Size(min = 1, max = 50, message = "El apellido paterno debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "El apellido materno es requerido")
    @Size(min = 1, max = 50, message = "El apellido materno debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 50)
    private String secondLastName;

    @Size(max = 75, message = "El correo debe contener máximo {max} carácteres")
    @Email(message = "El correo no tiene un formato válido")
    @Column(length = 75)
    private String email;

    @Size(max = 15, message = "El teléfono debe contener máximo {max} carácteres")
    @Column(length = 15)
    private String phoneNumber;

    @Size(max = 50, message = "La cédula profesional debe contener máximo {max} carácteres")
    @Column(length = 50)
    private String professionalLicense;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

}
