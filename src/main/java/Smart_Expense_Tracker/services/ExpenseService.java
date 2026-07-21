// ===============================
// ExpenseService.java
// ===============================

package Smart_Expense_Tracker.services;

import Smart_Expense_Tracker.Entity.Expense;
import Smart_Expense_Tracker.dto.ExpenseRequestDto;
import Smart_Expense_Tracker.dto.ExpenseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {

    ExpenseResponseDto addExpense(ExpenseRequestDto request);

    ExpenseResponseDto getExpenseById(Long id);

    List<ExpenseResponseDto> getAllExpenses();

    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto request);

    void deleteExpense(Long id);

    Page<ExpenseResponseDto> getExpenses(int page,
                                         int size,
                                         String sortBy,
                                         String direction,
                                         String category
                                         );

    List<ExpenseResponseDto>searchExpenses(String keyword);
    List<ExpenseResponseDto> getExpensesByCategory(String keyword);

}