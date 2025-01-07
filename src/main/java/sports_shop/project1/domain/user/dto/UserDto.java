package sports_shop.project1.domain.user.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import sports_shop.project1.domain.user.entity.Role;

@Data
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String username;

    //010-1234-5678
    private String phoneNumber;
    private Role role;
}
