package Homework_Databases.HospitalDatabase;

import Homework_Databases.HospitalDatabase.base.BaseEntity;
import Homework_Databases.SalesDatabase.Sale;
import Homework_Databases.UniversitySystem.Course;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "picture")
    @Lob
    private Byte[] picture;
    @Column(name = "has_medical_insurance")
    private boolean hasMedicalInsurance;
    @ManyToMany
    @JoinTable(
            name = "patients_diagnoses",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "diagnose_id") }
    )
    private List<Diagnose> diagnoses;
    @ManyToMany
    @JoinTable(
            name = "patients_medicaments",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "medicament_id") }
    )
    private List<Medicament> medicaments;
    @OneToMany(mappedBy = "patient")
    private List<Visitation> visitations;
}
