package org.astron.tickfocus.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Length(min = 3, message = "Username is too short.")
    @Length(max = 30, message = "Username is too long.")
    @Pattern(regexp = "^[a-zA-Z_]+$",
            message = "Username should contain only latin letters and underscores.")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is not valid.")
    private String email;

    @Length(min = 3, message = "Password is too short.")
    @Length(max = 255, message = "Password is too long.")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
