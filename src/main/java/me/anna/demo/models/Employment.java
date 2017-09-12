package me.anna.demo.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Work Experience
 */

@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=1, max=50)
    private String positionTitle;

    @NotEmpty
    @Size(min=1, max=50)
    private String organisation;

    // Start date (employment with the organisation)
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    /* JPA 2.1 was released before Java 8 and therefore doesn’t support the new Date and Time API.
    * I use LocalDateAttributeConverter class that implemented AttributeConverter interface.
    * https://www.thoughts-on-java.org/persist-localdate-localdatetime-jpa/
    * Attribute converter is part of the JPA 2.1 specification and can be used with any JPA 2.1 implementation, e.g. Hibernate or EclipseLink.
     */
    private LocalDate startDate;

    // End date (employment with the organisation)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    /* How to avoid BLOB value in Date column in MySQl table (when use LocalDate or LocalDateTime formats)
    * JPA not support LocalDate and LocalDateTime!:
    * Java 8: The new Date and Time API is well designed, easy to use and immutable.
    * You can use it, but JPA will map it to a BLOB instead of a DATE or TIMESTAMP.
    *
    * If you want to store a LocalDate attribute in a DATE column or a LocalDateTime in a TIMESTAMP column,
    * you need to define the mapping to java.sql.Date or java.sql.Timestamp yourself.
    * This one doesn't work for me:
    * @Column(name = "END_DATE", columnDefinition = "DATETIME", nullable = true)
    * In my case I use (instead of mapping) LocalDateAttributeConverter class that implemented AttributeConverter interface
    */
    private LocalDate endDate;

    // If person has just one duty for his position:
    @NotEmpty
    @Size(min=1, max=50)
    private String duty;


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

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }


    // Setter and Getter for Relationship - Person and Employment

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
