package com.devbaktiyarov.webocr.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;


@Entity
public class UserProfile implements UserDetails {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    
    @Column(unique=true)
    private String email;
    private String password;
    private String role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private List<ImageFile> imageFileList;

    public UserProfile() {
    }

    public UserProfile(String email, List<ImageFile> imageFileList, String password, String role, long userId) {
        this.email = email;
        this.imageFileList = imageFileList;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(role));

        return list;

    }

    public List<ImageFile> getImageFileList() {
        return imageFileList;
    }

    public void setImageFileList(List<ImageFile> imageFileList) {
        this.imageFileList = imageFileList;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    

    

}
