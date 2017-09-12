package me.anna.demo.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Skills With Rating
 */

@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=1, max=50, message = "Must enter your skill.")
    private String skillTitle;

    @NotEmpty
    @Size(min=1, max=50)
    private String skillRating;




    //=== MANY-TO-MANY: Person and Skills ===
    @ManyToMany(mappedBy="skillsWithRating")
    private Collection<Person> persons;


//////////////////////////////////////
    //=== MANY-TO-MANY: Job and Skills ===
    //=== Add New Relationship:
//    @ManyToMany(mappedBy = "setOfSkills")
//    private Collection<Job> jobs;
    // Skill owns Job
    @ManyToMany(mappedBy = "skillsWithRating", fetch = FetchType.EAGER)
    private Collection<Job> jobs;



    // ======== Constructor ========
    public Skills()
    {
        this.persons=new ArrayList<Person>();
        this.jobs=new ArrayList<Job>();
    }






    // ==== Setters and Getters ====

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }

    public String getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(String skillRating) {
        this.skillRating = skillRating;
    }




    //=== Setter and getter for Relationship - Jobs and Skills
    public Collection<Job> getJobs() {
        return jobs;
    }
    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }


    //=== Setter and getter for Relationship - Persons and Skills
    public Collection<Person> getPersons() { return persons;  }
    public void setPersons(Collection<Person> persons) { this.persons = persons; }






    //==== Method to add Job object to collections of jobs that have a particular Skill
    // === (in my case the class name is Skills not Skill) ===
    public void addJob (Job job){
        this.jobs.add(job);
    }
    public void removeJob(Job job) {
        this.jobs.remove(job);
    }


    //==== Method to add Person object to collections of Persons that have a particular Skill
    // === (in my case the class name is Skills not Skill) ===
    public void addPerson (Person person){
        this.persons.add(person);
    }


    //==== Method to remove Person object from collections of Persons that have a particular Skill
    // === (in my case the class name is Skills not Skill) ===
    public void removePerson (Person person){
        this.persons.remove(person);
    }








}
