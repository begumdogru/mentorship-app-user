package com.mentorship.user_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.mentorship.user_service.middlewares.JWTAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    // Security configuration settings can be added here
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final org.springframework.security.authentication.AuthenticationProvider authenticationProvider;

    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
            org.springframework.security.authentication.AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) //hem cookie saldirilarina karsi koruma saglar hem de  token tabanli authentication yapiyoruz, bu nedenle csrf korumasini devre disi birakiyoruz.
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/login", "/api/v1/users/register").permitAll().anyRequest().authenticated()) //hangi endpointlere kim girebilir? Bizde login ve register endpointlerine herkes girebilsin, digerlerine ise sadece login olmus kullanicilar girebilsin.
            .sessionManagement(session -> session.sessionCreationPolicy(
                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
            )) //stateless yapmamizin sebebi token tabanli authentication yapiyoruz. Yani her istekte token gonderilecek ve sunucu tarafinda herhangi bir session bilgisi tutulmayacak. bir de stateful degeri var. bunu yaparsak jessionId tutulacak (cookie) ve kullanici tarayiciyi kapatip actiginda tekrardan login olmadan girebilecek.
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);//jwt filterimizi spring security filter zincirine ek
        return http.build();
    }
        
    

}