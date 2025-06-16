package DTOs;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

public public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndUserOrIsCustomFalse(String name, User user);
    List<Category> findByUserOrIsCustomFalse(User user);
}

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
}
 {
    
}
