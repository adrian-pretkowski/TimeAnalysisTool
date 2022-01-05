package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.AppUser;
import pl.adi.timeanalysistool.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser (AppUser appUser);
    Role saveRole (Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
    List<Role> getRoles();
}
