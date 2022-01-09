package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    void deleteByUsername (String username);
    void deleteById (Long id);
}
