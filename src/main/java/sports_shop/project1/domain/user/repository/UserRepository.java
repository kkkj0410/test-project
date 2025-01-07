package sports_shop.project1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sports_shop.project1.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {


    public User findByLoginId(String loginId);
//
//    public User findByPassword(String password);

    @Query("SELECT u FROM User u WHERE u.loginId = :loginId AND u.password = :password")
    User findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);

    User findByUsername(String username);
}
