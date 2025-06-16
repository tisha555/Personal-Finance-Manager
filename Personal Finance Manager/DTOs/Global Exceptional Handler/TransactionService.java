@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;
    @Autowired
    private CategoryRepository categoryRepo;

    public List<TransactionResponse> getTransactions(User user, LocalDate start, LocalDate end, String categoryName) {
        List<Transaction> list = repo.findByUserAndDateBetween(user,
                start != null ? start : LocalDate.MIN,
                end != null ? end : LocalDate.now());

        return list.stream()
                .filter(tx -> categoryName == null || tx.getCategory().getName().equalsIgnoreCase(categoryName))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse create(TransactionRequest req, User user) {
        Category cat = categoryRepo.findByNameAndUserOrIsCustomFalse(req.category, user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Transaction tx = new Transaction();
        tx.setAmount(req.amount);
        tx.setDate(req.date);
        tx.setDescription(req.description);
        tx.setCategory(cat);
        tx.setType(cat.getType());
        tx.setUser(user);
        return toResponse(repo.save(tx));
    }

    public TransactionResponse update(Long id, TransactionRequest req, User user) {
        Transaction tx = repo.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        tx.setAmount(req.amount);
        tx.setDescription(req.description);
        return toResponse(repo.save(tx));
    }

    public void delete(Long id, User user) {
        Transaction tx = repo.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        repo.delete(tx);
    }

    private TransactionResponse toResponse(Transaction tx) {
        TransactionResponse res = new TransactionResponse();
        res.id = tx.getId();
        res.amount = tx.getAmount();
        res.date = tx.getDate();
        res.description = tx.getDescription();
        res.category = tx.getCategory().getName();
        res.type = tx.getType();
        return res;
    }
}
