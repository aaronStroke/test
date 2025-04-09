package mx.loal.pharmacy_admin_api.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Size(max = 200, message = "Las alergias no pueden contener más de {max} carácteres")
    @Column(length = 200)
    private String allergies;

    @NotBlank(message = "El diagnostico es requerido")
    @Size(min = 1, max = 200, message = "El diagnostico solo puede contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 200)
    private String diagnosis;

    @Size(max = 200, message = "Las indicaciones pueden contener máximo {max} carácteres")
    @Column(length = 200)
    private String generalIndications;

    @Column(length = 4000)
    private String qr;

    @DecimalMax(value = "400", message = "El peso no puede superar los {value}kg")
    @Column(length = 5, precision = 2)
    private Double weight;

    @DecimalMax(value = "300", message = "La talla no puede superar los {value}cm")
    @Column(length = 5, precision = 2)
    private Double size;

    @Size(max = 10, message = "Las tensión arterial no pueden contener más de {max} carácteres")
    @Column(length = 10)
    private String ta;

    @Max(value = 200, message = "La frecuencia cardiaca no puede ser mayor a {value}")
    @Column(length = 3)
    private Integer fc;

    @Max(value = 100, message = "La frecuencia respiratoria no puede ser mayor a {value}")
    @Column(length = 3)
    private Integer fr;

    @DecimalMax(value = "45.00", message = "La temperatura no puede exceder de {value} grados")
    @Column(length = 4, precision = 2)
    private Double temperature;

    private Boolean revision;
    private Boolean editable;

    private LocalDate revisionDate;

    @Column(length = 1000)
    private String recipeDir;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "recipe_id")
    private List<Treatment> treatments;

}
