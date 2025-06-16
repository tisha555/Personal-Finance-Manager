public class CategoryRequest {
    @NotBlank
    public String name;

    @Pattern(regexp = "^(INCOME|EXPENSE)$")
    public String type;
}
