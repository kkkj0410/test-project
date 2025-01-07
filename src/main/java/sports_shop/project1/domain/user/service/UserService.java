package sports_shop.project1.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sports_shop.project1.domain.user.dto.UserDto;
import sports_shop.project1.domain.user.entity.Role;
import sports_shop.project1.domain.user.entity.User;
import sports_shop.project1.domain.user.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean save(UserDto userDto){
        if(this.findByUser(userDto.getLoginId()) != null){
            log.info("User already exists");
            return false;
        }

        userDto.setRole(Role.USER);
        User user = modelMapper.map(userDto, User.class);

        userRepository.save(user);
        log.info("Save User : {}", user);
        return true;
    }

    public UserDto findByUser(String loginId){
        User user = userRepository.findByLoginId(loginId);
        if(user == null){
            log.info("user is null");
            return null;
        }

        return modelMapper.map(user, UserDto.class);
    }

//    public UserDto findByLoginId(String loginId){
//        User user = userRepository.findByLoginId(loginId);
//        if(user == null){
//            return null;
//        }
//
//        return modelMapper.map(user, UserDto.class);
//    }
//
//    public UserDto findByPassword(String password){
//        User user = userRepository.findByPassword(password);
//        if(user == null){
//            return null;
//        }
//
//        return modelMapper.map(user, UserDto.class);
//    }


}
