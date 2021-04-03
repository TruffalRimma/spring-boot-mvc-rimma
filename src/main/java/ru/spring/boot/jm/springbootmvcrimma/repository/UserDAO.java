package ru.spring.boot.jm.springbootmvcrimma.repository;

import ru.spring.boot.jm.springbootmvcrimma.model.User;

import java.util.List;

public interface UserDAO {
    User loadUserByUsername(String username);
    List<User> getUsers();
    User getUserByID(Long id);
    void save(User user);
    void update(User user);
    void delete(User user);
}