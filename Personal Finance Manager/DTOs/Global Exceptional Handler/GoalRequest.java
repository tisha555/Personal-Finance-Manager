public class GoalRequest {
    @NotBlank
    public String goalName;

    @Positive
    public BigDecimal targetAmount;

    @Future
    public LocalDate targetDate;

    public LocalDate startDate;
}
