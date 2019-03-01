package pl.wroc.uni.ii.guard.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {"/socket/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        httpBasic(http);
        sessionCreationPolicy(http);
        cors(http);
        authorizeRequests(http);
        logout(http);
        csrf(http);
    }

    private void httpBasic(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
    }

    private void sessionCreationPolicy(HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void cors(HttpSecurity http) throws Exception {
        http.cors();
    }

    private void authorizeRequests(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().denyAll();
    }

    private void logout(HttpSecurity http) throws Exception {
        http.logout().disable();
    }

    private void csrf(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
