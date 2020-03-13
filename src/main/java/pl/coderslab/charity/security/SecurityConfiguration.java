package pl.coderslab.charity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.coderslab.charity.services.SpringDataUserDetailsService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler(){
        return new AuthenticationSuccessHandlerImpl();
    }

    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Available for everyone
        http.authorizeRequests()
                .antMatchers("/", "/**").permitAll();

        http.cors().and().csrf().disable();

        //Only logged users
        http.authorizeRequests()
                .antMatchers("/app", "/app/**")
                .authenticated()
                .and().formLogin().loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customSuccessHandler())
                .failureHandler(customFailureHandler())
                .and().logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID");

        //Only logged admins
        http.authorizeRequests()
                .antMatchers("/admin", "/admin/**")
                .hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customSuccessHandler())
                .failureHandler(customFailureHandler())
                .and().logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID");
    }
}
