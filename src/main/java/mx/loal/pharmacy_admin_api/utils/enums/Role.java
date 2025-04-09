package mx.loal.pharmacy_admin_api.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static mx.loal.pharmacy_admin_api.utils.enums.Permissions.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(
        Set.of(
            USER_READ,
            USER_CREATE,
            USER_UPDATE,
            USER_DELETE
        )
    ),
    ADMIN(
        Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            DOCTOR_READ,
            DOCTOR_CREATE,
            DOCTOR_UPDATE,
            DOCTOR_DELETE
        )
    ),
    DOCTOR(
        Set.of(
            DOCTOR_READ,
            DOCTOR_CREATE,
            DOCTOR_UPDATE,
            DOCTOR_DELETE
        )
    );

    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
