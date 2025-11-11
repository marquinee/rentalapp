package com.example.rentalapp.controller;

import com.example.rentalapp.entity.Role;
import com.example.rentalapp.entity.User;
import com.example.rentalapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("errorMessage", "Неверный логин или пароль");
        if (logout != null) model.addAttribute("successMessage", "Вы вышли из системы");
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes ra) {
        if (userRepository.existsByUsername(user.getUsername())) {
            ra.addFlashAttribute("errorMessage", "Пользователь с таким именем уже существует");
            return "redirect:/register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        ra.addFlashAttribute("successMessage", "Регистрация прошла успешно. Войдите в систему.");
        return "redirect:/login";
    }
}
