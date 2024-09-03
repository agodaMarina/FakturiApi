package com.marina.comptaApi.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.marina.comptaApi.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 100, unique = true)
    //yabode3449@maxturns.com
    private String email;

    @Column(length = 100)
    private String telephone;

    @Column()
    private String password;

    @OneToOne
    @JsonBackReference //pour gérer la récursivité de la sérialisation
    private Solde solde;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean accountLocked;

    private boolean enabled;

    private LocalDateTime created;


    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return enabled;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}
