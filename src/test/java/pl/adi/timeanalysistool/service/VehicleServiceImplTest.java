package pl.adi.timeanalysistool.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.FunctionNotFoundException;
import pl.adi.timeanalysistool.repo.EcuRepo;
import pl.adi.timeanalysistool.repo.FunctionRepo;
import pl.adi.timeanalysistool.repo.VehicleRepo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private EcuRepo ecuRepo;
    @Mock
    private FunctionRepo functionRepo;
    @Mock
    private VehicleRepo vehicleRepo;

    private VehicleServiceImpl vehicleServiceUnderTest;

    @BeforeEach
    void setUp() {
        vehicleServiceUnderTest = new VehicleServiceImpl(
                vehicleRepo, ecuRepo, functionRepo
        );
    }

    @Test
    void shouldThrowEcuNotFoundExceptionDuringGettingVehicleByEcuId() {
        //given
        Long ecuId = 1L;
        given(ecuRepo.existsById(ecuId)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> vehicleServiceUnderTest.getVehicleByEcuId(ecuId)
        )
                .isInstanceOf(EcuNotFoundException.class)
                .hasMessageContaining("Ecu with given ID does not exist.");

        verify(vehicleRepo, never()).findVehicleByEcuId(any());
    }

    @Test
    void shouldGetVehicleByEcuId() {
        //given
        Long ecuId = 1L;
        given(ecuRepo.existsById(ecuId)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> vehicleServiceUnderTest.getVehicleByEcuId(ecuId)
        ).doesNotThrowAnyException();

        verify(vehicleRepo).findVehicleByEcuId(any());
    }

    @Test
    void shouldThrowFunctionNotFoundExceptionDuringGettingVehicleByFunctionId() {
        //given
        Long functionId = 1L;
        given(functionRepo.existsById(functionId)).willReturn(false);

        //when
        //then
        assertThatThrownBy(
                () -> vehicleServiceUnderTest.getVehicleByFunctionId(functionId)
        )
                .isInstanceOf(FunctionNotFoundException.class)
                .hasMessageContaining("Function with given ID does not exist.");

        verify(vehicleRepo, never()).findVehicleByFunctionId(any());
    }


    @Test
    void shouldGetVehicleByFunctionId() {
        //given
        Long functionId = 1L;
        given(functionRepo.existsById(functionId)).willReturn(true);

        //when
        //then
        assertThatCode(
                () -> vehicleServiceUnderTest.getVehicleByFunctionId(functionId)
        ).doesNotThrowAnyException();

        verify(vehicleRepo).findVehicleByFunctionId(any());
    }

    @Test
    void shouldGetDistinctVehicles() {
        assertThatCode(
                () -> vehicleServiceUnderTest.getDistinctVehicles()
        ).doesNotThrowAnyException();

        verify(vehicleRepo).findDistinctVehicles();
    }
}