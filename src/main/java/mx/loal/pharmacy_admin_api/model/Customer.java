package mx.loal.pharmacy_admin_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Hidden
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotBlank(message = "El primer nombre es requerido")
    @Size(min = 1, max = 50, message = "El primero nombre debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 50)
    private String firstName;

    @Size(max = 50, message = "El segundo nombre debe contener máximo {max} carácteres")
    @Column(length = 50)
    private String secondName;

    @NotBlank(message = "El apellido paterno es requerido")
    @Size(min = 1, max = 50, message = "El apellido paterno debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "El apellido materno es requerido")
    @Size(min = 1, max = 50, message = "El apellido materno debe contener entre {min} y {max} carácteres")
    @Column(nullable = false, length = 50)
    private String secondLastName;

    private LocalDate bornDate;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<MedicalConsult> medicalConsults;

    public void addMedicalConsult(MedicalConsult medicalConsult) {
        if (medicalConsults == null)
            medicalConsults = new ArrayList<>();

        medicalConsults.add(medicalConsult);
    }

    /**
     * Calculates the age of a person based on their birthdate.
     *
     * @return The age in years
     * @throws IllegalArgumentException if birthDate is in the future or null
     */
    @Transient
    public int calculateAge() {

        var currentDate = LocalDate.now();

        if (bornDate.isAfter(currentDate))
            throw new IllegalArgumentException("Birth date cannot be in the future");

        return Period.between(bornDate, currentDate).getYears();
    }

}
