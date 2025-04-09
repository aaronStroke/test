package mx.loal.pharmacy_admin_api.utils.constants;

public class ExceptionMessageConstants {

    private ExceptionMessageConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * AUTH
     */
    public static final String UNAUTHORIZED_MSG = "You are not authorized";
    public static final String WRONG_PASSWORD_MSG = "Wrong password";
    public static final String WRONG_CONF_PASSWORD_MSG = "Password are not the same";
    public static final String EXPIRED_TOKEN_MSG = "Token has expired";
    public static final String INVALID_TOKEN_MSG = "Token is invalid";
    public static final String USER_NOT_FOUND_MSG = "User not found";
    public static final String ROLE_NOT_FOUND_MSG = "Role not found";

    /**
     * LABORATORIES
     */
//    public static final String TIME_CONFLICT = "Appointments must be at least 60 minutes apart";
//    public static final String TIME_INVALID = "Appointments must be between 8 AM and 6 PM";
    public static final String LABORATORY_UPDATE_CONFLICT = "Laboratory cannot be updated. The data is not the same";
    public static final String LABORATORY_NOT_FOUND = "Laboratory not found";
    public static final String LABORATORY_ALREADY_EXISTS = "Laboratory with this name already exists";
    public static final String LABORATORIES_NOT_FOUND = "Laboratories is empty";

    /**
     * MEASUREMENT UNITS
     */
    public static final String MEASUREMENT_UNIT_UPDATE_CONFLICT = "Measurement unit cannot be updated. The data is not the same";
    public static final String MEASUREMENT_UNIT_NOT_FOUND = "Measurement unit not found";
    public static final String MEASUREMENT_UNITS_NOT_FOUND = "Measurement units is empty";
    public static final String MEASUREMENT_UNIT_ALREADY_EXISTS = "Measurement unit with this name already exists";

    /**
     * MEDICAL CONSULT TYPES
     */
    public static final String MEDICAL_CONSULT_TYPE_UPDATE_CONFLICT = "Medical consult type cannot be updated. The data is not the same";
    public static final String MEDICAL_CONSULT_TYPE_NOT_FOUND = "Medical consult type not found";
    public static final String MEDICAL_CONSULT_TYPE_ALREADY_EXISTS = "Medical consult type with this name already exists";

    /**
     * SUBSTANCES
     */
    public static final String SUBSTANCE_UPDATE_CONFLICT = "Substance cannot be updated. The data is not the same";
    public static final String SUBSTANCE_NOT_FOUND = "Substance not found";
    public static final String SUBSTANCE_ALREADY_EXISTS = "Substance with this name already exists";

    /**
     * SUBSTANCES
     */
    public static final String PRODUCT_UPDATE_CONFLICT = "Product cannot be updated. The data is not the same";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PRODUCTS_NOT_FOUND = "Products not found";
    public static final String PRODUCT_ALREADY_EXISTS = "Product with this name already exists";


    /**
     * CUSTOMER
     */

    public static final String CUSTOMER_NOT_EXIST = "Customer ID not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_UPDATE_CONFLICT = "Unable to update customer. The provided data does not match";



    /**
     * PATIENTS
     */
    public static final String PATIENT_NOT_FOUND_MSG = "Patient not found";

    /**
     * DOCTORS
     */
    public static final String DOCTOR_NOT_FOUND_MSG = "Doctor not found";


    /**
     * API RESPONSES
     */
    public static final String INTERNAL_SERVER_ERROR_MSG = "An unexpected error occurred";

}
