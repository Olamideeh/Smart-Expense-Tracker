package Smart_Expense_Tracker.ExpenseSpecification;
import Smart_Expense_Tracker.Entity.Expense;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ExpenseSpecification {

        public static Specification<Expense> hasCategory(String category) {
            return (root, query, cb) ->
                    cb.equal(root.get("category"), category);
        }
    public static Specification<Expense> titleContains(String keyword) {

        return (root, query, cb) ->

                cb.like(
                        cb.lower(root.get("title")),
                        "%" + keyword.toLowerCase() + "%"
                );
    }
    public static Specification<Expense> amountGreaterThan(BigDecimal amount) {

        return (root, query, cb) ->
                cb.greaterThan(root.get("amount"), amount);
    }

    public static Specification<Expense> amountLessThan(BigDecimal amount) {
            return (root, query, cb) ->
                    cb.lessThan(root.get("amount"), amount);
    }
    public static Specification<Expense> descriptionContains(String description) {
            return (root, query, cb) ->
                    cb.like(cb.lower(root.get("description")), description.toLowerCase() + "%");
    }

}
