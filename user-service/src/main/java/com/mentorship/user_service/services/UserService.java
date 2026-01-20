package com.mentorship.user_service.services;

import com.mentorship.user_service.models.dtos.UserDto;
import com.mentorship.user_service.models.entity.Sector;
import com.mentorship.user_service.models.entity.University;
import com.mentorship.user_service.models.repository.SectorRepository;
import com.mentorship.user_service.models.repository.UniversityRepository;
import com.mentorship.user_service.models.repository.UserRepository;
import java.util.ArrayList;

import com.mentorship.user_service.responses.SectorResponse;
import com.mentorship.user_service.responses.UniversityResponse;
import com.mentorship.user_service.responses.UserResponse;
import com.mentorship.user_service.responses.UserSubsResponse;
import org.springframework.stereotype.Service;
import com.mentorship.user_service.models.entity.User;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final SectorRepository sectorRepository;
    
    public UserService(UserRepository userRepository, UniversityRepository universityRepository, SectorRepository sectorRepository) {
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserSubsResponse getUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String universityName = null;
        if (user.getUniversityId() != null) {
            universityName = universityRepository.findById(user.getUniversityId())
                    .map(university -> university.getUniversityName())
                    .orElse(null);
        }

        String sectorName = null;
        if (user.getSector() != null) {
            sectorName = sectorRepository.findById(user.getSector())
                    .map(sector -> sector.getSectorName())
                    .orElse(null);
        }

        return UserSubsResponse.builder()
                .userId(user.getUserId())
                .role(user.getRole())
                .universityId(user.getUniversityId())
                .universityName(universityName)
                .sectorId(user.getSector())
                .sectorName(sectorName)
                .build();
    }
    
    public User updateProfile(Integer userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Sadece gönderilen alanları güncelle (null olmayanlar)
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getFullName() != null) {
            user.setFullName(userDto.getFullName());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        if (userDto.getUniversityId() != null) {
            user.setUniversityId(userDto.getUniversityId());
        }
        if (userDto.getSector() != null) {
            user.setSector(userDto.getSector());
        }
        if (userDto.getBiography() != null) {
            user.setBiography(userDto.getBiography());
        }
        if (userDto.getExperience() != null) {
            user.setExperience(userDto.getExperience());
        }
        
        return userRepository.save(user);
    }

    public UniversityResponse getUserUniversity(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getUniversityId() == null) {
            return null;
        }
        
        University university = universityRepository.findById(user.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        
        return UniversityResponse.builder()
                .universityId(university.getUniversityId())
                .universityName(university.getUniversityName())
                .build();
    }

    public List<UniversityResponse> getAllUniversities() {
        return universityRepository.findAll().stream()
                .map(university -> UniversityResponse.builder()
                        .universityId(university.getUniversityId())
                        .universityName(university.getUniversityName())
                        .build())
                .toList();
    }

    public UserResponse getUserResponse(User user) {
        String universityName = null;
        if (user.getUniversityId() != null) {
            universityName = universityRepository.findById(user.getUniversityId())
                    .map(University::getUniversityName)
                    .orElse(null);
        }

        String sectorName = null;
        if (user.getSector() != null) {
            sectorName = sectorRepository.findById(user.getSector())
                    .map(Sector::getSectorName)
                    .orElse(null);
        }

        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .universityId(user.getUniversityId())
                .universityName(universityName)
                .role(user.getRole())
                .sectorId(user.getSector())
                .sectorName(sectorName)
                .biography(user.getBiography())
                .experience(user.getExperience())
                .rating(user.getRating())
                .build();
    }

    public List<UserResponse> getAllUsersWithUniversity() {
        return userRepository.findAll().stream()
                .map(this::getUserResponse)
                .toList();
    }

    public SectorResponse getUserSector(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getSector() == null) {
            return null;
        }
        
        Sector sector = sectorRepository.findById(user.getSector())
                .orElseThrow(() -> new RuntimeException("Sector not found"));
        
        return SectorResponse.builder()
                .sectorId(sector.getSectorId())
                .sectorName(sector.getSectorName())
                .build();
    }

    public List<SectorResponse> getAllSectors() {
        return sectorRepository.findAll().stream()
                .map(sector -> SectorResponse.builder()
                        .sectorId(sector.getSectorId())
                        .sectorName(sector.getSectorName())
                        .build())
                .toList();
    }
}
