package pl.adi.timeanalysistool.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
