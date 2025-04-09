package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "laboratory")
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laboratory_id")
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 5, max = 100, message = "El nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 100)
    private String name;

    private Boolean active;

    @PrePersist
    protected void onCreate() {
        active = true;
    }

}
