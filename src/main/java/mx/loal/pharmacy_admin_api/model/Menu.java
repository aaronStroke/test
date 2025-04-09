package mx.loal.pharmacy_admin_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import mx.loal.pharmacy_admin_api.utils.enums.Role;

import java.util.List;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String name;
    private String icon;
    private String uri;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer orden;

    @Column(name = "superior_menu")
    private Long superiorMenu;

    @ManyToOne
    @JoinColumn(name = "father_id", insertable = false, updatable = false)
    @JsonBackReference
    private Menu menu;

    private Boolean active;

    @OneToMany(mappedBy = "menu")
    private List<Menu> subMenu;

}
