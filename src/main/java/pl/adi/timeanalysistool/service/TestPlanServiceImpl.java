package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.repo.TestPlanRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TestPlanServiceImpl implements TestPlanService {

    private final TestPlanRepo testPlanRepo;

    @Override
    public TestPlan saveTestPlan(TestPlan testPlan) {
        log.info("[DB] Saving new TestPlan, location: {} to the database.", testPlan.getTestLocation());
        return testPlanRepo.save(testPlan);
    }

    @Override
    public List<String> getDistinctTestLocationByVehicleTyp(String vehicleTyp) {
        log.info("[DB] Fetching distinct test locations for vehicle: {}", vehicleTyp);
        return testPlanRepo.findDistinctTestLocationsBasedOnVehicleTyp(vehicleTyp);
    }

    @Override
    public TestPlan getTestPlanById(Long id) {
        log.info("[DB] Fetching TestPlan id: {}", id);
        return testPlanRepo.findById(id).orElseThrow();
    }

    @Override
    public List<TestPlan> getTestPlansBasedOnVehicleTypAndTestLocation(String vehicleTyp, String testLocation) {
        log.info("[DB] Fetching TestPlans based on vehicleTyp: {} and testLocation: {}", vehicleTyp, testLocation);
        return testPlanRepo.findTestPlansBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation);
    }

    @Override
    public List<TestPlan> getTestPlans() {
        log.info("[DB] Fetching all TestPlans...");
        return testPlanRepo.findAll();
    }
}
