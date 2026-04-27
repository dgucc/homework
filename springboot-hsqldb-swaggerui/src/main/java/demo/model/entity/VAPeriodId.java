package demo.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class VAPeriodId implements Serializable {
    @Column(name="N_I_ANNEE", nullable = false)
    private int taxYear;
    @Column(name="C_I_TYPE", nullable = false)
    private String taxYearType;
    @Column(name="N_I_YEAR_BILAN", nullable = false)
    private int yearOfBalance;
    @Column(name="N_I_MONTH_BILAN", nullable = false)
    private int monthOfBalance;
}