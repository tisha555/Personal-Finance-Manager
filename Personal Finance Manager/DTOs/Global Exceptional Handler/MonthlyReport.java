public class MonthlyReport {
    public int month;
    public int year;
    public Map<String, BigDecimal> totalIncome;
    public Map<String, BigDecimal> totalExpenses;
    public BigDecimal netSavings;

    public MonthlyReport(int month, int year,
            Map<String, BigDecimal> income,
            Map<String, BigDecimal> expenses,
            BigDecimal net) {
        this.month = month;
        this.year = year;
        this.totalIncome = income;
        this.totalExpenses = expenses;
        this.netSavings = net;
    }
}
