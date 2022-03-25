package pl.adi.timeanalysistool.service;

import pl.adi.timeanalysistool.domain.model.Function;

import java.util.List;

public interface FunctionService {

    List<Function> getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(String vehicleTyp,
                                                                             String ecuName,
                                                                             String functionName);

    List<String> getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(String vehicleTyp,
                                                                        String ecuName);

    List<Double> getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(String testLocation,
                                                                                                   String vehicleTyp,
                                                                                                   String ecuName,
                                                                                                   String functionName);
}
