package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.adi.timeanalysistool.domain.model.Vehicle;

import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    boolean existsByVehicleTyp(String vehicleTyp);

    @Query("select distinct vehicleTyp from Vehicle")
    List<String> findDistinctVehicles();

    @Query("SELECT v from Vehicle v " +
            "JOIN v.ecuMap e " +
            "WHERE e.id = :ecuId ")
    Vehicle findVehicleByEcuId(@Param("ecuId") Long ecuId);

    @Query("SELECT v from Vehicle v " +
            "JOIN v.ecuMap e " +
            "JOIN e.functionList f " +
            "WHERE f.id = :functionId")
    Vehicle findVehicleByFunctionId(@Param("functionId") Long functionId);

}
