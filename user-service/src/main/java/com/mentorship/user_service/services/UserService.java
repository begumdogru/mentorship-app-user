package com.mentorship.user_service.services;

import com.mentorship.user_service.models.repository.UserRepository;
import java.util.ArrayList;

import com.mentorship.user_service.responses.UserSubsResponse;
import org.springframework.stereotype.Service;
import com.mentorship.user_service.models.entity.User;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserSubsResponse getUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserSubsResponse.builder()
                .userId(user.getUserId())
                .role(user.getRole())
                .build();
    }
}
