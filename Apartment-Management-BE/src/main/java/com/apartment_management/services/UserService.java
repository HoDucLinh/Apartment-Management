/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.apartment_management.services;

import com.apartment_management.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
public interface UserService extends UserDetailsService {
    User getUserByUserName(String username);
    boolean addUser(User user, MultipartFile file);
    User editProfile(int id, User user);
    User authenticateForClient(String username, String password);
    List<User> getUsers(Map<String, String> params);
    boolean deleteUser(int id);
    User getUserById(int id);
    User blockUser(int userId);
    User updateAvatar(int userId, MultipartFile file);
    boolean changePassword(int userId, String oldPassword, String newPassword);

}
