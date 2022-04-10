package pl.adi.timeanalysistool.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.adi.timeanalysistool.domain.model.Ecu;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.extractfromlog.ExtractFromLog;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EcuRepoTest {

    @Autowired
    private TestPlanRepo testPlanRepoUnderTest;
    @Autowired
    private EcuRepo ecuRepoUnderTest;

    @Test
    void shouldExistsByEcuName() {
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
        String ecuName = testPlan.getVehicle().getEcuMap().get("BCM_1").getEcuName();

        //when
        boolean expected = ecuRepoUnderTest.existsByEcuName(ecuName);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void shouldNotExistsByEcuName() {
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
        String ecuName = "TEST";

        //when
        boolean expected = ecuRepoUnderTest.existsByEcuName(ecuName);

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void shouldFindAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                testLocation,
                vehicleTyp
        );
        testPlanRepoUnderTest.save(testPlan);

        //when
        List<String> foundDistinctEcuNames = ecuRepoUnderTest.findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation);

        //then
        assertThat(foundDistinctEcuNames).isNotNull();

    }

    @Test
    void shouldFindAllDistinctEcuNamesBasedOnVehicleTyp() {
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

        //when
        List<String> foundDistinctEcuNames = ecuRepoUnderTest.findAllDistinctEcuNamesBasedOnVehicleTyp(vehicleTyp);

        //then
        assertThat(foundDistinctEcuNames).isNotNull();
    }

    @Test
    void shouldFindAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                testLocation,
                vehicleTyp
        );
        testPlanRepoUnderTest.save(testPlan);
        String ecuName = testPlan.getVehicle().getEcuMap().get("BCM_1").getEcuName();

        //when
        List<Ecu> foundEcusList = ecuRepoUnderTest.findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(vehicleTyp, testLocation, ecuName);

        //then
        assertThat(foundEcusList).isNotNull();
    }
}