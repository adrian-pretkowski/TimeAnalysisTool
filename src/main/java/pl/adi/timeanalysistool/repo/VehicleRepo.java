package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.adi.timeanalysistool.domain.model.Vehicle;

import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    Vehicle findByKennNumber(String kennNumber);

    @Query("select distinct vehicleTyp from Vehicle")
    List<String> findDistinctVehicles();

    void deleteById(Long id);
}
