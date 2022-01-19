package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.model.TestPlan;

public interface TestPlanRepo extends JpaRepository<TestPlan, Long> {
    TestPlan findByTestLocation(String testLocation);
    void deleteById (Long id);
}
