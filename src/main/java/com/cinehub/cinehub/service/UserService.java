package com.cinehub.cinehub.service;

import com.cinehub.cinehub.model.User;
import com.cinehub.cinehub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 Register new user (used by AuthController)
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 🔹 Login user (used by AuthController)
    public Optional<User> login(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && passwordEncoder.matches(password, existingUser.get().getPassword())) {
            return existingUser;
        }
        return Optional.empty();
    }

    // 🔹 Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 🔹 Update user (except password)
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔹 Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
