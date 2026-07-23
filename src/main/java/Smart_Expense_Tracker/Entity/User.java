package Smart_Expense_Tracker.Entity;

import Smart_Expense_Tracker.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.usertype.UserType;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FullName;
    @Column(unique = true)
    @NotBlank(message = "pls provide valid email")
    private String email;
    @Column(unique = true)
    @NotBlank(message = "input correct password")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
