package mx.loal.pharmacy_admin_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "medical_consult_id")
    private MedicalConsult medicalConsult;

    @NotNull(message = "La cantidad es requerida")
    @Max(value = 999, message = "El valor máximo de la cantidad es {value}")
    @Min(value = 0, message = "El valor mínimo de la cantidad es {value}")
    @Column(nullable = false, precision = 3)
    private Integer quantity;

    @NotNull(message = "El precio unitario es requerido")
    @DecimalMax(value = "99999999.99", message = "El valor máximo del precio unitario puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del precio unitario puede ser {value}")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @NotNull(message = "El subtotal es requerido")
    @DecimalMax(value = "99999999.99", message = "El valor máximo del precio unitario puede ser {value}")
    @DecimalMin(value = "0.00", message = "El valor mínimo del precio unitario puede ser {value}")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    @JsonBackReference
    private Sale sale;

}
