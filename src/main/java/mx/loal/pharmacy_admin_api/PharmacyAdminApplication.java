package mx.loal.pharmacy_admin_api;

import mx.loal.pharmacy_admin_api.model.AgeGroup;
import mx.loal.pharmacy_admin_api.model.Menu;
import mx.loal.pharmacy_admin_api.model.Presentation;
import mx.loal.pharmacy_admin_api.model.User;
import mx.loal.pharmacy_admin_api.repository.AgeGroupRepository;
import mx.loal.pharmacy_admin_api.repository.MenuRepository;
import mx.loal.pharmacy_admin_api.repository.PresentationRepository;
import mx.loal.pharmacy_admin_api.repository.UserRepository;
import mx.loal.pharmacy_admin_api.utils.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PharmacyAdminApplication implements CommandLineRunner {

    @Autowired
    private PresentationRepository presentationRepository;

	public static void main(String[] args) {
		SpringApplication.run(PharmacyAdminApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AgeGroupRepository ageGroupRepository;

	@Autowired
	private MenuRepository _menuRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

//		var user = new User();
//
//		user.setPassword(passwordEncoder.encode("q1w2e3r4"));
//		user.setUsername("aalopeza");
//		user.setRole(Role.ADMIN);
//		user.setFirstName("Aarón");
//		user.setSecondName("Azael");
//		user.setLastName("López");
//		user.setSecondLastName("Alvarez");
//
//		userRepository.save(user);

//		var menu = Menu
//			.builder()
//			.name("Catálogos")
//			.icon("book-information-variant")
//			.orden(1)
//			.role(Role.ADMIN)
//			.uri("/catalogs")
//			.active(true)
//			.build();
//
//		_menuRepository.save(menu);
//
//		var pediatric = AgeGroup
//			.builder()
//			.name("Pediatrico")
//			.build();
//
//		var childish = AgeGroup
//			.builder()
//			.name("Infantil")
//			.build();
//
//		var adult = AgeGroup
//				.builder()
//				.name("Adulto")
//				.build();
//
//		ageGroupRepository.save(pediatric);
//		ageGroupRepository.save(childish);
//		ageGroupRepository.save(adult);
//
//		var presentation = Presentation
//			.builder()
//			.name("Tabletas")
//			.build();
//
//		presentationRepository.save(presentation);

	}
}
