package mx.loal.pharmacy_admin_api.utils.validations;

import org.apache.commons.validator.routines.IntegerValidator;

public class NumberValidations {

    public static boolean checkIfIsNumber(String number) {
        var validator = IntegerValidator.getInstance();
        return validator.isValid(number) && !number.startsWith("0");
    }

}
