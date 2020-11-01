package Homework_Databases.HospitalDatabase;

import Homework_Databases.HospitalDatabase.base.BaseEntity;
import Homework_Databases.SalesDatabase.StoreLocation;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    @Column(name = "date")
    private Date date;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
}
