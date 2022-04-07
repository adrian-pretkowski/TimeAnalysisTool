package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.adi.timeanalysistool.domain.model.Ecu;

import java.util.List;

public interface EcuRepo extends JpaRepository<Ecu, Long> {

    boolean existsByEcuName(String ecuName);

    @Query("SELECT DISTINCT e.ecuName from Ecu e " +
            "JOIN e.vehicle v " +
            "JOIN v.testPlan tp " +
            "WHERE v.vehicleTyp = :vehicleTyp " +
            "AND tp.testLocation = :testLocation")
    List<String> findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(@Param("vehicleTyp") String vehicleTyp,
                                                                         @Param("testLocation") String testLocation);

    @Query("SELECT DISTINCT e.ecuName from Ecu e " +
            "JOIN e.vehicle v " +
            "WHERE v.vehicleTyp = :vehicleTyp")
    List<String> findAllDistinctEcuNamesBasedOnVehicleTyp(@Param("vehicleTyp") String vehicleTyp);

    @Query("SELECT e from Ecu e " +
            "JOIN e.vehicle v " +
            "JOIN v.testPlan tp " +
            "WHERE v.vehicleTyp = :vehicleTyp " +
            "AND tp.testLocation = :testLocation " +
            "AND e.ecuName = :ecuName")
    List<Ecu> findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
            @Param("vehicleTyp") String vehicleTyp,
            @Param("testLocation") String testLocation,
            @Param("ecuName") String ecuName
    );

    void deleteById(Long id);
}
