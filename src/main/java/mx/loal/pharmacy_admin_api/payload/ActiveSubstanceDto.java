package mx.loal.pharmacy_admin_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.ActiveSubstance}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveSubstanceDto implements Serializable {
    @Size(message = "La concentración no pueden contener más de {max} carácteres", max = 100)
    @NotBlank(message = "La concentración es requerida")
    private String concentration;
    @NotNull(message = "La sustancia es requerida")
    private SubstanceDto substance;
    @NotNull(message = "La unidad de medida es requerida")
    private MeasurementUnitDto measurementUnit;
}
