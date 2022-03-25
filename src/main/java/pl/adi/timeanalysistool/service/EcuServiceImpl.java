package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.Ecu;
import pl.adi.timeanalysistool.repo.EcuRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EcuServiceImpl implements EcuService{

    private final EcuRepo ecuRepo;

    @Override
    public List<String> getAllDistinctEcuNamesBasedOnVehicleTyp(String vehicleTyp) {
        log.info("[DB] Fetching distinct Ecu Names based on vehicle typ: {}", vehicleTyp);
        return ecuRepo.findAllDistinctEcuNamesBasedOnVehicleTyp(vehicleTyp);
    }

    @Override
    public List<String> getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(String testLocation, String vehicleTyp) {
        log.info("[DB] Fetching distinct Ecu Names based on test location: {} and vehicle typ: {}", testLocation, vehicleTyp);
        return ecuRepo.findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation);
    }

    @Override
    public List<Ecu> getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(String ecuName, String vehicleTyp, String testLocation) {
        log.info("[DB] Fetching Ecus: {}, based on test location: {} and vehicle typ: {}", ecuName, testLocation, vehicleTyp);
        return ecuRepo.findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(vehicleTyp, testLocation, ecuName);
    }
}
