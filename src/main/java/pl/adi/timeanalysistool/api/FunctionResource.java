package pl.adi.timeanalysistool.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adi.timeanalysistool.service.FunctionService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/functions")
@RequiredArgsConstructor
@Slf4j
public class FunctionResource {
    private final FunctionService functionService;

    @GetMapping("durations/{vehicleTyp}/{testLocation}/{ecuName}/{functionName}")
    public ResponseEntity<List<Double>> getFunctionDurationsBasedOnVehicleAndLocation(@PathVariable String vehicleTyp,
                                                                                      @PathVariable String testLocation,
                                                                                      @PathVariable String ecuName,
                                                                                      @PathVariable String functionName) {
        return ResponseEntity.ok().body(functionService.getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                testLocation, vehicleTyp, ecuName, functionName
        ));
    }

}
