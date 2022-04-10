package pl.adi.timeanalysistool.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.adi.timeanalysistool.domain.model.Function;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.extractfromlog.ExtractFromLog;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FunctionRepoTest {

    @Autowired
    private TestPlanRepo testPlanRepoUnderTest;
    @Autowired
    private FunctionRepo functionRepoUnderTest;

    @Test
    void shouldExistsByFunctionName() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                "IBN1",
                "Vehicle 1"
        );
        testPlanRepoUnderTest.save(testPlan);
        String bcmFunctionName = testPlan.getVehicle().getEcuMap()
                .get("BCM_1").getFunctionList()
                .stream().findFirst().get().getFunctionName();

        //when
        boolean expected = functionRepoUnderTest.existsByFunctionName(bcmFunctionName);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void shouldNotExistsByFunctionName() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                "IBN1",
                "Vehicle 1"
        );
        testPlanRepoUnderTest.save(testPlan);
        String bcmFunctionName = "TEST";

        //when
        boolean expected = functionRepoUnderTest.existsByFunctionName(bcmFunctionName);

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void shouldFindAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String vehicleTyp = "Vehicle 1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                "IBN1",
                vehicleTyp
        );
        testPlanRepoUnderTest.save(testPlan);
        String ecuName = testPlan.getVehicle().getEcuMap().get("BCM_1").getEcuName();
        String functionName = testPlan.getVehicle().getEcuMap().get("BCM_1").getFunctionList()
                .stream().findFirst().get().getFunctionName();

        //when
        List<Function> foundFunctionsList = functionRepoUnderTest.findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(vehicleTyp, ecuName, functionName);

        //then
        assertThat(foundFunctionsList).isNotNull();
    }

    @Test
    void shouldFindAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String vehicleTyp = "Vehicle 1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                "IBN1",
                vehicleTyp
        );
        testPlanRepoUnderTest.save(testPlan);
        String ecuName = testPlan.getVehicle().getEcuMap().get("BCM_1").getEcuName();

        //when
        List<String> foundDistinctFunctionNames = functionRepoUnderTest.findAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(vehicleTyp, ecuName);

        //then
        assertThat(foundDistinctFunctionNames).isNotNull();
    }

    @Test
    void shouldFindAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                testLocation,
                vehicleTyp
        );
        testPlanRepoUnderTest.save(testPlan);
        String ecuName = testPlan.getVehicle().getEcuMap().get("BCM_1").getEcuName();
        String functionName = testPlan.getVehicle().getEcuMap().get("BCM_1").getFunctionList()
                .stream().findFirst().get().getFunctionName();

        //when
        List<Double> foundFunctionsDurations = functionRepoUnderTest.findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(vehicleTyp, testLocation, ecuName, functionName);

        //then
        assertThat(foundFunctionsDurations).isNotNull();
    }
}