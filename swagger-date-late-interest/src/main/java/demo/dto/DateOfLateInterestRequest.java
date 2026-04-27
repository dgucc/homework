package demo.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateOfLateInterestRequest {

    
    @SuppressWarnings("deprecation")
    @Schema(description = "Tax year (Bord.TAXYEAR) [yyyy]", required = true, example = "2025")
    @NotNull(message="taxYear cannot be null")
    private Integer taxYear;
    
    @SuppressWarnings("deprecation")
    @Schema(description = "Balance date (Bord.DATEBILAN) [yyyy-mm-dd]", required = true, example = "2024-12-31")
    @NotNull(message="balanceDate cannot be null")
    private LocalDate balanceDate;

    @SuppressWarnings("deprecation")
    @Schema(description = "Execution date (Bord.UITVOERDATUMMANU) [yyyy-mm-dd]", required = true, example = "2026-07-10")
    @NotNull(message="executionDate cannot be null")    
    private LocalDate executionDate;    

    @SuppressWarnings("deprecation")
    @Schema(description = "Sending date (Biz.VERZENDINGDATUM) [yyyy-mm-dd]", required = false, example = "2026-07-11")
    @NotNull(message="sendingDate cannot be null")
    private LocalDate sendingDate;
    
    @SuppressWarnings("deprecation")
    @Schema(description = "Rate (Bord.TAUX, Bord.TAUXPM) [yyyy]", required = true, example = "5000")
    @NotNull(message="rate cannot be null")
    private Integer rate;

    @SuppressWarnings("deprecation")
    @Schema(description = "With non standard interest (INTERETNONSTANDARD Art. 416)?", required = true, example = "false")
    @NotNull(message="withNonStandardInterest cannot be null")
    private Boolean withNonStandardInterest;
    
    // @Schema(description = "Due date of interest from Krol (Krol.KROLINTDATE) [yyyy-mm-dd]", required = true, example = "2026-12-30")
    // @NotNull(message="krolIntDate cannot be null")    
    // private LocalDate krolIntDate;
    
}
