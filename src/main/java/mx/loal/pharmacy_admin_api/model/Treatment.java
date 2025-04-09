package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "treatment")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private Long id;

    private Boolean assortment;

    @NotBlank(message = "Las indicaciones son requeridas")
    @Size(min = 1, max = 200, message = "Las indicaciones deben contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 200)
    private String indications;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

}
