package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.Role;
import pl.adi.timeanalysistool.domain.User;

import java.util.List;

public interface UserService {
    User saveUser (User user);
    Role saveRole (Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
