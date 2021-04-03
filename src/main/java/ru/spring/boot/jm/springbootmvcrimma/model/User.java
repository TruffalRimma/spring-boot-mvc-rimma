package ru.spring.boot.jm.springbootmvcrimma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

/*
Lombok features (фичи) :

1. @Data - генерация всех служебных методов:
    1.1 @ToString - определение аннотации перед классом, для реализации стандартного toString метода
    1.2 @EqualsAndHashCode - легкое создание методов Equals и HashCode
    1.3 @Getter / @Setter - легкое создание getter’ов и setter’ов
    1.4 @AllArgsConstructor - создание конструктора включающего все возможные поля
2. @NoArgsConstructor - создание пустого конструктора
3. @RequiredArgsConstructor - создание конструктора включающего все final поля
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Field username should not be empty!")
    @Size(min = 2, max = 25, message = "Field username should be between 2 and 25 characters!")
    private String username;

    @Column(name = "nickname")
    @NotEmpty(message = "Field nickname should not be empty!")
    @Size(min = 2, max = 25, message = "Field nickname should be between 2 and 25 characters!")
    private String nickname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 4, max = 25, message = "Field password should be between 4 and 25 characters!")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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
