package mx.loal.pharmacy_admin_api.utils.constants;

public class EndpointsConstants {

    private EndpointsConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String BASE_API_ENDPOINT = "/api/v1";

    public static final String AUTH_ENDPOINT = BASE_API_ENDPOINT + "/auth";
    public static final String LOGOUT_ENDPOINT = BASE_API_ENDPOINT + "/logout";
    public static final String AUTH_PATTERN_ENDPOINT = AUTH_ENDPOINT + "/**";
    public static final String ACTUATOR_ENDPOINT = "/actuator";
    public static final String ACTUATOR_PATTERN_ENDPOINT = ACTUATOR_ENDPOINT + "/**";
    public static final String OPEN_API_HTML_ENDPOINT = "/swagger-ui.html";
    public static final String OPEN_API_UI_ENDPOINT = "/swagger-ui";
    public static final String OPEN_API_UI_PATTERN_ENDPOINT = OPEN_API_UI_ENDPOINT + "/**";
    public static final String OPEN_API_DOCS_CONFIG = "/v3/api-docs";
    public static final String OPEN_API_DOCS_CONFIG_PATTERN = OPEN_API_DOCS_CONFIG + "/**";
    public static final String OPEN_API_SWAGGER_CONFIG = "/v3/api-docs/swagger-config";
    public static final String OPEN_API_SWAGGER_CONFIG_PATTERN = OPEN_API_SWAGGER_CONFIG + "/**";

    /*
     * urls para transaccionales
     * */
    public static final String SALES_ENDPOINT = BASE_API_ENDPOINT + "/sales";
    public static final String SALES_PATTERN_ENDPOINT = SALES_ENDPOINT + "/**";

    public static final String CASH_REGISTER_ENDPOINT = BASE_API_ENDPOINT + "/cash-register";
    public static final String CASH_REGISTER_PATTERN_ENDPOINT = CASH_REGISTER_ENDPOINT + "/**";

    /*
    * urls para catálogos
    * */
    public static final String LABORATORIES_ENDPOINT = BASE_API_ENDPOINT + "/laboratories";
    public static final String LABORATORIES_PATTERN_ENDPOINT = LABORATORIES_ENDPOINT + "/**";

    public static final String AGE_GROUPS_ENDPOINT = BASE_API_ENDPOINT + "/age-groups";
    public static final String AGE_GROUPS_PATTERN_ENDPOINT = AGE_GROUPS_ENDPOINT + "/**";

    public static final String PRESENTATIONS_ENDPOINT = BASE_API_ENDPOINT + "/presentations";
    public static final String PRESENTATIONS_PATTERN_ENDPOINT = PRESENTATIONS_ENDPOINT + "/**";

    public static final String MEASUREMENT_UNITS_ENDPOINT = BASE_API_ENDPOINT + "/measurement-units";
    public static final String MEASUREMENT_UNITS_PATTERN_ENDPOINT = MEASUREMENT_UNITS_ENDPOINT + "/**";

    public static final String SUBSTANCES_ENDPOINT = BASE_API_ENDPOINT + "/substances";
    public static final String SUBSTANCES_PATTERN_ENDPOINT = SUBSTANCES_ENDPOINT + "/**";

    public static final String MEDICAL_CONSULT_TYPES_ENDPOINT = BASE_API_ENDPOINT + "/medical-consult-types";
    public static final String MEDICAL_CONSULT_TYPES_PATTERN_ENDPOINT = MEDICAL_CONSULT_TYPES_ENDPOINT + "/**";

    public static final String MEDICAL_CONSULT_ENDPOINT = BASE_API_ENDPOINT + "/medical-consult";
    public static final String MEDICAL_CONSULT_PATTERN_ENDPOINT = MEDICAL_CONSULT_TYPES_ENDPOINT + "/**";

    public static final String PRODUCTS_ENDPOINT = BASE_API_ENDPOINT + "/products";
    public static final String PRODUCTS_PATTERN_ENDPOINT = PRODUCTS_ENDPOINT + "/**";

    public static final String USERS_ENDPOINT = BASE_API_ENDPOINT + "/users";
    public static final String USERS_PATTERN_ENDPOINT = USERS_ENDPOINT + "/**";

    public static final String MENUS_ENDPOINT = BASE_API_ENDPOINT + "/menu";
    public static final String MENUS_PATTERN_ENDPOINT = MENUS_ENDPOINT + "/**";

    public static final String CUSTOMER_ENDPOINT = BASE_API_ENDPOINT + "/customer";
    public static final String CUSTOMER_PATTERN_ENDPOINT = CUSTOMER_ENDPOINT + "/**";

    /*
     * urls para los websockets
     * */
    public static final String TURNS_WEBSOCKET_ENDPOINT = "/turns-websocket";
    public static final String TURNS_WEBSOCKET_PATTERN_ENDPOINT = TURNS_WEBSOCKET_ENDPOINT + "/**";
    public static final String TURNS_WEBSOCKET_TOPIC = "/topic/turns";
    public static final String TURNS_ENDPOINT = BASE_API_ENDPOINT + "/turns";
    public static final String TURNS_PATTERN_ENDPOINT = TURNS_ENDPOINT + "/**";



}
