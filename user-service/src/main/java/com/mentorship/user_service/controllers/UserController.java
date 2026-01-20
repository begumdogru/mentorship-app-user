package com.mentorship.user_service.controllers;

import com.mentorship.user_service.models.dtos.UserDto;
import com.mentorship.user_service.models.entity.User;
import com.mentorship.user_service.responses.SectorResponse;
import com.mentorship.user_service.responses.UniversityResponse;
import com.mentorship.user_service.responses.UserResponse;
import com.mentorship.user_service.responses.UserSubsResponse;
import com.mentorship.user_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) (authentication != null ? authentication.getPrincipal() : null);

        return ResponseEntity.ok(userService.getUserResponse(currentUser));
    }
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> allUsers() {
        List<UserResponse> users = userService.getAllUsersWithUniversity();

        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserSubsResponse> getUserById(@PathVariable("id") Integer id) {
        UserSubsResponse userResponse = userService.getUser(id);
        return ResponseEntity.ok(userResponse);
    }
    
    @PutMapping("/updateProfile")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        User updatedUser = userService.updateProfile(currentUser.getUserId(), userDto);
        return ResponseEntity.ok(userService.getUserResponse(updatedUser));
    }

    @GetMapping("/{id}/university")
    public ResponseEntity<UniversityResponse> getUserUniversity(@PathVariable("id") Integer id) {
        UniversityResponse universityResponse = userService.getUserUniversity(id);
        return ResponseEntity.ok(universityResponse);
    }

    @GetMapping("/universities")
    public ResponseEntity<List<UniversityResponse>> getAllUniversities() {
        List<UniversityResponse> universities = userService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }

    @GetMapping("/{id}/sector")
    public ResponseEntity<SectorResponse> getUserSector(@PathVariable("id") Integer id) {
        SectorResponse sectorResponse = userService.getUserSector(id);
        return ResponseEntity.ok(sectorResponse);
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<SectorResponse>> getAllSectors() {
        List<SectorResponse> sectors = userService.getAllSectors();
        return ResponseEntity.ok(sectors);
    }
}

