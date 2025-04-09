package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Recipe}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto implements Serializable {
    private Long id;
    @Size(message = "Las alergias no pueden contener más de {max} carácteres", max = 200)
    private String allergies;
    @Size(message = "El diagnostico solo puede contener entre {min} y {max} carácteres", min = 1, max = 200)
    @NotBlank(message = "El diagnostico es requerido")
    private String diagnosis;
    @Size(message = "Las indicaciones pueden contener máximo {max} carácteres", max = 200)
    private String generalIndications;
    private String qr;
    private Double weight;
    private Double size;
    @Size(message = "Las tensión arterial no pueden contener más de {max} carácteres", max = 10)
    private String ta;
    @Max(message = "La frecuencia cardiaca no puede ser mayor a {value}", value = 200)
    private Integer fc;
    @Max(message = "La frecuencia respiratoria no puede ser mayor a {value}", value = 100)
    private Integer fr;
    private Double temperature;
    private Boolean revision;
    private Boolean editable;
    private LocalDate revisionDate;
    @Size(message = "Debes agregar al menos un registro de tratamiento", min = 1)
    private List<TreatmentDto> treatments;
}
