package mx.loal.pharmacy_admin_api.exceptions;

public class UnauthorizedAppointmentException extends RuntimeException {
    public UnauthorizedAppointmentException(String message) {
        super(message);
    }
}
