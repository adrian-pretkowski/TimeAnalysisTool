package pl.adi.timeanalysistool.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.FunctionNotFoundException;
import pl.adi.timeanalysistool.exception.TestPlanNotFoundException;
import pl.adi.timeanalysistool.exception.VehicleNotFoundException;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.FunctionRepo;
import pl.adi.timeanalysistool.repo.TestPlanRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FunctionServiceImplTest {

    @Mock
    private TestPlanRepo testPlanRepo;
    @Mock
    private VehicleRepo vehicleRepo;
    @Mock
    private EcuRepo ecuRepo;
    @Mock
    private FunctionRepo functionRepo;

    private FunctionServiceImpl functionServiceUnderTest;

    @BeforeEach
    void setUp() {
        functionServiceUnderTest = new FunctionServiceImpl(
                functionRepo, ecuRepo, vehicleRepo, testPlanRepo
        );
    }

    @Test
    void shouldGetAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> functionServiceUnderTest.getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                        vehicleTyp, ecuName, functionName
                )
        )
                .doesNotThrowAnyException();

        verify(functionRepo).findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                any(), any(), any()
        );
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionDuringGettingAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(false);
//        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
//        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                                vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("Given Vehicle Typ does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                        any(), any(), any()
                );
    }

    @Test
    void shouldThrowEcuNotFoundExceptionDuringGettingAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(false);
//        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                                vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(EcuNotFoundException.class)
                .hasMessageContaining("Given Ecu Name does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                        any(), any(), any()
                );
    }

    @Test
    void shouldThrowFunctionNotFoundExceptionDuringGettingAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
        given(functionRepo.existsByFunctionName(functionName)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                                vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(FunctionNotFoundException.class)
                .hasMessageContaining("Given Function Name does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsBasedOnVehicleTypAndEcuNameAndFunctionName(
                        any(), any(), any()
                );
    }

    @Test
    void shouldGetAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";

        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> functionServiceUnderTest
                        .getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
                                vehicleTyp, ecuName
                        )
        )
                .doesNotThrowAnyException();

        verify(functionRepo).findAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
                any(), any()
        );
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionDuringGettingAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";

        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(false);
//        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
                                vehicleTyp, ecuName
                        )
        )
                .isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("Given Vehicle Typ does not exist.");

        verify(functionRepo, never())
                .findAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
                        any(), any()
                );
    }

    @Test
    void shouldThrowEcuNotFoundExceptionDuringGettingAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName() {
        //given
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";

        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
                                vehicleTyp, ecuName
                        )
        )
                .isInstanceOf(EcuNotFoundException.class)
                .hasMessageContaining("Given Ecu Name does not exist.");

        verify(functionRepo, never())
                .findAllDistinctFunctionNamesBasedOnVehicleTypAndEcuName(
                        any(), any()
                );
    }

    @Test
    void shouldGetAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName() {
        //given
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> functionServiceUnderTest
                        .getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                                testLocation, vehicleTyp, ecuName, functionName
                        )
        )
                .doesNotThrowAnyException();

        verify(functionRepo)
                .findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                        any(), any(), any(), any()
                );
    }

    @Test
    void shouldThrowTestPlanNotFoundExceptionDuringGettingAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName() {
        //given
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(false);
//        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
//        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
//        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                                testLocation, vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(TestPlanNotFoundException.class)
                .hasMessageContaining("Given Test Location does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                        any(), any(), any(), any()
                );
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionDuringGettingAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName() {
        //given
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(false);
//        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
//        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                                testLocation, vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("Given Vehicle Typ does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                        any(), any(), any(), any()
                );
    }

    @Test
    void shouldThrowEcuNotFoundExceptionDuringGettingAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName() {
        //given
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(false);
//        given(functionRepo.existsByFunctionName(functionName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                                testLocation, vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(EcuNotFoundException.class)
                .hasMessageContaining("Given Ecu Name does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                        any(), any(), any(), any()
                );
    }

    @Test
    void shouldThrowFunctionNotFoundExceptionDuringGettingAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName() {
        //given
        String testLocation = "IBN1";
        String vehicleTyp = "Vehicle 1";
        String ecuName = "BCM_1";
        String functionName = "DTCClear";
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);
        given(functionRepo.existsByFunctionName(functionName)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> functionServiceUnderTest
                        .getAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                                testLocation, vehicleTyp, ecuName, functionName
                        )
        )
                .isInstanceOf(FunctionNotFoundException.class)
                .hasMessageContaining("Given Function Name does not exist.");

        verify(functionRepo, never())
                .findAllFunctionsDurationsBasedOnVehicleTypAndTestLocationAndEcuNameAndFunctionName(
                        any(), any(), any(), any()
                );
    }
}