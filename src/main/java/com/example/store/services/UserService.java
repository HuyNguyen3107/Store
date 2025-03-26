package com.example.store.service;

import com.example.store.model.User;
import com.example.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy danh sách tất cả users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy user theo ID
    public User getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Lấy user theo Email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Thêm user mới
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Cập nhật user
    // public User updateUser(Integer id, User newUserData) {
    //     return userRepository.findById(id).map(user -> {
    //         user.setName(newUserData.getName());
    //         user.setEmail(newUserData.getEmail());
    //         user.setPhone(newUserData.getPhone());
    //         user.setAddress(newUserData.getAddress());
    //         user.setStatus(newUserData.getStatus());
    //         user.setToken(newUserData.getToken());
    //         return userRepository.save(user);
    //     });
    // }

    // Xóa user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // save token
    public void saveToken(Integer userId, String token) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setToken(token);
            userRepository.save(user);
        }
    }

    public User getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

    public void updatePassword(Integer userId, String newPassword) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}
