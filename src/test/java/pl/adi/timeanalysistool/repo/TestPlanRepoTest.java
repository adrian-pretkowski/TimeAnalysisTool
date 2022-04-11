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
class TestPlanRepoTest {

    @Autowired
    private TestPlanRepo testPlanRepoUnderTest;

    @Test
    void shouldExistsByTestLocation() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String testLocation = "IBN1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                testLocation,
                "Vehicle 1"
        );
        testPlanRepoUnderTest.save(testPlan);

        //when
        boolean expected = testPlanRepoUnderTest.existsByTestLocation(testLocation);

        //then
        assertThat(expected).isTrue();

    }

    @Test
    void shouldNotExistByTestLocation() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        String testLocation = "IBN1";
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                "IBN3",
                "Vehicle 1"
        );
        testPlanRepoUnderTest.save(testPlan);

        //when
        boolean expected = testPlanRepoUnderTest.existsByTestLocation(testLocation);

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void shouldFindTestPlanByEcuId() {
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
        Long ecuID = testPlan.getVehicle().getEcuMap().get("BCM_1").getId();

        //when
        Long foundTestPlanId = testPlanRepoUnderTest.findTestPlanByEcuId(ecuID).getId();

        //then
        assertThat(foundTestPlanId).isEqualTo(expectedTestPlanId);

    }

    @Test
    void findTestPlanByFunctionId() {
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
        Long bcmFunctionId = testPlan.getVehicle().getEcuMap().get("BCM_1")
                .getFunctionList().stream().findFirst().get().getId();

        //when
        Long foundTestPlanId = testPlanRepoUnderTest.findTestPlanByFunctionId(bcmFunctionId).getId();

        //then
        assertThat(foundTestPlanId).isEqualTo(expectedTestPlanId);
    }

    @Test
    void shouldFindDistinctTestLocationsBasedOnVehicleTyp() {
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
        List<String> foundTestLocations = testPlanRepoUnderTest.findDistinctTestLocationsBasedOnVehicleTyp(vehicleTyp);

        //then
        assertThat(foundTestLocations).isNotNull();
    }

    @Test
    void findTestPlansBasedOnVehicleTypAndTestLocation() {
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

        //when
        List<TestPlan> foundTestPlans = testPlanRepoUnderTest.findTestPlansBasedOnVehicleTypAndTestLocation(vehicleTyp, testLocation);

        //then
        assertThat(foundTestPlans).isNotNull();

    }
}