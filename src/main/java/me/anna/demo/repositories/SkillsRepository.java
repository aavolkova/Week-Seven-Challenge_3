package me.anna.demo.repositories;


import me.anna.demo.models.Job;
import me.anna.demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface SkillsRepository extends CrudRepository<Skills,Long> {

    Skills findBySkillTitle(String SkillTitle);

    Iterable<Skills> findAllByJobsIsNotNull();

//    Iterable<Skills> findAllByPersonOrderById(Long id);



//    Iterable<Skills> findAll;

//    Iterable<Skills> findBy
//    Iterable<Skills> findAllByPersonIdOrderBySkillId(Long id);
//    findAllByOrderByIdAsc();


    Skills findOneBySkillTitle(String SkillTitle);

    Skills findBySkillTitleIn(Collection<Skills> collectionOfSkills);
//    List<Job> findAllBySkillsWithRatingIn(Collection<Skills> collectionOfSkills);

}
