package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.adi.timeanalysistool.domain.model.TestPlan;

import java.util.List;

public interface TestPlanRepo extends JpaRepository<TestPlan, Long> {
    TestPlan findByTestLocation(String testLocation);

    @Query("SELECT DISTINCT testLocation from TestPlan")
    List<String> findDistinctTestLocations();

    @Query("SELECT DISTINCT tp.testLocation from TestPlan tp " +
            "INNER JOIN Vehicle v ON tp.vehicle = v.testPlan " +
            "WHERE v.vehicleTyp = :vehicleTyp")
    List<String> findDistinctTestLocationsBasedOnVehicleTyp(@Param("vehicleTyp") String vehicleTyp);

    @Query("SELECT DISTINCT tp from TestPlan tp " +
            "INNER JOIN Vehicle v ON tp.vehicle = v.testPlan " +
            "WHERE v.vehicleTyp = :vehicleTyp AND tp.testLocation = :testLocation")
    List<TestPlan> findTestPlansBasedOnVehicleTypAndTestLocation(@Param("vehicleTyp") String vehicleTyp,
                                                                 @Param("testLocation") String testLocation);

    void deleteById(Long id);
}
