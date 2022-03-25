package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.model.Ecu;

import java.util.List;

public interface EcuService {
    List<String> getAllDistinctEcuNamesBasedOnVehicleTyp(String vehicleTyp);

    List<String> getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(String testLocation,
                                                                        String vehicleTyp);

    List<Ecu> getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
            String ecuName,
            String vehicleTyp,
            String testLocation
    );
}
