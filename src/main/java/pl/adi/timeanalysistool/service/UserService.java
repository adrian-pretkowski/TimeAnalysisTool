package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.AppUser;
import pl.adi.timeanalysistool.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser (AppUser appUser);
    Role saveRole (Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    AppUser getUserByEmail(String email);
    AppUser getUserById(Long id);
    void deleteUser(String username);
    void deleteUserById (Long id);
    List<AppUser> getUsers();
    List<Role> getRoles();
}
