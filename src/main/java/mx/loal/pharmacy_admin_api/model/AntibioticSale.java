package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "antibiotic_sale")
public class AntibioticSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "antibiotic_sale_id")
    private Long id;

    @NotNull(message = "El producto es requerido")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "La cantidad es requerida")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "La fecha y hora de la venta es requerida")
    @Column(nullable = false)
    private LocalDateTime saleDateTime;

    @NotNull(message = "El usuario es requerido")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
