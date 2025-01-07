package sports_shop.project1.security.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sports_shop.project1.domain.user.dto.UserDto;
import sports_shop.project1.domain.user.entity.User;
import sports_shop.project1.domain.user.repository.UserRepository;
import sports_shop.project1.security.model.Principal;
import sports_shop.project1.security.model.UserDetailsDto;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId);
        if(user == null){
            log.info("user is null");
            return null;
        }
        UserDetailsDto userDetailsDto = new UserDetailsDto(modelMapper.map(user, UserDto.class));
        return new Principal(userDetailsDto);
    }
}
