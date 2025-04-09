package mx.loal.pharmacy_admin_api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InternalServerErrorException extends RuntimeException {

  private String code;
  private String message;

  public InternalServerErrorException(String message) {
    this.message = message;
  }
}
