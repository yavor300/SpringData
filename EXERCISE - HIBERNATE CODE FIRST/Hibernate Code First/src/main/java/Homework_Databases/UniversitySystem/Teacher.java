package Homework_Databases.UniversitySystem;

import Homework_Databases.UniversitySystem.base.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher extends Person {
    @Column(name = "email")
    private String email;
    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
}
