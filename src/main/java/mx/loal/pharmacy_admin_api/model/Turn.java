package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.loal.pharmacy_admin_api.utils.enums.TurnStatus;

import java.time.LocalDateTime;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "turn")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "turn_id")
    private Long id;

    @NotBlank(message = "El numero de turno es requerido")
    @Size(min = 1, max = 10, message = "El numero del turno puede contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 10)
    private String turnCode;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @NotNull(message = "El tipo de consulta médica es requerido")
    @ManyToOne
    @JoinColumn(name = "medical_consult_type_id", referencedColumnName = "medical_consult_type_id", nullable = false)
    private MedicalConsultType medicalConsultType;

    @NotNull(message = "El estatus es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private TurnStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
