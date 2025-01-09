package sports_shop.project1.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sports_shop.project1.domain.user.dto.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsDto implements ProviderUser{

    private final UserDto userDto;

    public UserDetailsDto(UserDto userDto) {
        this.userDto = userDto;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = "ROLE_" + userDto.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }
}
