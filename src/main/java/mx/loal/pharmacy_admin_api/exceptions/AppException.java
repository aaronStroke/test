package mx.loal.pharmacy_admin_api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mx.loal.pharmacy_admin_api.payload.ExceptionDto;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppException extends RuntimeException {

  private final String code;
  private final int responseCode;
  private final List<ExceptionDto> errorList = new ArrayList<>();

  public AppException(String code, int responseCode, String message) {
    super(message);
    this.code = code;
    this.responseCode = responseCode;
  }
}
