/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.globaz.devsecops.activiti.lab.application.configuration;

import ch.globaz.devsecops.activiti.lab.application.ActivitiAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailService());
    }

    @Bean
    public UserDetailsService inMemoryUserDetailService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        for (User user : generateInMemroryUser()) {
            log.info("> Création utilisateurr: {}, authority: {}",user, authoritiesAsString(user));
            inMemoryUserDetailsManager.createUser(user);
        }

        return inMemoryUserDetailsManager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/h2/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        http
                .addFilterAfter(new ActivitiAuthFilter(), BasicAuthenticationFilter.class);

        http.headers().frameOptions().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private List<User> generateInMemroryUser(){

        SimpleGrantedAuthority roleActivitiUser = new SimpleGrantedAuthority("ROLE_ACTIVITI_USER");
        SimpleGrantedAuthority roleActivitiAdmin = new SimpleGrantedAuthority("ROLE_ACTIVITI_ADMIN");
        SimpleGrantedAuthority groupeActivitiGetionnaire = new SimpleGrantedAuthority("GROUP_gestionnaire");
        SimpleGrantedAuthority groupeActivitiStagiaire = new SimpleGrantedAuthority("GROUP_stagiaire");


        List<User> users = new ArrayList<>();

        users.add(new User("gestionnaire",passwordEncoder().encode("1234"),
                Arrays.asList(roleActivitiUser,groupeActivitiGetionnaire)));

        users.add(new User("gestionnaire2",passwordEncoder().encode("1234"),
                Arrays.asList(roleActivitiUser,groupeActivitiGetionnaire)));


        users.add(new User("stagiaire",passwordEncoder().encode("1234"),
                Arrays.asList(roleActivitiUser,groupeActivitiStagiaire)));

        users.add(new User("admin",passwordEncoder().encode("1234"),
                Arrays.asList(roleActivitiAdmin)));

        return users;

    }

    private List<String> authoritiesAsString(User user){
        return user.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
    }

}
