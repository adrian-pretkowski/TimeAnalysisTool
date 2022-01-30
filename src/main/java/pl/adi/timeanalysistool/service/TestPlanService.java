package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.model.TestPlan;

import java.util.List;

public interface TestPlanService {
    TestPlan saveTestPlan (TestPlan testPlan);
    List<String> getDistinctTestLocationByVehicleTyp(String vehicleTyp);
    TestPlan getTestPlanById (Long id);
    List<TestPlan> getTestPlansBasedOnVehicleTypAndTestLocation(String vehicleTyp, String testLocation);
    List<TestPlan> getTestPlans();
}
