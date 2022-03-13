package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.adi.timeanalysistool.domain.model.Function;

import java.util.List;

public interface FunctionRepo extends JpaRepository<Function, Long> {
    Function findByFunctionName(String functionName);

    @Query("SELECT f.duration from Function f " +
            "JOIN f.ecu e " +
            "JOIN e.vehicle v " +
            "INNER JOIN TestPlan tp ON v.testPlan = tp.vehicle " +
            "WHERE v.vehicleTyp = :vehicleTyp " +
            "AND tp.testLocation = :testLocation " +
            "AND e.ecuName = :ecuName " +
            "AND f.functionName =:functionName")
    List<Double> findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(@Param("vehicleTyp") String vehicleTyp,
                                                                                          @Param("testLocation") String testLocation,
                                                                                          @Param("ecuName") String ecuName,
                                                                                          @Param("functionName") String functionName);

    void deleteById(Long id);
}
