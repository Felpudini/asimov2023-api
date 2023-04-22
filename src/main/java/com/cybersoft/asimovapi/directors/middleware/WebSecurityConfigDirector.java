package com.cybersoft.asimovapi.directors.middleware;

import com.cybersoft.asimovapi.directors.domain.service.DirectorService;
import com.cybersoft.asimovapi.security.middleware.JwtAuthenticationEntryPointDirector;
import com.cybersoft.asimovapi.teachers.domain.service.TeacherService;
import com.cybersoft.asimovapi.teachers.middleware.JwtAuthorizationFilterTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfigDirector extends WebSecurityConfigurerAdapter {
    @Autowired
    DirectorService directorService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    JwtAuthenticationEntryPointDirector unauthorizedHandler;

    @Bean
    public JwtAuthorizationFilterDirector authorizationFilterDirector() {
        return new JwtAuthorizationFilterDirector();
    }

    @Bean
    public JwtAuthorizationFilterTeacher authorizationFilterTeacher() {
        return new JwtAuthorizationFilterTeacher();
    }


    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(directorService);
        builder.userDetailsService(teacherService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception {
       // web.ignoring().antMatchers("/swagger-ui.html", "/swagger-ui/**");

    } */

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/v1/directors/auth/*", "/api/v1/teachers/auth/**", "/api/v1/directors","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authorizationFilterDirector(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authorizationFilterTeacher(), UsernamePasswordAuthenticationFilter.class);
    }



}
