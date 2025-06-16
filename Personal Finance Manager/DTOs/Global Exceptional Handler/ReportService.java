@Service
public class ReportService {

    @Autowired
    private TransactionRepository txRepo;

    public MonthlyReport generateMonthlyReport(User user, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<Transaction> txs = txRepo.findByUserAndDateBetween(user, start, end);

        return buildReport(year, month, txs);
    }

    public YearlyReport generateYearlyReport(User user, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        List<Transaction> txs = txRepo.findByUserAndDateBetween(user, start, end);

        return buildYearlyReport(year, txs);
    }

    private MonthlyReport buildReport(int year, int month, List<Transaction> txs) {
        Map<String, BigDecimal> income = new HashMap<>();
        Map<String, BigDecimal> expense = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction tx : txs) {
            Map<String, BigDecimal> target = tx.getType().equals("INCOME") ? income : expense;
            target.merge(tx.getCategory().getName(), tx.getAmount(), BigDecimal::add);

            if (tx.getType().equals("INCOME"))
                totalIncome = totalIncome.add(tx.getAmount());
            else
                totalExpense = totalExpense.add(tx.getAmount());
        }

        return new MonthlyReport(month, year, income, expense, totalIncome.subtract(totalExpense));
    }

    private YearlyReport buildYearlyReport(int year, List<Transaction> txs) {
        Map<String, BigDecimal> income = new HashMap<>();
        Map<String, BigDecimal> expense = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction tx : txs) {
            Map<String, BigDecimal> target = tx.getType().equals("INCOME") ? income : expense;
            target.merge(tx.getCategory().getName(), tx.getAmount(), BigDecimal::add);

            if (tx.getType().equals("INCOME"))
                totalIncome = totalIncome.add(tx.getAmount());
            else
                totalExpense = totalExpense.add(tx.getAmount());
        }

        return new YearlyReport(year, income, expense, totalIncome.subtract(totalExpense));
    }
}
