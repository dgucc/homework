package demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

   @Schema(description = "Amount of an anticipated payment", required = true, example = "3000")
   @NotNull(message="amount cannot be null")
   @Positive(message="amount must be positive")
   private BigDecimal amount;
   
   @Schema(description = "Date of an anticipated payment", required = true, example = "2023-08-01")
   @NotNull(message="paymentDate cannot be null")
   private LocalDate paymentDate;
}
