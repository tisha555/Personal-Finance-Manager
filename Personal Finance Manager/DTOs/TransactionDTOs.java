package DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

public public class TransactionRequest {
    @Positive
    public BigDecimal amount;

    @PastOrPresent
    public LocalDate date;

    @NotBlank
    public String category;

    public String description;
}

public class TransactionResponse {
    public Long id;
    public BigDecimal amount;
    public LocalDate date;
    public String category;
    public String type;
    public String description;
}
 {
    
}
