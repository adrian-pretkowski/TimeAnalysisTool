package pl.adi.timeanalysistool.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.adi.timeanalysistool.filter.CustomAuthenticationFilter;
import pl.adi.timeanalysistool.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/roles/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save/**")
                .hasAnyAuthority("ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/user/delete/**")
                .hasAnyAuthority("ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/testplans/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/vehicles/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/functions/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
