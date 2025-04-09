package mx.loal.pharmacy_admin_api.payload.request;

import mx.loal.pharmacy_admin_api.utils.enums.TurnStatus;

public record ChangeTurnStatus(
    TurnStatus status
) {
}
