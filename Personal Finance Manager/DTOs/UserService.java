@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest req) {
        if (userRepo.findByUsername(req.username).isPresent()) {
            throw new ConflictException("User already exists");
        }
        User user = new User();
        user.setUsername(req.username);
        user.setPassword(passwordEncoder.encode(req.password));
        user.setFullName(req.fullName);
        user.setPhoneNumber(req.phoneNumber);
        return userRepo.save(user);
    }

    public User login(LoginRequest req) {
        User user = userRepo.findByUsername(req.username)
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(req.password, user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return user;
    }
}
