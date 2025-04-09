package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProductSubstanceId implements Serializable {

    private Long productId;

    private Long substanceId;

}
