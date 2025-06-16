@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepo;
    @Autowired
    private TransactionRepository txRepo;

    public List<GoalResponse> getAllGoals(User user) {
        return goalRepo.findByUser(user).stream().map(goal -> calculate(goal, user)).toList();
    }

    public GoalResponse getGoal(Long id, User user) {
        Goal goal = goalRepo.findById(id)
                .filter(g -> g.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        return calculate(goal, user);
    }

    public GoalResponse createGoal(GoalRequest req, User user) {
        Goal goal = new Goal();
        goal.setGoalName(req.goalName);
        goal.setTargetAmount(req.targetAmount);
        goal.setTargetDate(req.targetDate);
        goal.setStartDate(req.startDate != null ? req.startDate : LocalDate.now());
        goal.setUser(user);
        return calculate(goalRepo.save(goal), user);
    }

    public GoalResponse updateGoal(Long id, GoalRequest req, User user) {
        Goal goal = goalRepo.findById(id)
                .filter(g -> g.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        goal.setTargetAmount(req.targetAmount);
        goal.setTargetDate(req.targetDate);
        return calculate(goalRepo.save(goal), user);
    }

    public void deleteGoal(Long id, User user) {
        Goal goal = goalRepo.findById(id)
                .filter(g -> g.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        goalRepo.delete(goal);
    }

    private GoalResponse calculate(Goal goal, User user) {
        LocalDate start = goal.getStartDate();
        LocalDate now = LocalDate.now();
        List<Transaction> txs = txRepo.findByUserAndDateBetween(user, start, now);

        BigDecimal income = txs.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = txs.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal progress = income.subtract(expense);
        BigDecimal remaining = goal.getTargetAmount().subtract(progress);
        BigDecimal percent = goal.getTargetAmount().compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : progress.multiply(BigDecimal.valueOf(100)).divide(goal.getTargetAmount(), 2, RoundingMode.HALF_UP);

        return new GoalResponse(goal, progress, remaining, percent);
    }
}
