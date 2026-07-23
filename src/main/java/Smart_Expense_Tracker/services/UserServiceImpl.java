import Smart_Expense_Tracker.Entity.User;
import Smart_Expense_Tracker.Enum.Role;
import Smart_Expense_Tracker.dto.UserRequestDto;
import Smart_Expense_Tracker.dto.UserResponseDto;
import Smart_Expense_Tracker.repository.UserRepository;
import Smart_Expense_Tracker.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto register(UserRequestDto request) {

        User user = new User();

        user.setFullName(request.getFullName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        UserResponseDto response = new UserResponseDto();

        response.setId(savedUser.getId());

        response.setFullName(savedUser.getFullName());

        response.setEmail(savedUser.getEmail());

        response.setRole(savedUser.getRole());

        return response;

    }

}