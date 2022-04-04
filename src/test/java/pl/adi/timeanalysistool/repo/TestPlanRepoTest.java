package pl.adi.timeanalysistool.repo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.extractfromlog.ExtractFromLog;

import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestPlanRepoTest {

    @Autowired
    private TestPlanRepo underTest;

    @Test
    void itShouldFindTestPlanByEcuId() {
        // given
        URL resourceURL = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        ExtractFromLog extract = new ExtractFromLog();
        assert resourceURL != null;
        TestPlan testPlan = extract.extractLog(resourceURL.getPath(), "IBN3", "Vehicle 1");
        underTest.save(testPlan);

        //when
        Long bcm_1 = testPlan.getVehicle().getEcuMap().get("BCM_1").getId();
        TestPlan foundTestPlan = underTest.findTestPlanByEcuId(bcm_1);

        //then
        assertThat(foundTestPlan.getId()).isEqualTo(testPlan.getId());
    }

    @Test
    @Disabled
    void findTestPlanByFunctionId() {
    }

    @Test
    @Disabled
    void findDistinctTestLocationsBasedOnVehicleTyp() {
    }

    @Test
    @Disabled
    void findTestPlansBasedOnVehicleTypAndTestLocation() {
    }
}