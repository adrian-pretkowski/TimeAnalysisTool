package pl.adi.timeanalysistool.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.FunctionNotFoundException;
import pl.adi.timeanalysistool.extractfromlog.ExtractFromLog;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.FunctionRepo;
import pl.adi.timeanalysistool.repo.TestPlanRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestPlanServiceImplTest {

    @Mock private TestPlanRepo testPlanRepo;
    @Mock private VehicleRepo vehicleRepo;
    @Mock private EcuRepo ecuRepo;
    @Mock private FunctionRepo functionRepo;
    private TestPlanServiceImpl testPlanServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        testPlanServiceImplUnderTest = new TestPlanServiceImpl(
                testPlanRepo, ecuRepo, functionRepo, vehicleRepo
        );
    }

    @Test
    void canSaveTestPlan() {
        //given
        ExtractFromLog extract = new ExtractFromLog();
        URL url = getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");
        assert url != null;
        TestPlan testPlan = extract.extractLog(
                url.getPath(),
                "IBN1",
                "Vehicle 1"
        );
        //when
        testPlanServiceImplUnderTest.saveTestPlan(testPlan);

        //then
        ArgumentCaptor<TestPlan> testPlanArgumentCaptor = ArgumentCaptor.forClass(TestPlan.class);
        verify(testPlanRepo).save(testPlanArgumentCaptor.capture());

        TestPlan capturedTestPlan = testPlanArgumentCaptor.getValue();
        assertThat(capturedTestPlan).isEqualTo(testPlan);
    }

    @Test
    void canGetAllTestPlans() {
        //when
        testPlanServiceImplUnderTest.getTestPlans();
        //then
        verify(testPlanRepo).findAll();
    }

    @Test
    void shouldThrowExceptionWhenTestPlanDoesNotExistWithGivenEcuId() {
        //given
        Long ecuId = 50L;
        given(ecuRepo.existsById(ecuId)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> testPlanServiceImplUnderTest.getTestPlanByEcuId(ecuId))
                .isInstanceOf(EcuNotFoundException.class)
                .hasMessageContaining("Ecu with given ID does not exist.");

        verify(testPlanRepo, never()).findTestPlanByEcuId(any());
    }

    @Test
    void shouldGetTestPlanByEcuId() {
        //given
        Long ecuId = 50L;
        given(ecuRepo.existsById(ecuId)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> testPlanServiceImplUnderTest.getTestPlanByEcuId(ecuId))
                .doesNotThrowAnyException();

        verify(testPlanRepo).findTestPlanByEcuId(any());
    }

    @Test
    void shouldThrowExceptionWhenTestPlanDoesNotExistWithGivenFunctionId(){
        //given
        Long functionId = 25L;
        given(functionRepo.existsById(functionId)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> testPlanServiceImplUnderTest.getTestPlanByFunctionId(functionId))
                .isInstanceOf(FunctionNotFoundException.class)
                .hasMessageContaining("Function with given ID does not exist");

        verify(testPlanRepo, never()).findTestPlanByFunctionId(any());
    }

    @Test
    void shouldGetTestPlanByFunctionId() {
        //given
        Long functionId = 25L;
        given(functionRepo.existsById(functionId)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> testPlanServiceImplUnderTest.getTestPlanByFunctionId(functionId))
                .doesNotThrowAnyException();

        verify(testPlanRepo).findTestPlanByFunctionId(any());
    }

    @Test
    @Disabled
    void getDistinctTestLocationByVehicleTyp() {
    }

    @Test
    @Disabled
    void getTestPlanById() {
    }

    @Test
    @Disabled
    void getTestPlansBasedOnVehicleTypAndTestLocation() {
    }


}