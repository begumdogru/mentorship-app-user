package com.mentorship.user_service.services;

import com.mentorship.user_service.models.dtos.LoginUserDto;
import com.mentorship.user_service.models.dtos.RegisterUserDto;
import com.mentorship.user_service.models.entity.Role;
import com.mentorship.user_service.models.entity.User;  
import com.mentorship.user_service.models.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto registerUserDto) {
        // Signup sırasında sadece temel bilgiler - diğerleri profile completion'da doldurulacak
        User user = User.builder()
                .username(registerUserDto.getUsername())
                .fullName(registerUserDto.getFullName())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .role(Role.UNKNOWN) // Kullanıcı daha role seçmedi - profil tamamlamada seçecek
                .experience(0) // Default değer
                .rating(0.0) // Default değer
                .build();   

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );
        return userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
}
