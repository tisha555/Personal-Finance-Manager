@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/monthly/{year}/{month}")
    public MonthlyReport getMonthlyReport(
            @PathVariable int year,
            @PathVariable int month,
            HttpSession session) {
        User user = getUserFromSession(session);
        return reportService.generateMonthlyReport(user, year, month);
    }

    @GetMapping("/yearly/{year}")
    public YearlyReport getYearlyReport(
            @PathVariable int year,
            HttpSession session) {
        User user = getUserFromSession(session);
        return reportService.generateYearlyReport(user, year);
    }

    private User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Please log in");
        return user;
    }
}
