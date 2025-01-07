package sports_shop.project1.security.model;

import org.springframework.security.core.GrantedAuthority;
import sports_shop.project1.domain.user.entity.Role;

import java.util.Collection;

public interface ProviderUser {

    Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getUsername();

}
