package sports_shop.project1.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sports_shop.project1.domain.user.dto.UserDto;
import sports_shop.project1.domain.user.entity.Users;
import sports_shop.project1.domain.user.repository.UserRepository;
import sports_shop.project1.security.model.Principal;
import sports_shop.project1.security.model.UserDetailsDto;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email);
        if(users == null){
            log.error("user is null");
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        UserDetailsDto userDetailsDto = new UserDetailsDto(userToUserDto(users));
        return new Principal(userDetailsDto);
    }


    private UserDto userToUserDto(Users users){

        return UserDto.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .username(users.getUsername())
                .phoneNumber(users.getPhoneNumber())
                .role(users.getRole())
                .birthday(users.getBirthday())
                .build();
    }
}
