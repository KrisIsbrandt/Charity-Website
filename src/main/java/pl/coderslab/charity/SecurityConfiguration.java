package pl.coderslab.charity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.charity.services.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout().logoutSuccessUrl("/app/donation");

        //Only logged admins
        http.authorizeRequests()
                .antMatchers("/admin", "/admin/**")
                .hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout().logoutSuccessUrl("/app/donation");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }
}
