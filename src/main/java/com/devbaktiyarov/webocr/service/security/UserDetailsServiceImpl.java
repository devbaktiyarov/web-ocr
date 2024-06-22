package com.devbaktiyarov.webocr.service.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserProfile> user = userRepository.findByEmail(email);

        if(user.isEmpty() || !user.get().isEnabled()) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return user.get();
    }
    
}
