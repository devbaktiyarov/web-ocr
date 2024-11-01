package com.devbaktiyarov.webocr.service.security;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class RegistrationService {
    


    private final JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository, JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(UserProfile user, String url) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        try {
            sendVerificationEmail(user, url);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean validateUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    
    
    private void sendVerificationEmail(UserProfile user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "devbaktiyarov@gmail.com";
        String senderName = "DEVBAKTIYAROV";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
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
