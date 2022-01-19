package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.model.Vehicle;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    Vehicle findByKennNumber(String kennNumber);

    void deleteById(Long id);
}
