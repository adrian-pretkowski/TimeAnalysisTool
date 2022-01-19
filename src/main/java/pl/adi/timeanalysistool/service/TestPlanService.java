package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.model.TestPlan;

import java.util.List;

public interface TestPlanService {
    TestPlan saveTestPlan (TestPlan testPlan);
    TestPlan getTestPlanById (Long id);
    List<TestPlan> getTestPlans();
}
