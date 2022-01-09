package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.AppUser;
import pl.adi.timeanalysistool.domain.Role;
import pl.adi.timeanalysistool.repo.RoleRepo;
import pl.adi.timeanalysistool.repo.UserRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUsername(username);
        if (appUser == null) {
            log.error("[DB] User not found in the database...");
            throw new UsernameNotFoundException("User not found in the database...");
        } else {
            log.info("[DB] User {} found in the database...", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("[DB] Saving new user: {} to the database.", appUser.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepo.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("[DB] Saving new role: {} to the database.", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("[DB] Adding role: {} to user: {}.", roleName, username);
        AppUser appUser = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        appUser.getRoles().add(role); //transaction -> auto save
    }

    @Override
    public AppUser getUser(String username) {
        log.info("[DB] Fetching user: {}.", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        log.info("[DB] Fetching email: {}.", email);
        return userRepo.findByEmail(email);
    }

    public AppUser getUserById(Long id) {
        log.info("[DB] Fetching id: {}.", id);
        return userRepo.findById(id).orElseThrow();
    }

    @Override
    public void deleteUser(String username) {
        AppUser userFoundByUsername = userRepo.findByUsername(username);
        if (userFoundByUsername != null) {
            log.info("[DB] Deleting user: {}.", username);
            userRepo.deleteByUsername(username);
        } else {
            log.warn("[DB] User with given username: {} doest not exist", username);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<AppUser> userById = userRepo.findById(id);
        if (userById.isPresent()) {
            log.info("[DB] Deleting user by id: {}", id);
            userRepo.deleteById(id);
        } else {
            log.warn("[DB] User with given id: {} doest not exist", id);
        }
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("[DB] Fetching all users...");
        return userRepo.findAll();
    }

    @Override
    public List<Role> getRoles() {
        log.info("[DB] Fetching all roles...");
        return roleRepo.findAll();
    }

}
