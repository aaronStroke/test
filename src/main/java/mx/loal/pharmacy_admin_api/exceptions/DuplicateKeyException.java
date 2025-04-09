package mx.loal.pharmacy_admin_api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DuplicateKeyException extends RuntimeException {

  private final String message;

  public DuplicateKeyException(String message) {
    super(message);
    this.message = message;
  }

}
