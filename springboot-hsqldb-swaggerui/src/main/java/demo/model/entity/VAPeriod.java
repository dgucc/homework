package demo.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema="CALCISOC",name="VA_PERIOD")
public class VAPeriod {

   @EmbeddedId
   private VAPeriodId vaPeriodId;

   @Column(name="D_I_DATE_VA1",nullable = true)
   private Date dateVA1;

   @Column(name="D_I_DATE_VA2", nullable = false)
   private Date dateVA2;
   
   @Column(name="D_I_DATE_VA3", nullable = false)
   private Date dateVA3;
   
   @Column(name="D_I_DATE_VA4", nullable = false)
   private Date dateVA4;
  

}
