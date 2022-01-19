package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.model.Ecu;

public interface EcuRepo extends JpaRepository<Ecu, Long> {
    Ecu findByEcuName(String ecuName);

    void deleteById(Long id);
}
