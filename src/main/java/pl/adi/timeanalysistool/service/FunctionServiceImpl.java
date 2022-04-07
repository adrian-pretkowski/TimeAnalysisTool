package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.Function;
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
public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepo functionRepo;
    private final EcuRepo ecuRepo;
    private final VehicleRepo vehicleRepo;
    private final TestPlanRepo testPlanRepo;

    @Override
    public List<Function> getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(String vehicleTyp, String ecuName, String functionName) {
        log.info("[DB] Fetching all Functions based on vehicleTyp: {}, ecuName: {} and functionName: {}",
                vehicleTyp, ecuName, functionName);

        if (checkIfVehicleTypNotExists(vehicleTyp)) {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist.");
        }
        if (checkIfEcuNameNotExists(ecuName)) {
            throw new EcuNotFoundException("Given Ecu Name does not exist.");
        }
        if (checkIfFunctionNameNotExists(functionName)) {
            throw new FunctionNotFoundException("Given Function Name does not exist.");
        }
        return functionRepo.findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(vehicleTyp, ecuName, functionName);
    }

    @Override
    public List<String> getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(String vehicleTyp, String ecuName) {
        log.info("[DB] Fetching all distinct Function Names based on vehicleTyp: {} and ecuName: {}", vehicleTyp, ecuName);

        if (checkIfVehicleTypNotExists(vehicleTyp)) {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist.");
        }
        if (checkIfEcuNameNotExists(ecuName)) {
            throw new EcuNotFoundException("Given Ecu Name does not exist.");
        }
        return functionRepo.findAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(vehicleTyp, ecuName);
    }

    @Override
    public List<Double> getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(String testLocation, String vehicleTyp, String ecuName, String functionName) {
        log.info("[DB] Fetching all function: '{}' durations based on test location: {}, vehicle typ: {}, ecu name: {}...", functionName, testLocation, vehicleTyp, ecuName);

        if (checkIfTestLocationNotExists(testLocation)) {
            throw new TestPlanNotFoundException("Given Test Location does not exist.");
        }
        if (checkIfVehicleTypNotExists(vehicleTyp)) {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist.");
        }
        if (checkIfEcuNameNotExists(ecuName)) {
            throw new EcuNotFoundException("Given Ecu Name does not exist.");
        }
        if (checkIfFunctionNameNotExists(functionName)) {
            throw new FunctionNotFoundException("Given Function Name does not exist.");
        }

        return functionRepo.findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(vehicleTyp, testLocation, ecuName, functionName);
    }

    private boolean checkIfVehicleTypNotExists(String vehicleTyp) {
        return !vehicleRepo.existsByVehicleTyp(vehicleTyp);
    }

    private boolean checkIfEcuNameNotExists(String ecuName) {
        return !ecuRepo.existsByEcuName(ecuName);
    }

    private boolean checkIfTestLocationNotExists(String testLocation) {
        return !testPlanRepo.existsByTestLocation(testLocation);
    }

    private boolean checkIfFunctionNameNotExists(String functionName) {
        return !functionRepo.existsByFunctionName(functionName);
    }

}
