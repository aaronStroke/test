package mx.loal.pharmacy_admin_api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    @Size(message = "El nombre debe contener entre {min} y {max} carácteres", min = 1, max = 100)
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotNull(message = "El contenido es requerido")
    @Size(max = 100, message = "El contenido puede contener máximo {max} carácteres")
    private String content;
    @Size(message = "El código de barras debe contener máximo {max} carácteres", max = 100)
    private String barCode;
    @Size(message = "La URL de la imagen debe contener máximo {max} carácteres", max = 1000)
    private String urlImage;
    @DecimalMax(value = "99999999.99", message = "El valor máximo del precio de compra puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del precio de compra puede ser {value}")
    @NotNull(message = "El precio de compra es requerido")
    private BigDecimal purchasePrice;
    @DecimalMax(value = "99999999.99", message = "El valor máximo del precio de venta puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del precio de venta puede ser {value}")
    @NotNull(message = "El precio de venta es requerido")
    private BigDecimal salePrice;
    @DecimalMax(value = "99999999.99", message = "El valor máximo del descuento puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del descuento puede ser {value}")
    private BigDecimal discountPrice;
    @NotNull(message = "El porcentaje de ganancia es requerido")
    private BigDecimal profitPercentage;
    @NotNull(message = "El valor del stock es requerido")
    @Min(message = "El valor mínimo del stock es {value}", value = 0)
    @Max(message = "El valor máximo del stock es {value}", value = 999)
    private Integer stock;
    @NotNull(message = "La categoria es requerida")
    private ProductCategory category;
    @NotNull(message = "El laboratorio es requerido")
    private LaboratoryDto laboratory;
    @NotNull(message = "La presentación es requerida")
    private PresentationDto presentation;
    private Set<ActiveSubstanceDto> activeSubstances;
    private Set<AgeGroupDto> ageGroups;
    private Boolean antibiotic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
