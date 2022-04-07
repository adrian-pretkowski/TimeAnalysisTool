package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.FunctionNotFoundException;
import pl.adi.timeanalysistool.exception.TestPlanNotFoundException;
import pl.adi.timeanalysistool.exception.VehicleNotFoundException;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.FunctionRepo;
import pl.adi.timeanalysistool.repo.TestPlanRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TestPlanServiceImpl implements TestPlanService {

    private final TestPlanRepo testPlanRepo;
    private final EcuRepo ecuRepo;
    private final FunctionRepo functionRepo;
    private final VehicleRepo vehicleRepo;

    @Override
    public TestPlan getTestPlanByEcuId(Long ecuId) {
        log.info("[DB] Fetching TestPlan based on EcuID: {}", ecuId);
        boolean existsById = ecuRepo.existsById(ecuId);
        if (existsById) {
            return testPlanRepo.findTestPlanByEcuId(ecuId);
        } else {
            throw new EcuNotFoundException("Ecu with given ID does not exist.");
        }
    }

    @Override
    public TestPlan getTestPlanByFunctionId(Long functionId) {
        log.info("[DB] Fetching TestPlan based on FunctionID: {}", functionId);
        boolean existsById = functionRepo.existsById(functionId);
        if (existsById) {
            return testPlanRepo.findTestPlanByFunctionId(functionId);
        } else {
            throw new FunctionNotFoundException("Function with given ID does not exist");
        }
    }

    @Override
    public TestPlan saveTestPlan(TestPlan testPlan) {
        log.info("[DB] Saving new TestPlan, location: {} to the database.", testPlan.getTestLocation());
        //ToDo: Validate if testPlan exist (check if Vehicle with KnNr exists for TestLocation)
        return testPlanRepo.save(testPlan);
    }

    @Override
    public List<String> getDistinctTestLocationByVehicleTyp(String vehicleTyp) {
        log.info("[DB] Fetching distinct test locations for vehicle: {}", vehicleTyp);
        boolean existsByVehicleTyp = vehicleRepo.existsByVehicleTyp(vehicleTyp);
        if (existsByVehicleTyp) {
            return testPlanRepo.findDistinctTestLocationsBasedOnVehicleTyp(vehicleTyp);
        } else {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist.");
        }
    }

    @Override
    public TestPlan getTestPlanById(Long id) {
        log.info("[DB] Fetching TestPlan id: {}", id);
        return testPlanRepo.findById(id).orElseThrow(
                () -> new VehicleNotFoundException("" +
                        "Test Plan with given ID does not exist."));
    }

    @Override
    public List<TestPlan> getTestPlansBasedOnVehicleTypAndTestLocation(String vehicleTyp, String testLocation) {
        log.info("[DB] Fetching TestPlans based on vehicleTyp: {} and testLocation: {}", vehicleTyp, testLocation);
        boolean existsByTestLocation = testPlanRepo.existsByTestLocation(testLocation);
        boolean existsByVehicleTyp = vehicleRepo.existsByVehicleTyp(vehicleTyp);
        if (!existsByTestLocation) {
            throw new TestPlanNotFoundException("Given Test Location does not exist.");
        }
        if (!existsByVehicleTyp) {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist");
        }
        return testPlanRepo.findTestPlansBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation);
    }

    @Override
    public List<TestPlan> getTestPlans() {
        log.info("[DB] Fetching all TestPlans...");
        return testPlanRepo.findAll();
    }
}
