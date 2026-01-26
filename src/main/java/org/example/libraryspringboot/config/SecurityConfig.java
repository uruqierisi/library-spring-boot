package org.example.libraryspringboot.config;


import org.example.libraryspringboot.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()


                        .requestMatchers("/admin/**").hasRole("ADMIN")


                        .requestMatchers("/app/**", "/api/**").hasAnyRole("USER", "ADMIN")


                        .anyRequest().authenticated()
                )


                .formLogin(form -> form
                        .loginPage("/login") // URL i logini
                        .defaultSuccessUrl("/app/books", true) // ku me shku masi tbohet login succesfful
                        .failureUrl("/login?error=true") // ku me shku nese fail
                        .permitAll()
                )


                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )


                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied") // access denied page me html
                );

        return http.build();
    }
}
