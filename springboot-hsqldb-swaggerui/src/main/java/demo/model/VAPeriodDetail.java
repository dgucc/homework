package demo.model;

import java.math.BigDecimal;
import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VAPeriodDetail{
   @Id
   @Schema(description = "Amount of an anticipated payment", required = true, example = "4500")
   @Column(name="VA_AMOUNT", nullable = false)
   private BigDecimal amount;

   @Schema(description = "Date of an anticipated payment", required = true, example = "2023-09-01")
   @Column(name="PAYMENT_DATE")
   private Date paymentDate;
   
   @Schema(description = "Specific VA Period ", required = true, example = "VA_PERIOD_3")
   @Column(name="VA_PERIOD")
   private String vaPeriod;
   
   @Schema(description = "Limitative VA Date", required = true, example = "2023-10-11")
   @Column(name="VA_DATE")
   private Date vaMaxDate;
   
   @Schema(description = "Identified type (normal of special) of tax year [for test purpose]", required = true, example = "NORM")
   @Column(name="TYPE")
   private String taxYearType;
}

