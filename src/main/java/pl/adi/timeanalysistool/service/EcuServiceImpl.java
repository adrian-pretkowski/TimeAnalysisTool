package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.Ecu;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.TestPlanNotFoundException;
import pl.adi.timeanalysistool.exception.VehicleNotFoundException;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.TestPlanRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EcuServiceImpl implements EcuService {

    private final EcuRepo ecuRepo;
    private final VehicleRepo vehicleRepo;
    private final TestPlanRepo testPlanRepo;

    @Override
    public List<String> getAllDistinctEcuNamesBasedOnVehicleTyp(String vehicleTyp) throws VehicleNotFoundException {
        log.info("[DB] Fetching distinct Ecu Names based on vehicle typ: {}", vehicleTyp);
        boolean existsByVehicleTyp = vehicleRepo.existsByVehicleTyp(vehicleTyp);

        if (existsByVehicleTyp) {
            return ecuRepo.findAllDistinctEcuNamesBasedOnVehicleTyp(vehicleTyp);
        } else {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist.");
        }
    }

    @Override
    public List<String> getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(String testLocation, String vehicleTyp) {
        log.info("[DB] Fetching distinct Ecu Names based on test location: {} and vehicle typ: {}", testLocation, vehicleTyp);
        boolean existsByVehicleTyp = vehicleRepo.existsByVehicleTyp(vehicleTyp);
        boolean existsByTestLocation = testPlanRepo.existsByTestLocation(testLocation);
        if (!existsByVehicleTyp) {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist.");
        }
        if (!existsByTestLocation) {
            throw new TestPlanNotFoundException("Given Test Location does not exist.");
        }
        return ecuRepo.findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation);
    }

    @Override
    public List<Ecu> getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(String ecuName, String vehicleTyp, String testLocation) {
        log.info("[DB] Fetching Ecus: {}, based on test location: {} and vehicle typ: {}", ecuName, testLocation, vehicleTyp);
        boolean existsByVehicleTyp = vehicleRepo.existsByVehicleTyp(vehicleTyp);
        boolean existsByTestLocation = testPlanRepo.existsByTestLocation(testLocation);
        boolean existsByEcuName = ecuRepo.existsByEcuName(ecuName);

        if (!existsByVehicleTyp) {
            throw new VehicleNotFoundException("Given Vehicle Typ does not exist");
        }
        if (!existsByTestLocation) {
            throw new TestPlanNotFoundException("Given Test Location does not exist");
        }
        if (!existsByEcuName) {
            throw new EcuNotFoundException("Given Ecu Name does not exist!");
        }

        return ecuRepo.findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(vehicleTyp, testLocation, ecuName);
    }
}
