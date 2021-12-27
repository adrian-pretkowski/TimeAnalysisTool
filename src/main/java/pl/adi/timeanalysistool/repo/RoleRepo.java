package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
