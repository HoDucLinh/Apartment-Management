/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.services.impl;

import com.apartment_management.pojo.User;
import com.apartment_management.repositories.UserRepository;
import com.apartment_management.services.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUserName(String username) {
        return this.userRepo.getUserByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUserName(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username");
        }

        if (!"ADMIN".equals(u.getRole())) {
            throw new UsernameNotFoundException("Access denied: not an ADMIN");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public boolean addUser(User user, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatarUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        // Mã hóa mật khẩu
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        // Thêm thời gian tạo và kích hoạt tài khoản
        user.setCreatedAt(new Date());
        user.setIsActive(Boolean.TRUE);
        userRepo.addUser(user);
        return true;
    }

    @Override
    public User editProfile(int id, User updated) {
        User existing = userRepo.getUserById(id);
        if (existing == null) {
            return null;
        }

        if (updated.getUsername() != null) {
            existing.setUsername(updated.getUsername());
        }
        if (updated.getFullName() != null) {
            existing.setFullName(updated.getFullName());
        }
        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }
        if (updated.getPhone() != null) {
            existing.setPhone(updated.getPhone());
        }
        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }

        MultipartFile file = updated.getFile();
        if (file != null && !file.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                existing.setAvatarUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return userRepo.editProfile(existing);
    }

    @Override
    public User authenticateForClient(String username, String password) {
        return userRepo.authenticateForClient(username, password);
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return userRepo.getUsers(params);
    }

    @Override
    public boolean deleteUser(int id) {
        return userRepo.deleteUser(id);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public User blockUser(int userId) {
        User u = userRepo.getUserById(userId);
        if (u != null && u.getIsActive()) {
            u.setIsActive(Boolean.FALSE);
            return userRepo.editProfile(u); // cập nhật lại vào DB
        }
        return u;
    }

    @Override
    public User updateAvatar(int userId, MultipartFile file) {
        User user = userRepo.getUserById(userId);
        if (user == null || file == null || file.isEmpty()) {
            return null;
        }

        try {
            Map res = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            user.setAvatarUrl(res.get("secure_url").toString());
            return userRepo.editProfile(user);
        } catch (IOException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = userRepo.getUserById(userId);
        if (user == null) {
            return false;
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false; // Sai mật khẩu cũ
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.editProfile(user);
        return true;
    }

}
