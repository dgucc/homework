package demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

   @Schema(description = "Amount of an anticipated payment", required = true, example = "4500")
   private BigDecimal amount;
   
   @Schema(description = "Date of an anticipated payment", required = true, example = "2023-09-01")
   private LocalDate paymentDate;
}
