package pl.adi.timeanalysistool.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adi.timeanalysistool.domain.model.Function;
import pl.adi.timeanalysistool.service.FunctionService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/functions")
@RequiredArgsConstructor
@Slf4j
public class FunctionResource {
    private final FunctionService functionService;

    @GetMapping("/{vehicleTyp}/{ecuName}/{functionName}")
    public ResponseEntity<List<Function>> getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
            @PathVariable String vehicleTyp,
            @PathVariable String ecuName,
            @PathVariable String functionName) {
        return ResponseEntity.ok().body(
                functionService.getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                        vehicleTyp, ecuName, functionName));
    }

    @GetMapping("/distinct-names/{vehicleTyp}/{ecuName}")
    public ResponseEntity<List<String>> getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
            @PathVariable String vehicleTyp,
            @PathVariable String ecuName) {
        return ResponseEntity.ok()
                .body(functionService.getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(vehicleTyp, ecuName));
    }

    @GetMapping("/durations/{vehicleTyp}/{testLocation}/{ecuName}/{functionName}")
    public ResponseEntity<List<Double>> getFunctionDurationsBasedOnVehicleAndLocation(
            @PathVariable String vehicleTyp,
            @PathVariable String testLocation,
            @PathVariable String ecuName,
            @PathVariable String functionName) {
        return ResponseEntity.ok().body(functionService.getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                testLocation, vehicleTyp, ecuName, functionName
        ));
    }

}
