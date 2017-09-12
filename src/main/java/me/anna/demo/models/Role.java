package me.anna.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
//    private Collection<Person> users;
    private Collection<Person> persons;


    // ==== Constructor ====
//    public Role()
//    {
//        this.users=new ArrayList<Person>();
//    }
    public Role()
    {
        this.persons=new ArrayList<Person>();
    }


    // ==== Setters and Getters ====

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public Collection<Person> getUsers() {
//        return users;
//    }
//    public void setUsers(Collection<Person> users) {
//        this.users = users;
//}
    public Collection<Person> getUsers() {
        return persons;
    }
    public void setUsers(Collection<Person> users) {
        this.persons = users;
    }



    //==== Method to add User object to collections of users that have a particular Role ====
//    public void addUser (Person user){
//        this.users.add(user);
//    }
    public void addUser (Person user){
        this.persons.add(user);
    }


}
