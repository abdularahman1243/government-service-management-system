package spring.developer.gsms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_username", columnList = "username"),
                @Index(name = "idx_users_email", columnList = "email"),
                @Index(name = "idx_users_user_type", columnList = "userType")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== AUTH =====
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    // ===== ROLE =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserType userType;

    // ===== PERSONAL INFO =====
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;

    // ===== GOVERNMENT SPECIFIC =====
    private String nationalId;    // برای CITIZEN
    private String employeeCode;  // برای OFFICER / ADMIN
    private String department;    // ICT, Telecom, etc

    // ===== AUDIT =====
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.enabled = true;
        this.createdAt = LocalDateTime.now();
    }

    public enum UserType {
        SUPER_ADMIN,
        ADMIN,
        OFFICER,
        CITIZEN
    }
}
