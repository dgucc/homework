package minfin.ovcogr.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StakeholdingDto {

    private String parentBce;
    private String parentName;
    private String country;
    private String childBce;
    private String childName;
    private LocalDate endStakeholding;
    private BigDecimal direct;
    private BigDecimal indirect;

}
