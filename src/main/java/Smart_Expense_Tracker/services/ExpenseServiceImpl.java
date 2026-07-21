// =====================================
// ExpenseServiceImpl.java
// =====================================

package Smart_Expense_Tracker.services;

import Smart_Expense_Tracker.Entity.Expense;
import Smart_Expense_Tracker.dto.ExpenseMapper;
import Smart_Expense_Tracker.dto.ExpenseRequestDto;
import Smart_Expense_Tracker.dto.ExpenseResponseDto;
import Smart_Expense_Tracker.repository.ExpenseRepository;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ExpenseResponseDto addExpense(ExpenseRequestDto request) {

        Expense expense = ExpenseMapper.toEntity(request);

        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseMapper.toResponse(savedExpense);
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        return ExpenseMapper.toResponse(expense);
    }

    @Override
    public List<ExpenseResponseDto> getAllExpenses() {

        return expenseRepository.findAll()
                .stream()
                .map(ExpenseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto request) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        Expense updatedExpense = expenseRepository.save(expense);

        ExpenseMapper ExpenseMapper = null;
        return ExpenseMapper.toResponse(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {

        expenseRepository.deleteById(id);
    }

    @Override
    public Page<ExpenseResponseDto> getExpenses(int page, int size,String sortBy,String direction,String category) {

        Sort sort = direction.equalsIgnoreCase( "desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Expense> expenses;
        if(category == null || category.isBlank())  {
            expenses = expenseRepository.findAll(pageable);
        }else {
            expenses = (Page<Expense>) expenseRepository.findByCategory(category,pageable);
        }

        return expenses.map(ExpenseMapper::toResponseDto);
    }

    @Override
    public List<ExpenseResponseDto> searchExpenses(String keyword) {
        return expenseRepository.findByTitleContianingIgnoreCase(keyword).stream()
                .map(ExpenseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseResponseDto> getExpensesByCategory(String keyword) {
        return expenseRepository.findByCategoryContainingIgnoreCase(keyword)
                .stream()
                .map(ExpenseMapper::toResponseDto)
                .toList();
    }
}