package com.elg.vshop.config;

import com.elg.vshop.error.security.CustomAccessDeniedHandler;
import com.elg.vshop.service.security.AccountSecurityDetailsService;
import com.elg.vshop.error.security.JwtAuthenticationEntryPoint;
import com.elg.vshop.service.security.jwt.JwtTokenRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private AccountSecurityDetailsService detailsService;
    private JwtTokenRequestFilter  tokenRequestFilter;

    private static final String ANNONCE_ENDPOINT = "/api/annonces/vh/search";
    private static final String CAR_ATTR_LIST = "/api/vh/**";
    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String AUTH_ENDPOINT = "/api/auth/**";

    @Autowired
    public SpringSecurityConfig(AccountSecurityDetailsService detailsService, JwtTokenRequestFilter tokenRequestFilter) {
        this.detailsService = detailsService;
        this.tokenRequestFilter = tokenRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable().formLogin().disable()


        http
                .csrf().disable()

                // No sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers("/api/accounts/**").authenticated()
                .antMatchers("/api/details/**").authenticated()
                .antMatchers("/api/annonces/sec/**").authenticated()
                .antMatchers("/api/profiles/**").authenticated()
                .anyRequest().permitAll()

                .and()
                .logout().permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable();

        http.addFilterBefore(tokenRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.detailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
