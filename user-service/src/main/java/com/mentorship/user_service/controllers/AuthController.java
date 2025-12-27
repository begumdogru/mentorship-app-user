package com.mentorship.user_service.controllers;

import com.mentorship.user_service.models.dtos.LoginUserDto;
import com.mentorship.user_service.models.dtos.RegisterUserDto;
import com.mentorship.user_service.models.dtos.UserDto;
import com.mentorship.user_service.models.entity.User;
import com.mentorship.user_service.responses.LoginResponse;
import com.mentorship.user_service.services.AuthenticationService;
import com.mentorship.user_service.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody RegisterUserDto registerUser) {
        User register = authenticationService.signup(registerUser);
        return ResponseEntity.ok(register);

    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User login = authenticationService.authenticate(loginUserDto);
        UserDto.builder()
                .username(login.getUsername())
                .email(login.getEmail())
                .build();

        String jwtToken = jwtService.generateToken(login);

        LoginResponse response = LoginResponse.builder()
                .token(jwtToken)
                .username(login.getUsername())
                .expiresIn(jwtService.getExpirationInMs())
                .build();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) (authentication != null ? authentication.getPrincipal() : null);
        UserDto user = UserDto.builder()
                .username(authenticatedUser != null ? authenticatedUser.getUsername() : null)
                .fullName(authenticatedUser != null ? authenticatedUser.getFullName() : null)
                .user_id(authenticatedUser != null ? authenticatedUser.getUserId() : null)
                .email(authenticatedUser != null ? authenticatedUser.getEmail() : null)
                .build();

        return ResponseEntity.ok(user);
    }
}
