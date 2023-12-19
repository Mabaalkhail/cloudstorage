package com.udacity.jwdnd.course1.cloudstorage.conf;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringConf extends WebSecurityConfigurerAdapter {
    private AuthService authenticationService;

    public SpringConf(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**","/h2/**").permitAll().antMatchers( "/home/**").authenticated()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }
}
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(
//                //I've put them inside AntPathRequestMatcher to resolve error
//                new AntPathRequestMatcher("/static/**"),
//                new AntPathRequestMatcher("/js/**"),
//                new AntPathRequestMatcher("/h2-console/**"),
//                new AntPathRequestMatcher("/css/**")
//        );
//    }