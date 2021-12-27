package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
