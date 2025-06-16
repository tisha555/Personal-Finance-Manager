@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<TransactionResponse> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            HttpSession session) {

        User user = getUserFromSession(session);
        return transactionService.getTransactions(user, startDate, endDate, category);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest req,
            HttpSession session) {
        User user = getUserFromSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(req, user));
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequest req,
            HttpSession session) {
        User user = getUserFromSession(session);
        return transactionService.update(id, req, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(
            @PathVariable Long id,
            HttpSession session) {
        User user = getUserFromSession(session);
        transactionService.delete(id, user);
        return ResponseEntity.ok(Map.of("message", "Transaction deleted successfully"));
    }

    private User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Please log in");
        return user;
    }
}
