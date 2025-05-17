/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.controllers;

import com.apartment_management.pojo.User;
import com.apartment_management.services.UserService;
import com.apartment_management.utils.JwtUtils;
import jakarta.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api")
public class ApiUserControllers {

    @Autowired
    private UserService userService;

    @PutMapping(path = "/users/{id}", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<User> editProfile(
            @PathVariable("id") int id,
            @ModelAttribute User user) {

        User updatedUser = userService.editProfile(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        try {
            User authenticatedUser = userService.authenticateForClient(username, password);
            if (authenticatedUser != null) {
                String token = JwtUtils.generateToken(authenticatedUser.getUsername());

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("username", authenticatedUser.getUsername()); // Optional

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Tên đăng nhập hoặc mật khẩu không đúng!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Lỗi hệ thống: " + e.getMessage()));
        }
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userService.getUserByUserName(principal.getName()), HttpStatus.OK);
    }

    @PutMapping(path = "/users/{id}/update_avatar", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<?> updateAvatar(
            @PathVariable("id") int userId,
            @RequestParam("avatar") MultipartFile file) {

        User updatedUser = userService.updateAvatar(userId, file);
        if (updatedUser != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật avatar thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Không thể cập nhật avatar"));
        }
    }

    @PutMapping("/users/{id}/change_password")
    public ResponseEntity<?> changePassword(
            @PathVariable("id") int userId,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {

        boolean success = userService.changePassword(userId, oldPassword, newPassword);

        if (success) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Đổi mật khẩu thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Mật khẩu cũ không đúng hoặc người dùng không tồn tại"));
        }
    }

}
