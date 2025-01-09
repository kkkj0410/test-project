package sports_shop.project1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sports_shop.project1.domain.user.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
