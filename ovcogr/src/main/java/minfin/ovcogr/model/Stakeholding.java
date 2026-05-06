package minfin.ovcogr.model;

import java.sql.Date;

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

@Entity
@Table(name="PARTICIPATION",schema = "PUBLIC")
public class Stakeholding {

    @Id
    // @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer year;
    private String parentBce;
    private String parentName;
    private String filingRef;
    private Date startDate;
    private Date endDate;
    private String childBce;
    private String childName;
    private String country;
    private Date endStakeholding;
    private Float direct;
    private Float indirect;



    // public Stakeholding(String parentBce, String parentName, String childBce, String childName, String country, Date endStakeholding, Float direct, Float indirect) {
    //     this.parentBce = parentBce;
    //     this.parentName = parentName;
    //     this.country = country;
    //     this.childBce = childBce;
    //     this.childName = childName;
    //     this.endStakeholding = endStakeholding;
    //     this.direct = direct;
    //     this.indirect = indirect;
    // }
}
