package Smart_Expense_Tracker.dto;

import Smart_Expense_Tracker.Entity.Expense;
import Smart_Expense_Tracker.dto.ExpenseRequestDto;
import Smart_Expense_Tracker.dto.ExpenseResponseDto;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseRequestDto dto){

        Expense expense = new Expense();

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());

        return expense;
    }

    public static ExpenseResponseDto toResponse(Expense expense){

        ExpenseResponseDto dto = new ExpenseResponseDto();

        dto.setId(expense.getId());
        dto.setTitle(expense.getTitle());
        dto.setAmount(expense.getAmount());
        dto.setCategory(expense.getCategory());
        dto.setDescription(expense.getDescription());

        return dto;
    }
    public static ExpenseResponseDto toResponseDto(Expense expense) {
        return toResponse(expense);
    }
}