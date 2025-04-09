package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cash_register")
public class CashRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cash_register_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime openingDate;
    private LocalDateTime closeDate;

    @NotNull(message = "El saldo inicial es requerido")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal initialAmount;

    @NotNull(message = "El saldo final es requerido")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
