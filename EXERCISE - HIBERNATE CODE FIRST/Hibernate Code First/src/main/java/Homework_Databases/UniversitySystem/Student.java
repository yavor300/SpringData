package Homework_Databases.UniversitySystem;

import Homework_Databases.UniversitySystem.base.Person;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends Person {
    @Column(name = "average_grade")
    private Double averageGrade;
    @Column(name = "attendance")
    private Double attendance;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "students_courses",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    private List<Course> courses;
}
