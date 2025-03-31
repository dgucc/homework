package demo.model;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VARequest {
    
    @Schema(description = "Starting date of a tax year", required = true, example = "2023-06-01")
    @NotNull(message="taxYearStartDate cannot be null")
    private LocalDate taxYearStartDate;
    
    @Schema(description = "Balance sheet date", required = true, example = "2023-09-30")
    @NotNull(message="balanceDate cannot be null")
    private LocalDate balanceDate;
    
    @Schema(description = "Is the first activity of the company?", required = true, example = "false")
    @NotNull(message="isFirstActivity cannot be null")
    private Boolean isFirstActivity;

    @Schema(description = "List of payments", required = true)
    @Valid
    private List<Payment> payments;
}
