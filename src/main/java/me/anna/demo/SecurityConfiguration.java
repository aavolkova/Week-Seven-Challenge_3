package me.anna.demo;

import me.anna.demo.repositories.PersonRepository;
//import me.anna.demo.repositories.UserzRepo;
import me.anna.demo.services.SSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{



    @Autowired
    private SSUserDetailsService userDetailsService;

//    @Autowired
//    private UserzRepo uRepo;
//
//
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return new SSUserDetailsService(uRepo);
//    }

    @Autowired
    private PersonRepository uRepo;


    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(uRepo);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//
//                .antMatchers( "/",  "/css/**", "/img/**", "/js/**", "/mail/**", "/scss/**", "/vendor/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .httpBasic();

// 'RECRUITER'
// 'SEEKER'


        http
                .authorizeRequests()
                .antMatchers("/createRoles", "/", "/registerSeeker", "/registerRecruiter", "/welcome",
//                        "/persons/displayPersonAllInfo",
                                         "/css/**","/img/**", "/js/**",  "/mail/**", "/scss/**", "/vendor/**").permitAll()

//                .antMatchers("/books/list").access("hasRole('ROLE_USER')")
//                .antMatchers("/books/list").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//                .antMatchers("/books/list").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers( "/books/add", "/books/edit/{id}", "/books/delete/{id}").access("hasAnyRole('ROLE_ADMIN')")
                //                .antMatchers( "/books/add", "/books/edit/{id}", "/books/delete/{id}").access("hasRole('ROLE_ADMIN')")
//                .antMatchers( "/books/add", "/books/edit/{id}", "/books/delete/{id}").access("hasRole('ROLE_ADMIN')")

//                .antMatchers("/persons/**").access("hasAuthority('RECRUITER') or hasAuthority('SEEKER')")

//                .antMatchers( "/books/add", "/books/edit/**", "/books/delete/**").access("hasAuthority('ADMIN')")



//                .antMatchers( "/persons/displayPersonAllInfo",
//                                         "/persons/updatePerson/**",
//                                         "/persons/enterPerson",
//                                         "/persons/resultPerson").access("hasAuthority('SEEKER')")

//                .antMatchers( "/displayPersonAllInfo",
//                        "/updatePerson/**",
//                        "/enterPerson",
//                        "/resultPerson").access("hasAuthority('SEEKER')")
                //.antMatchers( "/displayPersonAllInfo",
                .antMatchers( 
                                         "/enterEducation/**", "/enterEmployment/**",
                                         "/updateEducation/**","/updateEmployment/**",
                                         "/deleteEducation/**","/deleteEmployment/**").access("hasAuthority('SEEKER')")



//                .antMatchers( "/persons/allPersons").access("hasAuthority('SEEKER')")

                .anyRequest().authenticated();
        http
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/welcome")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/").permitAll()
                .logoutSuccessUrl("/").permitAll().permitAll()
                .and()
                .httpBasic();



        http
                .csrf().disable();

        http
                .headers().frameOptions().disable();




    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceBean());
    }






//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication().
////                withUser("a").password("a").roles("USER");
//                withUser("newuser").password("newuserpa$$").roles("USER");
//        // to add additional accounts, remove the semicolon at
//        // the end of the previous command and add an additional user like below:
//        //           .and()
//        //           .withUser("dave").password("begreat").roles("USER");
//    }

}