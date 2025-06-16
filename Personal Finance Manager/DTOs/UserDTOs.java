public class RegisterRequest {
    @Email
    @NotBlank
    public String username;

    @NotBlank
    public String password;

    @NotBlank
    public String fullName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    public String phoneNumber;
}

public class LoginRequest {
    @Email
    @NotBlank
    public String username;

    @NotBlank
    public String password;
}
