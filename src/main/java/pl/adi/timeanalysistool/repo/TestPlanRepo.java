package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.adi.timeanalysistool.domain.model.TestPlan;

import java.util.List;

public interface TestPlanRepo extends JpaRepository<TestPlan, Long> {

    boolean existsByTestLocation(String testLocation);

    @Query("SELECT tp from TestPlan tp " +
            "JOIN tp.vehicle v " +
            "JOIN v.ecuMap e " +
            "WHERE e.id = :ecuId")
    TestPlan findTestPlanByEcuId(@Param("ecuId") Long ecuId);

    @Query("SELECT tp from TestPlan tp " +
            "JOIN tp.vehicle v " +
            "JOIN v.ecuMap e " +
            "JOIN e.functionList f " +
            "WHERE f.id = :functionId")
    TestPlan findTestPlanByFunctionId(@Param("functionId") Long functionId);

    @Query("SELECT DISTINCT tp.testLocation from TestPlan tp " +
            "INNER JOIN Vehicle v ON tp.vehicle = v.testPlan " +
            "WHERE v.vehicleTyp = :vehicleTyp")
    List<String> findDistinctTestLocationsBasedOnVehicleTyp(@Param("vehicleTyp") String vehicleTyp);

    @Query("SELECT DISTINCT tp from TestPlan tp " +
            "INNER JOIN Vehicle v ON tp.vehicle = v.testPlan " +
            "WHERE v.vehicleTyp = :vehicleTyp AND tp.testLocation = :testLocation")
    List<TestPlan> findTestPlansBasedOnVehicleTypAndTestLocation(@Param("vehicleTyp") String vehicleTyp,
                                                                 @Param("testLocation") String testLocation);

}
