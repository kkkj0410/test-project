package sports_shop.project1.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sports_shop.project1.domain.user.dto.UserDto;
import sports_shop.project1.domain.user.entity.Role;
import sports_shop.project1.domain.user.entity.Users;
import sports_shop.project1.domain.user.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean save(UserDto userDto){
        if(this.findByUser(userDto.getEmail()) != null){
            log.info("User already exists");
            return false;
        }

        userDto.setRole(Role.USER);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users users = userDtoToUser(userDto);

        userRepository.save(users);
        log.info("Save User : {}", users);
        return true;
    }

    public UserDto findByUser(String email){
        Users users = userRepository.findByEmail(email);
        if(users == null){
            log.info("user is null");
            return null;
        }

        return usersToUserDto(users);
    }

    private UserDto usersToUserDto(Users users){

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

    private Users userDtoToUser(UserDto userDto){
        return Users.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .phoneNumber(userDto.getPhoneNumber())
                .role(userDto.getRole())
                .birthday(userDto.getBirthday())
                .build();
    }



}
