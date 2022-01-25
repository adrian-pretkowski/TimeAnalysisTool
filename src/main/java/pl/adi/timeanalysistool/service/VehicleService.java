package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.model.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle getVehicleById(Long id);

    List<String> getDistinctVehicles();

    List<Vehicle> getVehicles();
}
