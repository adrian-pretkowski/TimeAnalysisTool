package pl.adi.timeanalysistool.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.extractfromlog.ExtractFromLog;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class VehicleRepoTest {

    @Autowired
    private TestPlanRepo testPlanRepoUnderTest;
    @Autowired
    private VehicleRepo vehicleRepoUnderTest;

    @Test
    void shouldExistsByVehicleTyp() {
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
        boolean expected = vehicleRepoUnderTest.existsByVehicleTyp(vehicleTyp);

        //then
        assertThat(expected).isTrue();

    }

    @Test
    void shouldNotExistsByVehicleTyp() {
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
        boolean expected = vehicleRepoUnderTest.existsByVehicleTyp("Vehicle 2");

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void shouldFindDistinctVehicles() {
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

        //when
        List<String> foundDistinctVehicles = vehicleRepoUnderTest.findDistinctVehicles();

        //then
        assertThat(foundDistinctVehicles).isNotNull();
    }

    @Test
    void shouldFindVehicleByEcuId() {
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

        Long expectedTestPlanId = testPlan.getId();
        Long ecuId = testPlan.getVehicle().getEcuMap().get("BCM_1").getId();

        //when
        Long foundTestPlanId = vehicleRepoUnderTest.findVehicleByEcuId(ecuId).getId();

        //then
        assertThat(foundTestPlanId).isEqualTo(expectedTestPlanId);

    }

    @Test
    void shouldFindVehicleByFunctionId() {
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

        Long expectedVehicleId = testPlan.getVehicle().getId();
        Long bcmFunctionId = testPlan.getVehicle().getEcuMap().get("BCM_1").getFunctionList()
                .stream().findFirst().get().getId();

        //when
        Long foundVehicleId = vehicleRepoUnderTest.findVehicleByFunctionId(bcmFunctionId).getId();

        //then
        assertThat(foundVehicleId).isEqualTo(expectedVehicleId);
    }
}