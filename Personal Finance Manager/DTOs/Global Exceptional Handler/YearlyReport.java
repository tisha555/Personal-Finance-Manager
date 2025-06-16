public class YearlyReport {
    public int year;
    public Map<String, BigDecimal> totalIncome;
    public Map<String, BigDecimal> totalExpenses;
    public BigDecimal netSavings;

    public YearlyReport(int year,
            Map<String, BigDecimal> income,
            Map<String, BigDecimal> expenses,
            BigDecimal net) {
        this.year = year;
        this.totalIncome = income;
        this.totalExpenses = expenses;
        this.netSavings = net;
    }
}
