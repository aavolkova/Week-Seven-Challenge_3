package me.anna.demo.repositories;


import me.anna.demo.models.Education;
import me.anna.demo.models.Job;
import me.anna.demo.models.Person;
import me.anna.demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PersonRepository extends CrudRepository<Person,Long> {

    Person findByUsername(String username);

    Person findByEmailAddress(String emailAddress);

    Long countByEmailAddress(String emailAddress);

    Long countByUsername(String username);

    Person findById(Long id);

//    Iterable<Person> findAllByByEducationalAchievements_EducationalInstitution(String university);
//    Iterable<Person> findAllByEducationalAchievements_
    Iterable<Person> findAllByFirstNameAndLastName (String firstName, String lastName);

    List<Job> findAllBySkillsWithRatingIn(Collection<Skills> collectionOfSkills);

    List<Person> findAllByEducationalAchievementsIn(Set<Education> e);
}
