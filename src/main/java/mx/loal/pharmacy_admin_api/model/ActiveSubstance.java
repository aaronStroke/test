package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "active_substance")
public class ActiveSubstance {

    @EmbeddedId
    private ProductSubstanceId id = new ProductSubstanceId();

    @NotBlank(message = "La concentración es requerida")
    @Size(max = 100, message = "La concentración no pueden contener más de {max} carácteres")
    @Column(length = 100, nullable = false)
    private String concentration;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "La sustancia es requerida")
    @ManyToOne
    @MapsId("substanceId")
    @JoinColumn(name = "substance_id")
    private Substance substance;

    @NotNull(message = "La unidad de medida es requerida")
    @ManyToOne
    @JoinColumn(name = "measurement_unit_id")
    private MeasurementUnit measurementUnit;

}
