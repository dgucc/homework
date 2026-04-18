package demo.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateOfLateInterestResponse {

    @Schema(description = "Calculated due date of interest : [yyyy-mm-dd]", required = true, example = "2026-07-01")
    private LocalDate dateOfLateInterest;
    @Schema(description = "Calculation Rules applied")
    private List<String> calculationRules;
    

}
