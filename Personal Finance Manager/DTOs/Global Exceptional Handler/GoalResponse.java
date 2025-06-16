public class GoalResponse {
    public Long id;
    public String goalName;
    public BigDecimal targetAmount;
    public LocalDate targetDate;
    public LocalDate startDate;
    public BigDecimal currentProgress;
    public BigDecimal remainingAmount;
    public BigDecimal progressPercentage;

    public GoalResponse(Goal goal, BigDecimal progress, BigDecimal remaining, BigDecimal percent) {
        this.id = goal.getId();
        this.goalName = goal.getGoalName();
        this.targetAmount = goal.getTargetAmount();
        this.targetDate = goal.getTargetDate();
        this.startDate = goal.getStartDate();
        this.currentProgress = progress;
        this.remainingAmount = remaining;
        this.progressPercentage = percent;
    }
}
