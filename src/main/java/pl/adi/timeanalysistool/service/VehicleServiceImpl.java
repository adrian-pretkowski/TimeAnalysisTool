package pl.adi.timeanalysistool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adi.timeanalysistool.domain.model.Vehicle;
import pl.adi.timeanalysistool.exception.FunctionNotFoundException;
import pl.adi.timeanalysistool.exception.VehicleNotFoundException;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.FunctionRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepo vehicleRepo;
    private final EcuRepo ecuRepo;
    private final FunctionRepo functionRepo;


    @Override
    public Vehicle getVehicleByEcuId(Long ecuId) {
        log.info("[DB] Fetching Vehicle based on EcuID: {}", ecuId);
        boolean existsById = ecuRepo.existsById(ecuId);

        if (existsById) {
            return vehicleRepo.findVehicleByEcuId(ecuId);
        } else {
            throw new VehicleNotFoundException("Ecu with given ID does not exist.");
        }
    }

    @Override
    public Vehicle getVehicleByFunctionId(Long functionId) {
        log.info("[DB] Fetching Vehicle based on FunctionID: {}", functionId);
        boolean existsById = functionRepo.existsById(functionId);
        if (existsById) {
            return vehicleRepo.findVehicleByFunctionId(functionId);
        } else {
            throw new FunctionNotFoundException("Function with given ID does not exist.");
        }
    }


    @Override
    public List<String> getDistinctVehicles() {
        log.info("[DB] Fetching distinct vehicles in db...");
        return vehicleRepo.findDistinctVehicles();
    }
}
