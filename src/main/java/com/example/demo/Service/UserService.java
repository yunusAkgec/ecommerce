package com.example.demo.Service;

import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(Users user){
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new RuntimeException("Bu kullanıcı adı zaten kullanılmaktadır!");
        }

        //Şifreyi hash'leme
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Users getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı."));
    }

    public Users loginUser(String username, String password){
        Users user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Kullanıcı adı veya şifre yanlış!");
        }
        return user;
    }
}
