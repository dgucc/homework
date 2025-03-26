package demo.repository;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.model.VAPeriodDetail;
import demo.model.entity.VAPeriodId;

@Repository
public interface VAPeriodRepository extends JpaRepository<VAPeriodDetail, VAPeriodId> {

    @Query(value="""
SELECT		
   N_I_ANNEE,
   C_I_TYPE AS TYPE,
   :amount AS VA_AMOUNT,
   :paymentDate AS PAYMENT_DATE,
   MIN(VA_TYPE) AS VA_PERIOD,
   MIN(VA_DATE) AS VA_DATE
FROM
   (
   SELECT N_I_ANNEE, C_I_TYPE, N_I_YEAR_BILAN, N_I_MONTH_BILAN, 'VA_PERIOD_1' AS VA_TYPE, D_I_DATE_VA1 AS VA_DATE FROM CALCISOC.VA_PERIOD WHERE D_I_DATE_VA1 IS NOT NULL 
   AND N_I_ANNEE=:taxYear AND C_I_TYPE=:taxYearType AND N_I_YEAR_BILAN=YEAR(CAST(:balanceDate AS DATE)) AND N_I_MONTH_BILAN=MONTH(CAST(:balanceDate AS DATE)) 
   UNION
   SELECT N_I_ANNEE, C_I_TYPE, N_I_YEAR_BILAN, N_I_MONTH_BILAN, 'VA_PERIOD_2' AS VA_TYPE, D_I_DATE_VA2 AS VA_DATE FROM CALCISOC.VA_PERIOD WHERE D_I_DATE_VA2 IS NOT NULL 
   AND N_I_ANNEE=:taxYear AND C_I_TYPE=:taxYearType AND N_I_YEAR_BILAN=YEAR(CAST(:balanceDate AS DATE)) AND N_I_MONTH_BILAN=MONTH(CAST(:balanceDate AS DATE)) 
   UNION
   SELECT N_I_ANNEE, C_I_TYPE, N_I_YEAR_BILAN, N_I_MONTH_BILAN, 'VA_PERIOD_3' AS VA_TYPE, D_I_DATE_VA3 AS VA_DATE FROM CALCISOC.VA_PERIOD WHERE D_I_DATE_VA3 IS NOT NULL 
   AND N_I_ANNEE=:taxYear AND C_I_TYPE=:taxYearType AND N_I_YEAR_BILAN=YEAR(CAST(:balanceDate AS DATE)) AND N_I_MONTH_BILAN=MONTH(CAST(:balanceDate AS DATE)) 
   UNION
   SELECT N_I_ANNEE, C_I_TYPE, N_I_YEAR_BILAN, N_I_MONTH_BILAN, 'VA_PERIOD_4' AS VA_TYPE, D_I_DATE_VA4 AS VA_DATE FROM CALCISOC.VA_PERIOD WHERE D_I_DATE_VA4 IS NOT NULL 
   AND N_I_ANNEE=:taxYear AND C_I_TYPE=:taxYearType AND N_I_YEAR_BILAN=YEAR(CAST(:balanceDate AS DATE)) AND N_I_MONTH_BILAN=MONTH(CAST(:balanceDate AS DATE)) 
   ) AS T
WHERE
   VA_DATE >= CAST(:paymentDate AS DATE)
GROUP BY 
   N_I_ANNEE,C_I_TYPE
HAVING
   MIN(VA_DATE) >= CAST(:paymentDate AS DATE)
;""", nativeQuery=true)
    VAPeriodDetail getVAPeriod(
        @Param("taxYear") long taxYear,
        @Param("taxYearType") String taxYearType,
        @Param("balanceDate") Date balanceDate,
        @Param("amount") BigDecimal amount, 
        @Param("paymentDate") Date paymentDate
    );

}
