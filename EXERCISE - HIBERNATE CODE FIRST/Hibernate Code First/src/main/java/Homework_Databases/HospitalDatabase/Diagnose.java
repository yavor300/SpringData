package Homework_Databases.HospitalDatabase;

import Homework_Databases.HospitalDatabase.base.BaseEntity;
import Homework_Databases.UniversitySystem.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "comments")
    private String comment;
    @ManyToMany(mappedBy = "diagnoses")
    private List<Patient> patients;
}
