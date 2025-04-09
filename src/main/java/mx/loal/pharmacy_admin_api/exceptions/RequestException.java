package mx.loal.pharmacy_admin_api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestException extends RuntimeException {

    private final String message;

    public RequestException(String message) {
        this.message = message;
    }
}
