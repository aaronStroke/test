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
@Table
@Entity(name = "measurement_unit")
public class MeasurementUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_unit_id")
    private Long id;

    @NotBlank(message = "El identificador es requerido")
    @Size(min = 1, max = 10, message = "El identificador debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 10)
    private String identifier;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 1, max = 100, message = "El nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 100)
    private String name;

}
