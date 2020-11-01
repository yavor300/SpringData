package Homework_Databases.HospitalDatabase;

import Homework_Databases.HospitalDatabase.base.BaseEntity;
import Homework_Databases.UniversitySystem.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "medicaments")
    private List<Patient> patients;
}
