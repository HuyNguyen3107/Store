package com.example.store.service;

import com.example.store.model.*;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String createUser(UserDTO userDTO) {
        String name = userDTO.getName();
        String email = userDTO.getEmail();
        String phone = userDTO.getPhone();
        String address = userDTO.getAddress();
        String status = userDTO.getStatus();
        String password = GeneratePwHelper.generateRandomPassword(); 
        String hashedPassword = PasswordEncoderUtil.encodePassword(password); 
        User user = new User(name, email, hashedPassword, status, phone, address);
        User newUser = userRepository.save(user);

        userRoleRepository.deleteAllByUserId(newUser.getId());
        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : userDTO.getRoleIds()) {
            UserRole userRole = new UserRole();
            userRole.setUserId(newUser.getId());    
            userRole.setRoleId(Integer.parseInt(roleId));
            userRoles.add(userRole);
        }

        userRoleRepository.saveAll(userRoles);
        return password; 
    }

    public String updateUser(Integer id, UserInfoDTO userInfoDTO, String email) {
        User user = userRepository.findUserByIdAndEmail(id, email);
        if (user == null) {
            return null; 
        }
        String name = userInfoDTO.getName();
        if (name != null) {
            user.setName(name);
        }
        String address = userInfoDTO.getAddress();
        if (address != null) {
            user.setAddress(address);
        }
        String phone = userInfoDTO.getPhone();
        if (phone != null) {
            user.setPhone(phone);
        }
        userRepository.save(user);
        return "User updated successfully";
    }
    
    public boolean deleteUser(Integer id, String email) {
        User user = userRepository.findByEmail(email);
        User userToDelete = userRepository.findUserById(id);
        if (id == user.getId()) {
            return false; 
        }
        if (userToDelete != null) {
            userRepository.delete(userToDelete);
            return true; 
        } else {
            return false; 
        }
    }

    
    public void saveToken(Integer userId, String token) {
        User user = userRepository.findUserById(userId);
        if (user != null) {
            user.setToken(token);
            userRepository.save(user);
        }
    }

    public User getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

    public void updatePassword(Integer userId, String newPassword) {
        User user = userRepository.findUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
    public String changePassword(User user, PasswordDTO passwordDTO) {
        String password = passwordDTO.getPassword();
        String newPassword = passwordDTO.getNwPassword();
        String confirmPassword = passwordDTO.getConfirmPassword();

        if (PasswordEncoderUtil.matches(password, user.getPassword())) {
            if (newPassword.equals(confirmPassword)) {
                String hashedNewPassword = PasswordEncoderUtil.encodePassword(newPassword);
                user.setPassword(hashedNewPassword);
                userRepository.save(user);
                return "Password updated successfully";
            } else {
                return "New password and confirm password do not match";
            }
        } else {
            return "Current password is incorrect";
        }
    }
}
