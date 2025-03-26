package demo.model;

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
public class VAResponse {

    @Schema(description = "Is the first activity of the company?", required = true, example = "true")
    private Boolean isFirstActivity;

    @Schema(description = "Starting date of a tax year", required = true, example = "2023-06-01")
    private LocalDate taxYearStartDate;

    @Schema(description = "Balance sheet date", required = true, example = "2023-12-31")
    private LocalDate balanceDate;

    @Schema(description = "Calculated fictive balance date [for test purpose]", required = false)
    private LocalDate fictiveBalanceDate;

    @Schema(description = "List of VA Periods Details ", required = false)
    private List<VAPeriodDetail> vaPeriods;
}
