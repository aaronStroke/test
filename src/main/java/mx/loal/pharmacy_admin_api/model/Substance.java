package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table
@Entity(name = "substance")
public class Substance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substance_id")
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 1, max = 100, message = "El nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Boolean active;

    @PrePersist
    public void prePersist() {
        active = true;
    }

}
