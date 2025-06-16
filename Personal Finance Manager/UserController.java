// PACKAGE: com.example.finance.entity

@Entity
public class User {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // email

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    // Getters and setters
}

@Entity
public class Category {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String type; // INCOME/EXPENSE
    private boolean isCustom;

    @ManyToOne
    private User user; // null if system-defined
}

@Entity
public class Transaction {
    @Id @GeneratedValue
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private String type; // INCOME/EXPENSE

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;
}

@Entity
public class Goal {
    @Id @GeneratedValue
    private Long id;
    private String goalName;
    private BigDecimal targetAmount;
    private LocalDate targetDate;
    private LocalDate startDate;

    @ManyToOne
    private User user;
}

// PACKAGE: com.example.finance.dto

public class RegisterRequest {
    public String username;
    public String password;
    public String fullName;
    public String phoneNumber;
}

public class LoginRequest {
    public String username;
    public String password;
}

// PACKAGE: com.example.finance.repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserOrUserIsNull(User user);
}

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
}

// PACKAGE: com.example.finance.service

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setFullName(request.fullName);
        user.setPhoneNumber(request.phoneNumber);
        return userRepository.save(user);
    }

    public User authenticate(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}


