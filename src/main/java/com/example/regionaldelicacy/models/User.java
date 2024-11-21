package com.example.regionaldelicacy.models;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.regionaldelicacy.dtos.SignUpDto;
import com.example.regionaldelicacy.enums.UserRole;
import com.example.regionaldelicacy.serializers.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;

    @JsonSerialize(using = InstantSerializer.class)
    private Instant lastLogin;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(SignUpDto userSignupInfo, String encryptedPassword, UserRole role) {
        this.name = userSignupInfo.getName();
        this.email = userSignupInfo.getEmail();
        this.password = encryptedPassword;
        this.phoneNumber = userSignupInfo.getPhoneNumber();
        this.address = userSignupInfo.getAddress();
        this.city = userSignupInfo.getCity();
        this.country = userSignupInfo.getCountry();
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
