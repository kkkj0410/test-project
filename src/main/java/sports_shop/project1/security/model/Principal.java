package sports_shop.project1.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Principal implements UserDetails {

    private final ProviderUser providerUser;

    public Principal(ProviderUser providerUser) {
        this.providerUser = providerUser;
    }

    public Principal(UserDetailsDto userDetailsDto) {
        this.providerUser = userDetailsDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return providerUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        return providerUser.getPassword();
    }

    @Override
    public String getUsername() {
        return providerUser.getUsername();
    }
}
