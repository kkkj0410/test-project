package sports_shop.project1.domain.user.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users") // 데이터베이스 테이블 이름
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String loginId;
    private String password;
    private String email;
    private String username;
    //010-1234-5678
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

}
