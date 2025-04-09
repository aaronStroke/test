package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import mx.loal.pharmacy_admin_api.utils.enums.MedicalConsultTypePriority;

import java.math.BigDecimal;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "medical_consult_type")
public class MedicalConsultType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_consult_type_id")
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 1, max = 100, message = "El nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "El identificador es requerido")
    @Size(min = 1, max = 10, message = "El identificador debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 10)
    private String identifier;

    @NotBlank(message = "La descripción es requerida")
    @Size(min = 1, max = 200, message = "La descripción debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 200)
    private String description;

    @NotNull(message = "El costo del tipo de consulta es requerido")
    @DecimalMax(value = "9999.99", message = "El valor máximo del costo puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del costo puede ser {value}")
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal cost;

    @NotNull(message = "La prioridad es requerida")
    @Column(nullable = false, precision = 1)
    private Integer priority;

}
