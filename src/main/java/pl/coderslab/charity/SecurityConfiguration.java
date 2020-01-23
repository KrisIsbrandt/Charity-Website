package pl.coderslab.charity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.coderslab.charity.services.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //InMemory user authentication
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ADMIN");
    }

    //Endpoint authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Available for everyone
        http.authorizeRequests()
                .antMatchers("/").permitAll();

        //Only logged users
        http.authorizeRequests()
                .antMatchers("/app", "/app/**")
                .authenticated()
                .and().formLogin().loginPage("/login")
                .and().logout().logoutSuccessUrl("/app/donation");

        //Only logged admins
        http.authorizeRequests()
                .antMatchers("/admin", "/admin/**")
                .hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .and().logout().logoutSuccessUrl("/app/donation");
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }
}
