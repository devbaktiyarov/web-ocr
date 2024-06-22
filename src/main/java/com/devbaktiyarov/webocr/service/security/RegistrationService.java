package com.devbaktiyarov.webocr.service.security;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.repository.UserRepository;

@Service
public class RegistrationService {
    

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(UserProfile user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public boolean validateUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    


    public boolean verify(String verificationCode) {
        Optional<UserProfile> user = userRepository.findByVerificationCode(verificationCode);

        if (user.isEmpty() || user.get().isEnabled()) {
            return false;
        } else {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            userRepository.save(user.get());

            return true;
        }

    }

}
