@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private TransactionRepository transactionRepo;

    public List<Category> getAllCategories(User user) {
        return categoryRepo.findByUserOrIsCustomFalse(user);
    }

    public Category createCustomCategory(CategoryRequest req, User user) {
        Optional<Category> exists = categoryRepo.findByNameAndUserOrIsCustomFalse(req.name, user);
        if (exists.isPresent())
            throw new ConflictException("Category already exists");

        Category category = new Category();
        category.setName(req.name);
        category.setType(req.type.toUpperCase());
        category.setCustom(true);
        category.setUser(user);
        return categoryRepo.save(category);
    }

    public void deleteCustomCategory(String name, User user) {
        Category cat = categoryRepo.findByNameAndUserOrIsCustomFalse(name, user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!cat.isCustom() || !cat.getUser().getId().equals(user.getId()))
            throw new ForbiddenException("Cannot delete this category");

        boolean used = transactionRepo.findAll().stream()
                .anyMatch(tx -> tx.getCategory().getId().equals(cat.getId()));
        if (used)
            throw new ConflictException("Category in use");

        categoryRepo.delete(cat);
    }
}
