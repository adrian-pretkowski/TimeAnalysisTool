package pl.adi.timeanalysistool.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adi.timeanalysistool.domain.model.Ecu;
import pl.adi.timeanalysistool.service.EcuService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/ecus")
@RequiredArgsConstructor
@Slf4j
public class EcuResource {
    private final EcuService ecuService;

    @GetMapping("/distinct-names/{vehicleTyp}")
    public ResponseEntity<List<String>> getDistinctEcuNamesBasedOnVehicleTyp(
            @PathVariable String vehicleTyp) {
        return ResponseEntity.ok().body(ecuService.getAllDistinctEcuNamesBasedOnVehicleTyp(vehicleTyp));
    }

    @GetMapping("/distinct-names/{vehicleTyp}/{testLocation}")
    public ResponseEntity<List<String>> getDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
            @PathVariable String vehicleTyp,
            @PathVariable String testLocation) {
        return ResponseEntity.ok().body(ecuService
                .getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                        testLocation, vehicleTyp));
    }

    @GetMapping("/{vehicleTyp}/{testLocation}/{ecuName}")
    public ResponseEntity<List<Ecu>> getEcusBasedOnVehicleTypAndTestLocationAndEcuName(
            @PathVariable String vehicleTyp,
            @PathVariable String testLocation,
            @PathVariable String ecuName) {
        return ResponseEntity.ok().body(
                ecuService.getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                        ecuName, vehicleTyp, testLocation
                ));
    }

}
