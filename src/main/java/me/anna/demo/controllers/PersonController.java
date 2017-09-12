package me.anna.demo.controllers;
import me.anna.demo.models.*;
import me.anna.demo.repositories.*;
import me.anna.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
//@RequestMapping("/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    EmploymentRepository employmentRepository;
    @Autowired
    SkillsRepository skillsRepository;


    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserService userService;


    /////////////////////////////////////////////////////////////////
    // ===== Need to go to this route just ones,
    // ===== before the first registration of a first user of the App
    // ===== Set Up the Database: create two Roles:=====
    @RequestMapping("/createRoles")
    public @ResponseBody String createRolesInDatabase() {
        Role r1 = new Role();
        r1.setRole("SEEKER");
        roleRepo.save(r1);

        Role r2 = new Role();
        r2.setRole("RECRUITER");
        roleRepo.save(r2);
        return "two roles have been created";
    }
    /////////////////////////////////////////////////////////////////




    // ============ Display the Home page ============
    @GetMapping("/")
    public String showIndex(Model model)
    {
        String myMessage = "Welcome to the Robo Resume Application";
        model.addAttribute("message", myMessage);
        return "index";
    }


    // ============ Display the Login page ============
    @RequestMapping("/login")
//    public String login(Principal p){
    public String login(){
        return "login";
    }




    // ===== SEEKER's registration =====
    @RequestMapping(value="/registerSeeker", method = RequestMethod.GET)
    public String showRegistrationSeekerPage (Model model){

        model.addAttribute("user", new Person());
        return "registrationS";
    }

    @RequestMapping(value="/registerSeeker", method = RequestMethod.POST)
    public String processRegistrationSeekerPage (@Valid @ModelAttribute("user") Person person, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "registrationS";
        } else{
            userService.saveSeeker(person);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "index";
    }




    // ===== RECRUITER's registration =====
    @RequestMapping(value="/registerRecruiter", method = RequestMethod.GET)
    public String showRegistrationRecruiterPage (Model model){

        model.addAttribute("user", new Person());
        return "registrationR";
    }

    @RequestMapping(value="/registerRecruiter", method = RequestMethod.POST)
    public String processRegistrationRecruiterPage (@Valid @ModelAttribute("user") Person person, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "registrationR";
        } else{
            userService.saveRecruiter(person);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "index";
    }






    @RequestMapping("/welcome")
    public String welcome(Model model, Principal p)
    {

        System.out.println("welcome page: p username:"+ p.getName());
        long countAllSkills = skillsRepository.count();
        model.addAttribute("gotskills", countAllSkills);
        model.addAttribute("skillsList", skillsRepository.findAll());

        Person myPerson = personRepository.findByUsername(p.getName());
        model.addAttribute("currentPerson", myPerson );

        Person Seeker = personRepository.findById(new Long(2));



        Collection <Skills> myPersonSkills = myPerson.getSkillsWithRating();
        for(Skills s : myPersonSkills){

            System.out.println("Skill Title:"+ s.getSkillTitle());
            System.out.println("Skill Id:"+ s.getId());
        }

        //++++++++++++++++++++++ SEEKER ++++++++++++++++++++++++++++++
        ///++++++++++ JOB NOTIFICATIONS FOR Job's SEEKER: ++++++++++++

//        System.out.println(skillsRepository.count());
//
//        if (skillsRepository.count() == 0) {
//            return "setDBSeeker";
//        }


/*
        if(myPerson.getRoles().equals("SEEKER")){// && skillsRepository.count() == 0) {


            if (skillsRepository.count() == 0) {
                return "setDBseeker";
            }
            Long count = skillsRepository.count();
            model.addAttribute("count", count);

            Iterable<Job> jobsList = jobRepository.findAll();

            Iterable<Skills> skillsOfSeeker = Seeker.getSkillsWithRating();

            for (Skills s : skillsOfSeeker) {
                System.out.println("All Skills Titles of Seeker: " + s.getSkillTitle());
            }

            Iterable<Skills> AllSkillsWithJobs = skillsRepository.findAllByJobsIsNotNull();
            Collection<Skills> matchedSkills = new ArrayList<>();
            ArrayList<String> matchedSkillsTitles = new ArrayList<>();  // I need to add here the all matched skills found
            for (Skills seekerS : skillsOfSeeker) {
                for (Skills jobS : AllSkillsWithJobs) {
                    if (seekerS.getSkillTitle().equalsIgnoreCase(jobS.getSkillTitle())) {
                        matchedSkillsTitles.add(jobS.getSkillTitle());
                        matchedSkills.add(skillsRepository.findBySkillTitle(jobS.getSkillTitle()));
                    }
                }
            }

            int count = 0;
            for (String s : matchedSkillsTitles) {
                count += 1;
            }

            if (count == 0) {
                return "setDBseeker";
            }

            if (count >= 1) {


                model.addAttribute("listOfAllMatchedSkillsTitles", matchedSkillsTitles);
                model.addAttribute("listOfAllMatchedSkills", matchedSkills);

                Collection<Job> matchedJobs = new ArrayList<Job>();
                Long jobId = 0L;
                ArrayList<Long> matchedJobsIds = new ArrayList<>();

                for (String oneSkillMatchedTitle : matchedSkillsTitles) {
                    System.out.println("Matched Skill Title: " + oneSkillMatchedTitle);

                    Skills oneMatchedSkill = skillsRepository.findBySkillTitle(oneSkillMatchedTitle);
                    Collection<Job> j1;
                    j1 = oneMatchedSkill.getJobs();
                    // Add one collection to another:
                    //     c1.addAll(c2);

                    matchedJobs.addAll(j1);
                    for (Job j : j1) {
                        System.out.println("Matched Job Title: " + j.getTitle());
                    }
                }
                model.addAttribute("collectionOfAllMatchedJobs", matchedJobs);
                return "welcome";
            }
        }*/

        long countMatchedJobs = 0;
        List<Job> listOfJobsWithMatchedSkills = jobRepository.findAllBySkillsWithRatingIn(myPerson.getSkillsWithRating());

        for (Job jobs : listOfJobsWithMatchedSkills) {

            System.out.println("Matched Skill Title: " + jobs.getTitle());
            countMatchedJobs += 1;
//            System.out.println("Matched Skill Title: " + oneSkillMatchedTitle);
        }



        if(countMatchedJobs >= 1){
            model.addAttribute("collectionOfAllMatchedJobs", listOfJobsWithMatchedSkills);
        }

        if(countMatchedJobs == 0){
            listOfJobsWithMatchedSkills = new ArrayList<Job>();
            model.addAttribute("collectionOfAllMatchedJobs", listOfJobsWithMatchedSkills);
        }

        if(countAllSkills == 0){
            listOfJobsWithMatchedSkills = new ArrayList<Job>();
            model.addAttribute("collectionOfAllMatchedJobs", listOfJobsWithMatchedSkills);
        }

        return "welcome";

    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    // ================== DISPLAY FINAL RESUME ================
    // ===== Display Person's info saved to the database ======
    // == Person can only have one role? and unique username ==
    @GetMapping("/displayPersonAllInfo")
//    public String showAllPersonsInfo(Person person, Model model, Principal principal)
    public String showAllPersonsInfo(Model model, Principal principal)
    {
//        System.out.println("displayPersonAllInfo page: p.getname:"+ principal.getName());
        Person myPerson = personRepository.findByUsername(principal.getName());
//        System.out.println("p.getname:"+ principal.getName());
//        System.out.println(principal.getName());
        model.addAttribute("person", myPerson );


        long countAllSkills = skillsRepository.count();
        model.addAttribute("gotskills", countAllSkills);


        return "displayPersonAllInfo";
    }


    // ================ DISPLAY RECRUITER ALL POSTED JOBS ==============
    @GetMapping("/displayRecruiterAllJobPosts")
    public String showAllJobsForThisRecruiter(Model model, Principal principal)
    {
        Person myPerson = personRepository.findByUsername(principal.getName());
        //+++++++ BOTH WAYS WORK !!!!!!! +++++++++
        //Way1:
        Iterable <Job> jobslist = myPerson.getJobs();
        model.addAttribute("alljobs", jobslist);
        //Way2:
        //  Iterable <Job> joblist = jobRepository.findAllByPersonId(myPerson.getId());
        //  model.addAttribute("alljob", joblist);
        model.addAttribute("person", myPerson );
        return "displayRecruiterAllJobPosts";
    }

    //================== See Job Details ===================
    @GetMapping("/jobDetails/{id}")  //Job id
    public String seeJobDetails(@PathVariable("id") long id, Model model, Principal principal)
    {
        Job job = jobRepository.findOne(id);
        model.addAttribute("job", job);

        Iterable <Skills> skillsList = job.getSkillsWithRating();
        model.addAttribute("skillsWithRating", skillsList);
//        Person myPerson = personRepository.findByUsername(principal.getName());
//        model.addAttribute("person", myPerson );

        return "/jobDetails";
    }

//    //================== Update Personal Info ===================
//    ne budu seichas delat' update of personal info'///////////////////////////////////////////

//    @GetMapping("/updatePerson/{id}")
//    public String updatePerson(@PathVariable("id") long id, Model model)
//    {
//        Person person = personRepository.findOne(id);
//        model.addAttribute("user", person);
//        System.out.println("updating person");
//        return "/registrationS";
//    }
//
//    // Validate entered information and if it is valid display the result
//    // Person must have first name, last name, and email address
//    @PostMapping("/registerSeeker")
//    public String postPerson(@Valid @ModelAttribute("user") Person person, BindingResult bindingResult, Model model)
//    {
////        if(bindingResult.hasErrors()){return "registrationS";}
//
//
////        Person myPerson = personRepository.findByUsername(principal.getName());
////        model.addAttribute("personObject", myPerson);
//
////        Person p = userRepo.findByUsername(p1.getName());
//
//        personRepository.save(person);
//
//        long personId = person.getId();
//        Person p = personRepository.findOne(personId);
//        model.addAttribute("personObject", p);
//
//        return "/resultPerson";
////        return "redirect:/persons/displayPersonAllInfo";
//    }



//    //================== Update Personal Info ===================
//    @GetMapping("/updatePerson/{id}")
//    public String updatePerson(@PathVariable("id") long id, Model model)
//    {
//        Person person = personRepository.findOne(id);
//        model.addAttribute("newPerson", person);
//        System.out.println("updating person");
//        return "/enterPerson";
//    }









//
//    //=========== List ALL Persons/Users ==============
//    @GetMapping("/allPersons")
//    public String showAllPersons(Model model, Principal p)
//    {
//        Iterable <Person> personslist = personRepository.findAll();
//        model.addAttribute("allpersons", personslist);
//        return "allPersons";
//    }
//






//    // ============= Add Personal Info: =============
//    // Allow user to enter Person's information
//    @GetMapping("/enterPerson")
//    public String addPerson(Model model)
//    {
//        model.addAttribute("newPerson", new Person());
//        return "enterPerson";
//    }

    ///////////////////////////////////////////////////////////////////////

    // Validate entered information and if it is valid display the result
//    // Person must have first name, last name, and email address
//    @PostMapping("/enterPerson")
//    public String postPerson(@Valid @ModelAttribute("newPerson") Person person, BindingResult bindingResult, Model model)
//    {
//        if(bindingResult.hasErrors()){return "enterPerson";}
//
//
////        Person myPerson = personRepository.findByUsername(principal.getName());
////        model.addAttribute("personObject", myPerson);
//
////        Person p = userRepo.findByUsername(p1.getName());
//
//        personRepository.save(person);
//
//        long personId = person.getId();
//        Person p = personRepository.findOne(personId);
//        model.addAttribute("personObject", p);
//
//        return "/resultPerson";
////        return "redirect:/persons/displayPersonAllInfo";
//    }
//
//
//


    // ============== Add Education ===============
    // Allow user to enter Educational Achievements
    @GetMapping("/enterEducation/{id}") //Person's ID
    public String addEducation(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);
        model.addAttribute("newPerson", p);  /////////??????

        Education e = new Education();

//        ????????????????????
//        e.setPerson(p);
        p.addEducation(e);

        model.addAttribute("newEducation", e);
        return "enterEducation";
    }
    // Validate entered Educational Achievement and if it is valid display the result
    @PostMapping("/enterEducation")
    public String postEducation(@Valid @ModelAttribute("newEducation") Education education, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){return "enterEducation";}

        model.addAttribute("newEducation", education);

        long personId = education.getPerson().getId();
        Person p = personRepository.findOne(personId);
        model.addAttribute("personObject", p);

        educationRepository.save(education);
        return "resultEducation";
    }





    // ============== Add Employment ==============
    // Allow user to enter Employment
    @GetMapping("/enterEmployment/{id}") //Person's ID
    public String addEmployment(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);
        model.addAttribute("newPerson", p);  /////////??????

        Employment w = new Employment();
//        ????????????????????
//        w.setPerson(p);
        p.addEmployment(w);

        model.addAttribute("newEmployment", w);
        return "enterEmployment";
    }

    // Validate entered work experience and if it is valid display the result
    @PostMapping("/enterEmployment")
    public String postEmployment(@Valid @ModelAttribute("newEmployment") Employment employment, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){return "enterEmployment";}

        // === Allow user to leave the end date empty (assume he/she is still employed)
        if(employment.getEndDate() == null){
            employment.setEndDate(LocalDate.now());
        }
        //=== If dates entered, do not accept the end date before the start date
        else if(employment.getEndDate().compareTo(employment.getStartDate())< 0){
            return "enterEmployment";
        }

        model.addAttribute("newEmployment", employment);

        long personId = employment.getPerson().getId();
        Person p = personRepository.findOne(personId);
        model.addAttribute("personObject", p);

        employmentRepository.save(employment);
        return "resultEmployment";
    }


//
//
//    //================== SKILLS =====================
//    //============ Input form: Skills ===============
//    // Add new Skill (allow user to enter new Skill)
//    @GetMapping("/enterSkills/{id}")    // This is PERSON'S ID
//    public String addSkills(@PathVariable("id") long id, Model model)
//    {
//        Person p = personRepository.findOne(id);
//
////        model.addAttribute("newPerson", p);  /////////??????
//        Skills s = new Skills();
//
////        ????????????????????
////        s.setPerson(p);
//        p.addSkill(s);
//
//        model.addAttribute("newSkills", s);
//        return "enterSkills";
//    }
//
//    // Validate entered Skill and if it is valid display the result
//    @PostMapping("/enterSkills")
//    public String postSkills(@Valid @ModelAttribute("newSkills") Skills skills, BindingResult bindingResult, Model model)
//    {
//        if(bindingResult.hasErrors()){return "enterSkills";}
//
//        skillsRepository.save(skills);
//        model.addAttribute("newSkills", skills);
//
////        long personId = skills.getPerson().getId();
////        Person p = personRepository.findOne(personId);
//
////        model.addAttribute("personObject", p);
//
////        model.addAttribute("personID", personId);
//        return "resultSkills";
//    }

    //============ ADD SKILL ============
    @GetMapping("/addskill")
    public String addMovie(Model model)
    {
        Skills skill = new Skills();
        model.addAttribute("skill", skill);
        return "addskill";
    }

    @PostMapping("/addskill")
    public String saveMovie(@ModelAttribute("skill") Skills skill)
    {
        skillsRepository.save(skill);
        return "redirect:/welcome";
    }


//++++++++++ NOT WORKING +++++++++
/*
    @GetMapping("/addskillstoperson/{id}")
    public String addSkill(Model model, @PathVariable("id") long personID)
    {
        //Person myPerson = personRepository.findByUsername(p.getName());
        model.addAttribute("currentPerson", personRepository.findOne(new Long(personID)) );
//        model.addAttribute("p", personRepository.findByUsername(p.getName()));
        model.addAttribute("skillList",skillsRepository.findAll());
        return "personaddskill";
    }

    @PostMapping("/addskillstoperson/{skillid}")
    public String addSkillsToPerson(@RequestParam("person") String personID,
                                    @PathVariable("skill") long skillID,
                                    Model model)
    {
        //Person myPerson = personRepository.findByUsername(p.getName());
//        System.out.println("Actor ID"+actorID);
//        System.out.println("Movie ID"+movieID);
        Skills s = skillsRepository.findOne(new Long(skillID));
        s.addPerson(personRepository.findOne(new Long(personID)));
        //myPerson.addSkill(skillsRepository.findOne(new Long (skillID)));
        //personRepository.save(myPerson);
        skillsRepository.save(s);
        model.addAttribute("personList", personRepository.findAll());
        model.addAttribute("skillList",skillsRepository.findAll());
//        model.addAttribute("movieList",movieRepository.findAll());

//        model.addAttribute("myPerson", movieRepository.findAll());
        return "redirect:/welcome";
    }
*///////////////??????????????????????????????????


    ///////////////////////////////////////////////////////////////////////
    //====================== ADD SKILL TO PERSON ==========================
    @GetMapping("/addskillstoperson")
    public String addSkill(Model model, Principal principal)
    {
        Person myPerson = personRepository.findByUsername(principal.getName());
        model.addAttribute("currentPerson", myPerson);
//        model.addAttribute("p", personRepository.findByUsername(p.getName()));


        Collection <Skills> myPersonSkills = myPerson.getSkillsWithRating();


//        Skills sPerson = skillsRepository.findBySkillTitleIn(myPersonSkills);
//        System.out.println("Skill sPerson Title "+s.getSkillTitle());



        // Need to check here if the person already has this skill and if not add it to list

        model.addAttribute("skillList",skillsRepository.findAll());

        return "personaddskill";



    }

    @PostMapping("/addskillstoperson")///{skillid}")
    public String addSkillsToPerson(//@PathVariable("skill") long skillID,
                                    @RequestParam(value = "skills") long skills,
                                    //@ModelAttribute("skills") Skills mySkill,
                                    Model model, Principal principal)
    {
        Person myPerson = personRepository.findByUsername(principal.getName());
        model.addAttribute("currentPerson", myPerson );
        System.out.println("Skill ID: "+skills);
        System.out.println("Person username "+ principal.getName());
        System.out.println("Person Last name "+ myPerson.getLastName());

        // Skill chosen from drop down menu
        Skills s = skillsRepository.findOne(new Long(skills));
        System.out.println("Skill Title "+s.getSkillTitle());

//        s.addPerson(myPerson);???????????????
        //myPerson.addSkill(skillsRepository.findOne(new Long (skillID)));
        //personRepository.save(myPerson);
//        skillsRepository.save(s);??????????????????

        // Need to check here if the person already has this skill and if not add it to person
//
//        Collection <Skills> myPersonSkills = myPerson.getSkillsWithRating();
//        System.out.println("Skill s Title "+s.getSkillTitle());
//
//        Skills sPerson = skillsRepository.findBySkillTitleIn(myPersonSkills);
//        System.out.println("Skill sPerson Title "+s.getSkillTitle());
/////

        //////



            myPerson.addSkill(s);
            personRepository.save(myPerson);


//        model.addAttribute("personList", personRepository.findAll());
        model.addAttribute("skillList",skillsRepository.findAll());  //???????????????
//        model.addAttribute("movieList",movieRepository.findAll());

//        model.addAttribute("myPerson", movieRepository.findAll());
        return "redirect:/welcome";
    }
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    ///////////////////////////////////////////////////////////////////////
    //====================== REMOVE SKILL FROM PERSON =====================
    ///////////////////////////////////////////////////////////////////////
    @RequestMapping("/deleteSkills/{id}")
    public String delSkills(@PathVariable("id") long id, Principal principal)
    {
        Skills oneSkill = skillsRepository.findOne(id);
        Person myPerson = personRepository.findByUsername(principal.getName());

        myPerson.getSkillsWithRating().remove(oneSkill);
        personRepository.save(myPerson);

        return "redirect:/displayPersonAllInfo";
    }




    ///////////////////////////////////////////////////////////////////////
    // ================== DISPLAY FINAL RESUME ================
    // ===== Display Person's info saved to the database ======
    //== Person can only have one role and unique username ==

//    @GetMapping("/displayPersonAllInfo/{id}")
//    public String showAllPersonsInfo(@PathVariable("id") long id, Model model)
//    {
//        //Afua's help: Person myPerson = personRepository.findOne(Long.valueOf(1));
//        Person myPerson = personRepository.findOne(id);
//
//        model.addAttribute("person", myPerson );
//
//        return "displayPersonAllInfo";
//    }
    ///////////////////////////////////////////////////////////////////////

//    @GetMapping("/displayPersonAllInfo")
//    public String showAllPersonsInfo(Person person, Model model, Principal principal)
//    {
//        System.out.println("displayPersonAllInfo page: p.getname:"+ principal.getName());
//
//        Person myPerson = personRepository.findByUsername(principal.getName());
//        model.addAttribute("person", myPerson );
//
//        return "displayPersonAllInfo";
//    }



    //================== Update Personal Info ===================
    @GetMapping("/updatePerson/{id}")
    public String updatePerson(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);
        model.addAttribute("user", p);
        return "registrationS";
    }
//    //========= DELETE ENTIRE Person and his/her Resume  ===========
//    @GetMapping("/deletePerson/{id}")
//    public String deletePerson(@PathVariable("id") long id)
//    {
//        personRepository.delete(id);
//        return "redirect:/allPersons";
//    }






    //=========== Update, Delete Education =============
    //=========== Update Education =============
    @GetMapping("/updateEducation/{id}")  //Education id
    public String updateEducation(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newEducation", educationRepository.findOne(id));
        return "enterEducation";
    }

    //=========== Delete Education =============
    @RequestMapping("/deleteEducation/{id}")
    public String delEducation(@PathVariable("id") long id)
    {
        Education oneEducation= educationRepository.findOne(id);
        long personToGoTo = oneEducation.getPerson().getId();

        // you MUST first remove the Education from the Set of educationalAchievements for their person, then you can delete the education
        // Remove Education from person
        educationRepository.findOne(id).getPerson().removeEducation(oneEducation);

        // delete Education from the education table
        educationRepository.delete(id);
//        return "redirect:/displayPersonAllInfo/" + personToGoTo;

        return "redirect:/displayPersonAllInfo";
    }




    //=========== Update, Delete Employment ============
    //=========== Update Employment ============
    @GetMapping("/updateEmployment/{id}")  //Education id
    public String updateEmployment(@PathVariable("id") long id, Model model)
    {
        Employment e = employmentRepository.findOne(id);
        model.addAttribute("newEmployment", e);
        // The same in one line:
        // model.addAttribute("newEmployment", employmentRepository.findOne(id));
        return "enterEmployment";
    }

    //=========== Delete Employment ============
    @RequestMapping("/deleteEmployment/{id}")
    public String delEmployment(@PathVariable("id") long id)
    {
        Employment oneEmployment= employmentRepository.findOne(id);
        long personToGoTo = oneEmployment.getPerson().getId();

        // you MUST first remove the Employment from the Set of workExperience for their person, then you can delete the skill
        // Remove Employment from person
        employmentRepository.findOne(id).getPerson().removeEmployment(oneEmployment);

        // delete Employment from the employment table
        employmentRepository.delete(id);
//        return "redirect:/displayPersonAllInfo/" + personToGoTo;
        return "redirect:/displayPersonAllInfo";
    }



    // ============== Add Job ==============
    // Allow user to enter Employment
    @GetMapping("/addjob")
    public String addJob(Model model, Principal principal)
    {
//        Person p = personRepository.findOne(id);
//        model.addAttribute("newPerson", p);  /////////??????
        Person myPerson = personRepository.findByUsername(principal.getName());
        model.addAttribute("currentPerson", myPerson );

        Job j = new Job();
        myPerson.addJob(j);

        model.addAttribute("newJob", j);

        return "addjob";
    }

    // Validate entered work experience and if it is valid display the result
    @PostMapping("/addjob")
    public String postJob(@Valid @ModelAttribute("newJob") Job job, BindingResult bindingResult, Model model, Principal principal)
    {
        if(bindingResult.hasErrors()){return "addjob";}

        model.addAttribute("job", job);

        Person myPerson = personRepository.findByUsername(principal.getName());
//        model.addAttribute("currentPerson", myPerson );
        System.out.println("Recruiter last name "+ myPerson.getLastName());
        model.addAttribute("personObject", myPerson);

        jobRepository.save(job);


        // Addition to disable add skill to job button:
        long countAllSkills = skillsRepository.count();
        model.addAttribute("gotskills", countAllSkills);

        return "resultJob";
    }

    ///////////////////////////////////////////////////////////////////////
    //========================= ADD SKILL TO JOB ==========================
    ///////////////////////////////////////////////////////////////////////
    //========================= ADD SKILL TO JOB ==========================
    // Attach existed Skill to Job
    @GetMapping("/addskilltojob/{id}")// This is JOB ID
    public String addSkillToJob(@PathVariable("id") long id, Model model, Principal principal)
    {
        Person myPerson = personRepository.findByUsername(principal.getName());
        Job myJob = jobRepository.findOne(new Long(id));

        model.addAttribute("currentPerson", myPerson);
        model.addAttribute("currentJob", myJob);
        model.addAttribute("skillList",skillsRepository.findAll());

        System.out.println("GetMapping: Recruiter last name "+ myPerson.getLastName());
        System.out.println("GetMapping: Job title: "+ myJob.getTitle());

        return "jobaddskill";
    }

    @PostMapping("/addskilltojob")///{id}")// This is JOB ID
    public String processAddSkillsToJob(//@PathVariable("skill") long skillID,
                                     //   @PathVariable("id") long id, // This is JOB ID
                                        @RequestParam(value = "skills") long skills,
                                        @RequestParam(value = "currentJob") long currentJob,
                                    //@ModelAttribute("skills") Skills mySkill,
                                    //    @ModelAttribute("currentJob") Job myJob,
                                    Model model, Principal principal)
    {
        Person myPerson = personRepository.findByUsername(principal.getName());
//        Job myJob = jobRepository.findOne(new Long(id));
        Job j = jobRepository.findOne(new Long(currentJob));

        model.addAttribute("currentPerson", myPerson );
        model.addAttribute("currentJob", j);

        System.out.println("Person username "+ principal.getName());
        System.out.println("Person Last name "+ myPerson.getLastName());
        System.out.println("Job title: "+ j.getTitle());



        Skills s = skillsRepository.findOne(new Long(skills));
//        System.out.println("Skill Title: "+skills);
        System.out.println("Skill Title: "+s.getSkillTitle());


//        System.out.println("Person Last name "+ myPerson.getLastName());
//        System.out.println("Skill added to Job Title: "+ myJob.getTitle());
//
//        Skills s = skillsRepository.findOne(new Long(skills));
//        System.out.println("Skill Title "+s.getSkillTitle());
////        s.addPerson(myPerson);???????????????
//        //myPerson.addSkill(skillsRepository.findOne(new Long (skillID)));
//        //personRepository.save(myPerson);
////        skillsRepository.save(s);??????????????????
//
//
//        myPerson.addSkill(s);
//        personRepository.save(myPerson);
////        model.addAttribute("personList", personRepository.findAll());
//        model.addAttribute("skillList",skillsRepository.findAll());
////        model.addAttribute("movieList",movieRepository.findAll());
//
////        model.addAttribute("myPerson", movieRepository.findAll());

        j.addSkill(s);
        jobRepository.save(j);
//        model.addAttribute("personList", personRepository.findAll());
        model.addAttribute("skillList",skillsRepository.findAll());




        long jobToGoTo = j.getId();
//        return  "redirect:/jobDetails/{id}";  // This is JOB ID

//        return "redirect:/jobDetails/" + jobToGoTo;

        return "redirect:/welcome";
    } //////////////////////////////////////////////////////////////////////





    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // ==JOB SEARCH ==
    @GetMapping("/jobsearch")
    public String searchJob()
    {
        return "jobsearch";
    }
    @PostMapping("/jobsearchbycompany")
    public String processSearchJobByCompany(@RequestParam(value = "companyname") String companyname, Model model)
    {
//        Iterable <Job> jobsList = jobRepository.findAllByEmployerLike(companyname);

        Iterable <Job> jobsList = jobRepository.findAllByEmployerContains(companyname);
        System.out.println(companyname);
        for(Job j: jobsList){
            System.out.println("Job Company: " + j.getEmployer());
        }
        model.addAttribute("allJobsMatched", jobsList);
        return "jobsearchresult";
    }



    @PostMapping("/jobsearchbytitle")
    public String processSearchJobByTitle(@RequestParam(value = "positiontitle") String positiontitle, Model model)
    {
        System.out.println(positiontitle);

        Iterable <Job> jobsList = jobRepository.findAllByTitleContains(positiontitle);
        for(Job j: jobsList){
            System.out.println("Job Title: " + j.getTitle());
        }
        model.addAttribute("allJobsMatched", jobsList);
        return "jobsearchresult";
    }


    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    // ================= PEOPLE SEARCH ================
    @GetMapping("/peoplesearch")
    public String searchPeople()
    {
        return "peoplesearch";
    }

    @PostMapping("/peoplesearchbyschool")
    public String processSearchPeopleBySchool(@RequestParam(value = "schoolname") String schoolname, Model model)
    {
        // Iterable <Job> jobsList = jobRepository.findAllByEmployerLike(companyname);
        System.out.println(schoolname);

//        List<Person> searchPersonList= personRepository.findAllByEducationalAchievementsIsContaining(schoolname);

       Set<Education> edu= educationRepository.findByEducationalInstitution(schoolname);
        List<Person> searchPersonList= personRepository.findAllByEducationalAchievementsIn(edu);


//                findAllByByEducationalAchievements_EducationalInstitution(schoolname);
        model.addAttribute("searchPersonList", searchPersonList);

//        Iterable <Job> jobsList = jobRepository.findAllByEmployerContains(schoolname);
//        System.out.println(schoolname);
//        for(Job j: jobsList){
//            System.out.println("school name: " + j.getEmployer());
//        }
//        model.addAttribute("allJobsMatched", jobsList);
//
        return "peoplesearchresult";
    }

    @PostMapping("/peoplesearchbyname")
    public String processSearchPeopleByName(@RequestParam(value = "searchFirstName") String searchFirstName,
                                            @RequestParam(value = "searchLastName") String searchLastName,
                                            Model model)
    {
//        Iterable <Job> jobsList = jobRepository.findAllByEmployerLike(companyname);
        System.out.println("person name: " + searchFirstName + " " + searchLastName);
//
//        Iterable <Job> jobsList = jobRepository.findAllByEmployerContains(personname);
//        for(Job j: jobsList){
//            System.out.println("Job Title: " + j.getTitle());
//        }
//
//        model.addAttribute("allJobsMatched", jobsList);
        Iterable<Person> searchPersonList = personRepository.findAllByFirstNameAndLastName(searchFirstName, searchLastName);
        model.addAttribute("searchPersonList", searchPersonList);
        return "peoplesearchresult";
    }

//Just commented Monday evening:
//    @GetMapping("/displayPersonInfo/{id}")
//    public String showAllPersonsInfo(@PathVariable("id") long id, Model model)
//    {
//        Person myPerson = personRepository.findOne(id);
//        model.addAttribute("person", myPerson );
//        return "displayPerson";
//    }
//    /////////////////////////////////////////////////////////////////////
//
//    @PostMapping("/displayPersonInfo")
//    public String showAllPersonsInfo(@RequestParam() String positiontitle,Person person, Model model, Principal principal)
//    {
//        System.out.println("displayPersonAllInfo page: p.getname:"+ principal.getName());
//        Person myPerson = personRepository.findByUsername(principal.getName());
//        model.addAttribute("person", myPerson );
//
//        return "displayPerson";
//    }


}
