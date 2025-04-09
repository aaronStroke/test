package mx.loal.pharmacy_admin_api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.MedicalConsult}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalConsultDto implements Serializable {
    private Long id;
    private MedicalConsultTypeDto medicalConsultType;
    private RecipeDto recipe;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalTime initHour;
    private LocalTime endHour;
}
