package Smart_Expense_Tracker.services;
import Smart_Expense_Tracker.dto.UserRequestDto;
import Smart_Expense_Tracker.dto.UserResponseDto;

public interface UserService {

    UserResponseDto register(UserRequestDto request);

}