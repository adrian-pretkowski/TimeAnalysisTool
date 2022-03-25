package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.Vehicle;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepo vehicleRepo;

    @Override
    public Vehicle getVehicleByEcuId(Long ecuId) {
        log.info("[DB] Fetching Vehicle based on EcuID: {}", ecuId);
        return vehicleRepo.findVehicleByEcuId(ecuId);
    }

    @Override
    public Vehicle getVehicleByFunctionId(Long functionId) {
        log.info("[DB] Fetching Vehicle based on FunctionID: {}", functionId);
        return vehicleRepo.findVehicleByFunctionId(functionId);
    }


    @Override
    public List<String> getDistinctVehicles() {
        log.info("[DB] Fetching distinct vehicles in db...");
        return vehicleRepo.findDistinctVehicles();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return null;
    }
}
