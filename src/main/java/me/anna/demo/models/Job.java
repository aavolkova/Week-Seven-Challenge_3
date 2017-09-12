package me.anna.demo.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class Job {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=1, max=50, message = "Must enter a title.")
    private String title;

    @NotEmpty
    @Size(min=1, max=50, message = "Must enter an employer.")
    private String employer;

//    @NotNull
//    @Min(0)
//    @Max(1000000)
    private Integer minSalary;


//    @NotNull
//    @Min(0)
    @Max(1000000)
    private Integer maxSalary;


    @NotEmpty
    @Size(min=1, max=100, message = "Must enter a description.")
    private String description;



//    //=== Add Relationship - Job and Skills ===
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(joinColumns = @JoinColumn(name = "job_id"),
//    inverseJoinColumns = @JoinColumn(name = "skills_id"))
//    private Collection<Skills> setOfSkills;
    // Skill is owner of Job
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "job_id"),
        inverseJoinColumns = @JoinColumn(name = "skills_id"))
    private Collection<Skills> skillsWithRating;



    //=== Add Relationship - Job and Person ===
    // One Person (RECRUITER) can post many Jobs
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    private Person person;





//    //==== Constractor ====
//    public Job()
//    {
//        this.setOfSkills=new ArrayList<Skills>();
//    }
//    //==== Constractor ====
    public Job()
    {
        this.skillsWithRating=new ArrayList<Skills>();
    }




    //==== Setters and Getters ====
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    // ====== Setter and Getter for Relationship - Job and Person
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

//    // ====== Setter and Getter for Relationship - Jobs and Skills
//    public Collection<Skills> getSetOfSkills() {
//        return setOfSkills;
//    }
//    public void setSetOfSkills(Collection<Skills> setOfSkills) {
//        this.setOfSkills = setOfSkills;
//    }
//
//
//
//
//    //==== Method to add the skill to Job's collection of skills "setOfSkills" ====
//    public void addSkill(Skills skill)
//    {
//        this.setOfSkills.add(skill);
//    }
        // ====== Setter and Getter for Relationship - Jobs and Skills
    public Collection<Skills> getSkillsWithRating() {
        return skillsWithRating;
    }
    public void setSkillsWithRating(Collection<Skills> setOfSkills) {
        this.skillsWithRating = setOfSkills;
    }




    //==== Method to add the skill to Job's collection of skills "setOfSkills" ====
    public void addSkill(Skills skill)
    {
        this.skillsWithRating.add(skill);
    }


    //==== Method to REMOVE the skill from Job's collection of skills "setOfSkills" ====
    public void removeSkill(Skills skill)
    {
        this.skillsWithRating.remove(skill);
    }

}
