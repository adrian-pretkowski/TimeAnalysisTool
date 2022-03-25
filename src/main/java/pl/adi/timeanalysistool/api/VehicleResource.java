package pl.adi.timeanalysistool.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adi.timeanalysistool.domain.model.Vehicle;
import pl.adi.timeanalysistool.service.VehicleService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleResource {
    private final VehicleService vehicleService;

    @GetMapping("/distinct")
    public ResponseEntity<List<String>> getDistinctVehicles() {
        return ResponseEntity.ok().body(vehicleService.getDistinctVehicles());
    }

    @GetMapping("/based-on-function-id/{functionId}")
    public ResponseEntity<Vehicle> getVehicleBasedOnFunctionId(@PathVariable Long functionId) {
        return ResponseEntity.ok().body(vehicleService.getVehicleByFunctionId(functionId));
    }

    @GetMapping("/based-on-ecu-id/{ecuId}")
    public ResponseEntity<Vehicle> getVehicleBasedOnEcuId(@PathVariable Long ecuId) {
        return ResponseEntity.ok().body(vehicleService.getVehicleByEcuId(ecuId));
    }
}
