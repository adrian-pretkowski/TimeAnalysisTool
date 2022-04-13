package pl.adi.timeanalysistool.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.TestPlanNotFoundException;
import pl.adi.timeanalysistool.exception.VehicleNotFoundException;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.TestPlanRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EcuServiceImplTest {

    @Mock
    private TestPlanRepo testPlanRepo;
    @Mock
    private VehicleRepo vehicleRepo;
    @Mock
    private EcuRepo ecuRepo;

    private EcuServiceImpl ecuServiceUnderTest;

    @BeforeEach
    void setUp() {
        ecuServiceUnderTest = new EcuServiceImpl(
                ecuRepo, vehicleRepo, testPlanRepo);
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionDuringGettingDistinctEcuNames() {
        //given
        String vehicleTyp = "Vehicle 1";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> ecuServiceUnderTest.getAllDistinctEcuNamesBasedOnVehicleTyp(vehicleTyp)
        )
                .isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("Given Vehicle Typ does not exist.");

        verify(ecuRepo, never()).findAllDistinctEcuNamesBasedOnVehicleTyp(any());
    }

    @Test
    void shouldGetAllDistinctEcuNamesBasedOnVehicleTyp() {
        //given
        String vehicleTyp = "Vehicle 1";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> ecuServiceUnderTest.getAllDistinctEcuNamesBasedOnVehicleTyp(vehicleTyp)
        )
                .doesNotThrowAnyException();

        verify(ecuRepo).findAllDistinctEcuNamesBasedOnVehicleTyp(any());
    }

    @Test
    void shouldGetAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> ecuServiceUnderTest.getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                        testLocation, vehicleTyp
                )
        ).doesNotThrowAnyException();

        verify(ecuRepo).findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                any(), any()
        );
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionDuringGettingDistinctEcuNamesBasedOnVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(false);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> ecuServiceUnderTest.getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                        testLocation, vehicleTyp)
        )
                .isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("Given Vehicle Typ does not exist.");

        verify(ecuRepo, never()).findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                any(), any()
        );
    }

    @Test
    void shouldThrowTestPlanNotFoundExceptionDuringGettingDistinctEcuNamesBasedOnVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> ecuServiceUnderTest.getAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                        testLocation, vehicleTyp)
        )
                .isInstanceOf(TestPlanNotFoundException.class)
                .hasMessageContaining("Given Test Location does not exist.");

        verify(ecuRepo, never()).findAllDistinctEcuNamesBasedOnVehicleTypAndTestLocation(
                any(), any()
        );
    }

    @Test
    void shouldGetAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        String ecuName = "BCM_1";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> ecuServiceUnderTest
                        .getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                                ecuName, vehicleTyp, testLocation
                        )
        )
                .doesNotThrowAnyException();

        verify(ecuRepo).findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                any(), any(), any()
        );
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionDuringGettingAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        String ecuName = "BCM_1";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(false);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> ecuServiceUnderTest
                        .getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                                ecuName, vehicleTyp, testLocation
                        )
        )
                .isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("Given Vehicle Typ does not exist");

        verify(ecuRepo, never()).findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                any(), any(), any()
        );
    }

    @Test
    void shouldThrowTestPlanNotFoundExceptionDuringGettingAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        String ecuName = "BCM_1";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(false);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(true);

        //when
        //then
        assertThatThrownBy(
                () -> ecuServiceUnderTest
                        .getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                                ecuName, vehicleTyp, testLocation
                        )
        )
                .isInstanceOf(TestPlanNotFoundException.class)
                .hasMessageContaining("Given Test Location does not exist");

        verify(ecuRepo, never()).findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                any(), any(), any()
        );
    }

    @Test
    void shouldThrowEcuNotFoundExceptionDuringGettingAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation() {
        //given
        String vehicleTyp = "Vehicle 1";
        String testLocation = "IBN3";
        String ecuName = "BCM_1";
        given(vehicleRepo.existsByVehicleTyp(vehicleTyp)).willReturn(true);
        given(testPlanRepo.existsByTestLocation(testLocation)).willReturn(true);
        given(ecuRepo.existsByEcuName(ecuName)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> ecuServiceUnderTest
                        .getAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                                ecuName, vehicleTyp, testLocation
                        )
        )
                .isInstanceOf(EcuNotFoundException.class)
                .hasMessageContaining("Given Ecu Name does not exist!");

        verify(ecuRepo, never()).findAllEcusBasedOnEcuNameAndVehicleTypAndTestLocation(
                any(), any(), any()
        );
    }
}