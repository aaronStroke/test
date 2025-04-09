package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 1, max = 100, message = "El nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "El contenido es requerido")
    @Size(max = 500, message = "El contenido puede contener máximo {max} carácteres")
    @Column(nullable = false, length = 500)
    private String content;

    @Size(max = 100, message = "El código de barras debe contener máximo {max} carácteres")
    @Column(length = 100)
    private String barCode;

    @Size(max = 1000, message = "La URL de la imagen debe contener máximo {max} carácteres")
    @Column(length = 1000)
    private String urlImage;

    @NotNull(message = "El precio de compra es requerido")
    @DecimalMax(value = "99999999.99", message = "El valor máximo del precio de compra puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del precio de compra puede ser {value}")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @NotNull(message = "El precio de venta es requerido")
    @DecimalMax(value = "99999999.99", message = "El valor máximo del precio de venta puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del precio de venta puede ser {value}")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @DecimalMax(value = "99999999.99", message = "El valor máximo del descuento puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del descuento puede ser {value}")
    @Column(precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @NotNull(message = "El porcentaje de ganancia es requerido")
    @Column(nullable = false)
    private BigDecimal profitPercentage;

    @NotNull(message = "El valor del stock es requerido")
    @Max(value = 999, message = "El valor máximo del stock es {value}")
    @Min(value = 0, message = "El valor mínimo del stock es {value}")
    @Column(nullable = false, precision = 3)
    private Integer stock;

    @NotNull(message = "La categoria es requerida")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductCategory category;

    @NotNull(message = "El laboratorio es requerido")
    @ManyToOne
    @JoinColumn(name = "laboratory_id", referencedColumnName = "laboratory_id")
    private Laboratory laboratory;

    @NotNull(message = "La presentación es requerida")
    @ManyToOne
    @JoinColumn(name = "presentation_id")
    private Presentation presentation;

    @OneToMany(
        cascade = { CascadeType.ALL },
        mappedBy = "product",
        orphanRemoval = true
    )
    private Set<ActiveSubstance> activeSubstances;

    @ManyToMany
    @JoinTable(
        name = "product_age_group",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "age_group_id")
    )
    private Set<AgeGroup> ageGroups;

    private Boolean antibiotic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
