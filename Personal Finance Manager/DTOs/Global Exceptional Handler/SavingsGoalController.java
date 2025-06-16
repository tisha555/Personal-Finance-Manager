@RestController
@RequestMapping("/api/goals")
public class SavingsGoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping
    public List<GoalResponse> getAllGoals(HttpSession session) {
        User user = getUserFromSession(session);
        return goalService.getAllGoals(user);
    }

    @GetMapping("/{id}")
    public GoalResponse getGoal(@PathVariable Long id, HttpSession session) {
        User user = getUserFromSession(session);
        return goalService.getGoal(id, user);
    }

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(
            @Valid @RequestBody GoalRequest req,
            HttpSession session) {
        User user = getUserFromSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.createGoal(req, user));
    }

    @PutMapping("/{id}")
    public GoalResponse updateGoal(
            @PathVariable Long id,
            @RequestBody GoalRequest req,
            HttpSession session) {
        User user = getUserFromSession(session);
        return goalService.updateGoal(id, req, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long id, HttpSession session) {
        User user = getUserFromSession(session);
        goalService.deleteGoal(id, user);
        return ResponseEntity.ok(Map.of("message", "Goal deleted successfully"));
    }

    private User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Please log in");
        return user;
    }
}
