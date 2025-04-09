package mx.loal.pharmacy_admin_api.payload;

import lombok.Getter;
import lombok.Setter;
import mx.loal.pharmacy_admin_api.model.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Menu}
 */
@Getter
@Setter
public class MenuDto implements Serializable {
    private Long id;
    private String name;
    private String icon;
    private String uri;
    private Integer orden;
    private Long superiorMenu;
    private Boolean active;
    private List<Menu> subMenu;
}
