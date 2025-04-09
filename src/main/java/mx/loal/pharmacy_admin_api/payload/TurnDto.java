package mx.loal.pharmacy_admin_api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.loal.pharmacy_admin_api.model.Customer;
import mx.loal.pharmacy_admin_api.utils.enums.TurnStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link mx.loal.pharmacy_admin_api.model.Turn}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnDto implements Serializable {
    private Long id;
    private String turnCode;
    private Customer customer;
    @NotNull(message = "El tipo de consulta médica es requerido")
    private MedicalConsultTypeDto medicalConsultType;
    private TurnStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
