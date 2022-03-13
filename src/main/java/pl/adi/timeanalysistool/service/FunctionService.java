package pl.adi.timeanalysistool.service;

import java.util.List;

public interface FunctionService {
    List<Double> getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(String testLocation,
                                                                                                   String vehicleTyp,
                                                                                                   String ecuName,
                                                                                                   String functionName);
}
