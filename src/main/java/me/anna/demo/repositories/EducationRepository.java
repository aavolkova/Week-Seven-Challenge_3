package me.anna.demo.repositories;


import me.anna.demo.models.Education;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Set;

public interface EducationRepository extends CrudRepository<Education, Long> {

    Iterable <Education> findAll();
    Set<Education> findByEducationalInstitution(String schoolOrUniversity);

}
