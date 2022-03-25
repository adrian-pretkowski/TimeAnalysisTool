package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle getVehicleByEcuId(Long ecuId);

    Vehicle getVehicleByFunctionId(Long functionId);

    List<String> getDistinctVehicles();

    List<Vehicle> getVehicles();
}
