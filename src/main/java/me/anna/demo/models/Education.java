package me.anna.demo.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Educational Achievements
 */

@Entity
public class Education {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=1, max=50)
    private String degreeTitle;

    @NotEmpty
    @Size(min=1, max=50)
    private String educationalInstitution;

    @NotNull
    @Min(1930)
    @Max(2023)
    private Integer graduateDate;



    // Add Relationship - Person and Employment
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;




    // ==== Setters and Getters ====

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public void setDegreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
    }

    public String getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(String educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    public Integer getGraduateDate() {
        return graduateDate;
    }

    public void setGraduateDate(Integer graduateDate) {
        this.graduateDate = graduateDate;
    }



    // Setter and Getter for Relationship - Person and Education
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
