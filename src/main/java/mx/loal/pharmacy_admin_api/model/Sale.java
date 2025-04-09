package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mx.loal.pharmacy_admin_api.utils.enums.PayType;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "El total de la venta es requerido")
    @DecimalMax(value = "99999999.99", message = "El total de la venta no puede ser mayor a {value}")
    @DecimalMin(value = "0.00", message = "El total de la venta no puede ser menor a {value}")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @DecimalMax(value = "99999999.99", message = "El descuento no puede ser mayor a {value}")
    @DecimalMin(value = "0.00", message = "El descuento no puede ser menor a {value}")
    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @DecimalMax(value = "99999999.99", message = "El cambio no puede ser mayor a {value}")
    @DecimalMin(value = "0.00", message = "El cambio no puede ser menor a {value}")
    @Column(precision = 10, scale = 2)
    private BigDecimal cashReturn;

    @NotNull(message = "El tipo de pago es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PayType payType;

    @NotNull(message = "La caja que realizó la venta es requerida")
    @ManyToOne
    @JoinColumn(name = "cash_register_id", nullable = false)
    private CashRegister cashRegister;

    @OneToMany(mappedBy = "sale", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<SaleDetail> salesDetail;

}
