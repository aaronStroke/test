package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.model.User;
import mx.loal.pharmacy_admin_api.payload.UserDto;
import mx.loal.pharmacy_admin_api.repository.UserRepository;
import mx.loal.pharmacy_admin_api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @SneakyThrows
    @Override
    public UserDto getCurrentUser() {

        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            var currentUserName = authentication.getName();
            var currentUser = userRepository.findByUsername(currentUserName);

            if (currentUser.isEmpty())
                throw new UserPrincipalNotFoundException("User not valid");
            else
                return convertToDTO(currentUser.get());
        }

        throw new UserPrincipalNotFoundException("User not valid");
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertToDTO(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
