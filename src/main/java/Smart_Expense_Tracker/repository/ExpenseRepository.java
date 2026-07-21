package Smart_Expense_Tracker.repository;
import Smart_Expense_Tracker.Entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

   List<Expense>findByCategory(String category, Pageable pageable);
   Page<Expense> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);
   List<Expense>findByTitleContaining(String title, Pageable pageable);
   List<Expense>findByTitleContianingIgnoreCase(String keyword);
   List<Expense>findByCategoryContainingIgnoreCase(String keyword);
}
