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
    public Vehicle getVehicleById(Long id) {
        return null;
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
