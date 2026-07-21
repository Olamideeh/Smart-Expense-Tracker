// ===============================
// ExpenseController.java
// ===============================

package Smart_Expense_Tracker.controller;

import Smart_Expense_Tracker.dto.ExpenseRequestDto;
import Smart_Expense_Tracker.dto.ExpenseResponseDto;
import Smart_Expense_Tracker.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseResponseDto addExpense(@Valid @RequestBody ExpenseRequestDto request){

        return expenseService.addExpense(request);
    }

    @GetMapping("/{id}")
    public ExpenseResponseDto getExpenseById(@PathVariable Long id){

        return expenseService.getExpenseById(id);
    }

    @GetMapping
    public List<ExpenseResponseDto> getAllExpenses(){

        return expenseService.getAllExpenses();
    }

    @PutMapping("/{id}")
    public ExpenseResponseDto updateExpense(@PathVariable Long id,
                                            @Valid @RequestBody ExpenseRequestDto request){

        return expenseService.updateExpense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id){

        expenseService.deleteExpense(id);

        return "Expense Deleted Successfully";
    }

    @GetMapping
    public Page<ExpenseResponseDto> getExpenses(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction,

            @RequestParam(required = false) String category
    ) {

        return expenseService.getExpenses(
                page,
                size,
                sortBy,
                direction,
                category
        );
    }
    @GetMapping("/search")
    public Page<ExpenseResponseDto> searchExpenses(@RequestParam String keyword){
        return (Page<ExpenseResponseDto>) expenseService.searchExpenses(keyword);
    }
    @GetMapping("/category")
    public List<ExpenseResponseDto> getExpensesByCategory(@RequestParam String category){
        return expenseService.getExpensesByCategory(category);
    }
}