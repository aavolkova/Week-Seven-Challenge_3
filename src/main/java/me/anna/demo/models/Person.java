package me.anna.demo.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    @NotEmpty
    @Size(min=1, max=50, message = "Must enter your first name.")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Size(min=1, max=50, message = "Must enter your last name.")
    private String lastName;

    @Column(name = "email", nullable  = false)
    @Size(min=1, max=50, message = "Must enter your email address.")
    @Email
    private String emailAddress;

    @Column(name = "password")
    private String password;


    @Column(name = "enabled")
    private boolean enabled;    //Active user or not

    @Column(name = "username")
    private String username;

    //================ ONE-TO-MANY: ================
    //=== Add Relationship - Person and Education ===
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Education> educationalAchievements;

    //=== Add Relationship - Person and Employment ===
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Employment> workExperience;

    //=== Add Relationship - Person and Job ===
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Job> jobs;



// ==== OBSOLETE:
//    //=== Add Relationship - Person and Skills ===
//    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<Skills> skillsWithRating;



    //================ MANY-TO-MANY: ================
    //=== MANY-TO-MANY: === Person and ROLE ===
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "userz_id"),
        inverseJoinColumns = @JoinColumn(name = "rolez_id"))
    private Collection<Role> roles;

    //=== MANY-TO-MANY: === Person and Skill ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Collection<Skills> skillsWithRating;


    // ====== Constructor (we can also do it in the controller) =======
    public Person(){
        setEducationalAchievements(new HashSet<>());
        setWorkExperience(new HashSet<>());
        this.skillsWithRating=new ArrayList<Skills>();
        this.roles=new ArrayList<Role>();
        jobs = new HashSet<>();

    }



// ==== OBSOLETE:
//    // ========== Custom methods: ==========
//    // 1.1) ====== Delete Skill from Set
//    public void removeSkill(Skills s) {
//        skillsWithRating.remove(s);    }
//
//    // 1.2) ====== Add a Skill: =========
//    public void addSkill(Skills s){
//        s.setPerson(this);  //set person with this object (set Person)
//        skillsWithRating.add(s);    }
// ===========================================



    // ======= CUSTOM METHODS for ONE-TO-MANY: ========
    // 2.1) ====== Delete Education from Set
    public void removeEducation(Education e) {
        educationalAchievements.remove(e);
    }

    // 2.2) ====== Add a Education: =========
    public void addEducation(Education e){
        e.setPerson(this);  //set person with this object (set Person for education)
        educationalAchievements.add(e);
    } // ==============================



    // 3.1) ====== Delete Employment from Set
    public void removeEmployment(Employment w) {
        workExperience.remove(w);
    }

    // 3.2) ====== Add an Employment: =========
    public void addEmployment(Employment w){
        w.setPerson(this);
        workExperience.add(w);
    } // ==============================



    // 1.1) ====== Delete Job from Set
    public void removeJob(Job j) {
        jobs.remove(j);
    }

    // 1.2) ====== Add a Job to Set: =========
    public void addJob(Job j){
        j.setPerson(this);  //set person with this object (set Person for job)
        jobs.add(j);
    } // ==============================




    //==== Setters and Getters ====
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    //=== New Getters and Setters ===
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    // ====== Setter and Getter for Relationship - Person and Education
    public Set<Education> getEducationalAchievements() {
        return educationalAchievements;
    }
    public void setEducationalAchievements(Set<Education> educationalAchievements) {
        this.educationalAchievements = educationalAchievements;    }

    // ====== Setter and Getter for Relationship - Person and Employment
    public Set<Employment> getWorkExperience() {
        return workExperience;
    }
    public void setWorkExperience(Set<Employment> workExperience) {
        this.workExperience = workExperience;
    }

    // ====== Setter and Getter for Relationship - Person and Job
    public Set<Job> getJobs() { return jobs; }
    public void setJobs(Set<Job> jobs) { this.jobs = jobs; }





    // ======= CUSTOM METHODS for ONE-TO-MANY: ========

    // ====== NEW Setter and Getter for Relationship - Person and Skills
    public void setSkillsWithRating(Collection<Skills> skillsWithRating) { this.skillsWithRating = skillsWithRating; }
    public Collection<Skills> getSkillsWithRating() { return skillsWithRating; }

    // ====== NEW Setter and Getter for Relationship - Person and Role
    public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }






    // ======= CUSTOM METHODS for MANY-TO-MANY: ========

    //==== Method to ADD the ROLE to User's collection of Roles ====
    public void addRole(Role role)
    {
        this.roles.add(role);
    }



    //==== Method to ADD the Skill to Person's collection of Skills ====
    public void addSkill(Skills skill)
    {
        this.skillsWithRating.add(skill);
    }



    //==== Method to REMOVE the Skill from Person's collection of Skills ====
    public void removeSkill(Skills skill)
    {
        this.skillsWithRating.remove(skill);
    }

}
