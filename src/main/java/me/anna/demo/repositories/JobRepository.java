package me.anna.demo.repositories;

import me.anna.demo.models.Job;
import me.anna.demo.models.Person;
import me.anna.demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface JobRepository extends CrudRepository<Job,Long> {

//    Collection<Job> findAllByPersonIs(Person person);
//    List<Job> findAllByPersonId(Person person);

//    List<Job> findAllByPersonId(long RecruiterPersonId);



    ArrayList<Job> findAllBySkillsWithRatingEquals(String skillTitle);
    ArrayList<Job> findBySkillsWithRatingEquals(String skillTitle);

    //No property containingSkillFindBySkillTitle found for type Skills!:::::
//    List<Job> findAllBySkillsWithRatingContainingSkillFindBySkillTitle(String skillTitle);


//    List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);


    List<Job> findJobDistinctBySkillsWithRating(String skillTitle);
    List<Job> findJobBySkillsWithRating(String skillTitle);

    Collection<Job> findAllByPersonId(long RecruiterPersonId);




    Iterable<Job> findAllByEmployerContains(String company);
    Iterable<Job> findAllByTitleContains(String title);


//    List<Job> findAllSkillsIn(Collection<Skills> collectionOfSkills);
    List<Job> findAllBySkillsWithRatingIn(Collection<Skills> collectionOfSkills);

}