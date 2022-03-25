package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.Function;
import pl.adi.timeanalysistool.repo.FunctionRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepo functionRepo;

    @Override
    public List<Function> getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(String vehicleTyp, String ecuName, String functionName) {
        log.info("[DB] Fetching all Functions based on vehicleTyp: {}, ecuName: {} and functionName: {}",
                vehicleTyp, ecuName, functionName);
        return functionRepo.findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(vehicleTyp, ecuName, functionName);
    }

    @Override
    public List<String> getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(String vehicleTyp, String ecuName) {
        log.info("[DB] Fetching all distinct Function Names based on vehicleTyp: {} and ecuName: {}", vehicleTyp, ecuName);
        return functionRepo.findAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(vehicleTyp, ecuName);
    }

    @Override
    public List<Double> getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(String testLocation, String vehicleTyp, String ecuName, String functionName) {
        log.info("[DB] Fetching all function: '{}' durations based on test location: {}, vehicle typ: {}, ecu name: {}...", functionName, testLocation, vehicleTyp, ecuName);
        return functionRepo.findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(vehicleTyp, testLocation, ecuName, functionName);
    }
}
