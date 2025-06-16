@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll(HttpSession session) {
        User user = getUserFromSession(session);
        return categoryService.getAllCategories(user);
    }

    @PostMapping
    public ResponseEntity<Category> create(
            @Valid @RequestBody CategoryRequest req,
            HttpSession session) {
        User user = getUserFromSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCustomCategory(req, user));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name, HttpSession session) {
        User user = getUserFromSession(session);
        categoryService.deleteCustomCategory(name, user);
        return ResponseEntity.ok(Map.of("message", "Category deleted successfully"));
    }

    private User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Please log in");
        return user;
    }
}
