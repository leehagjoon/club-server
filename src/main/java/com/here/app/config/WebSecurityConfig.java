package com.here.app.config;

import com.here.app.api.acnt.jwt.filter.JwtAuthenticationEntryPoint;
import com.here.app.api.acnt.jwt.filter.JwtRequestFilter;
import com.here.app.api.acnt.jwt.service.JwtUserDetailsService;
import com.here.app.api.acnt.jwt.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private  UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
//        @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////         configure AuthenticationManager so that it knows from where to load
////         user for matching credentials
////         Use BCryptPasswordEncoder
////         로드할 위치를 알 수 있도록 AuthenticationManager를 구성합니다.
////         일치하는 자격 증명을 위한 사용자
////         BCryptPasswordEncoder 사용
//        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//    }
    private final String[] PERMITTED_URL = {
            "api/acnt/**",
};
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.jwtUserDetailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .mvcMatchers("/node_modules/**")
                .antMatchers("/images/**", "/js/**","/ignore1", "/ignore2")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
          return  http
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
                    .antMatchers("/","/**").permitAll()
                    .antMatchers(PERMITTED_URL).permitAll()
                    .anyRequest().authenticated().and()
                    .authenticationManager(authenticationManager)
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .headers().frameOptions().sameOrigin().and()
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }
}
