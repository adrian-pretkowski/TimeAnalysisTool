package pl.adi.timeanalysistool.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.service.TestPlanService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TestPlanResource {
    private final TestPlanService testPlanService;

    @GetMapping("/testplans")
    public ResponseEntity<List<TestPlan>> getTestPlans() {
        return ResponseEntity.ok().body(testPlanService.getTestPlans());
    }

    @GetMapping("/testplans/locations/{vehicleTyp}")
    public ResponseEntity<List<String>> getDistinctLocationsBasedOnVehicleTyp(@PathVariable String vehicleTyp) {
        return ResponseEntity.ok().body(testPlanService.getDistinctTestLocationByVehicleTyp(vehicleTyp));
    }

    @GetMapping("/testplans/{vehicleTyp}/{testLocation}")
    public ResponseEntity<List<TestPlan>> getTestPlansBasedOnVehicleTypAndLocation(@PathVariable String vehicleTyp, @PathVariable String testLocation) {
        return ResponseEntity.ok().body(testPlanService.getTestPlansBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation));
    }

    @PostMapping("/testplans/save")
    public ResponseEntity<TestPlan> saveTestPlanToDatabase(@RequestBody List<String> fileLines) {
        for (String fileLine : fileLines) {
            System.out.println(fileLine);
        }
        return ResponseEntity.ok().build();
    }
}
