package me.anna.demo.services;



import me.anna.demo.models.Person;
//import me.anna.demo.models.Userz;
import me.anna.demo.repositories.PersonRepository;
import me.anna.demo.repositories.RoleRepo;
//import me.anna.demo.repositories.UserzRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

//    @Autowired
//    UserzRepo uRepo;

    @Autowired
    PersonRepository uRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    public UserService(PersonRepository uRepo){
        this.uRepo = uRepo;
    }

    public Person findByEmailAddress(String emailAddress){
        return uRepo.findByEmailAddress(emailAddress);
    }


    public Long countByEmail(String email) {
        return uRepo.countByEmailAddress (email);

    }

    public Person findByUsername(String username){
        return uRepo.findByUsername(username);
    }





    public void saveSeeker (Person user){
        user.setRoles(Arrays.asList(roleRepo.findByRole("SEEKER")));
        user.setEnabled(true);
        uRepo.save(user);
    }


    public void saveRecruiter (Person user){
        user.setRoles(Arrays.asList(roleRepo.findByRole("RECRUITER")));
        user.setEnabled(true);
        uRepo.save(user);
    }





//
//    public void saveUserz (Userz user){
//        user.setRoles(Arrays.asList(roleRepo.findByRole("USER")));
//        user.setEnabled(true);
//        uRepo.save(user);
//    }
//
//
//    public void saveAdmin (Userz user){
//        user.setRoles(Arrays.asList(roleRepo.findByRole("ADMIN")));
//        user.setEnabled(true);
//        uRepo.save(user);
//    }


}
