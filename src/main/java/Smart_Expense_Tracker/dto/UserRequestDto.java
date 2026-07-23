package Smart_Expense_Tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDto {
    @NotBlank(message = "please provide ur full name")
    private String FullName;

    @NotBlank
    private String Email;

    @NotBlank(message = "password cannot be empty")
    private String Password;
}
