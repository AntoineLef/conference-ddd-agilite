package ca.nexapp.conf.ddd.ws.domain.md;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.truth.Truth;

public class BillingServiceTest {

  private static final String DOCTOR_ID = "1";
  private static final String LICENSE_NUMBER = "A_PERMIT_NUMBER";
  private static final String LOCAL_HOSPITAL = "CHUDEQUEBEC";
  private static final LocalDate TODAY = LocalDate.now();
  private static final Double DAILY_RATE = 600.0;
  private static final String PROCEDURE_ID = "21";

  @Mock
  private DoctorRepository doctorRepository;

  @Mock
  private Doctor doctor;

  private BillingService billingService;

  @BeforeEach
  public void Setup() {
    MockitoAnnotations.initMocks(this);

    billingService = new BillingService(doctorRepository);
    forADoctor();
  }

  @Test
  public void givenADoctor_whenAddingAProcedure_thenProcedureIsAddedToDoctorBilling() throws DoctorNotFoundException {
    // given
    ProcedureInfo procedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), 8);

    // when
    billingService.addNewProcedure(DOCTOR_ID, procedureInfo);

    // then
    verify(doctorRepository).update(doctor);
  }

  @Test
  public void givenADoctor_whenCalculatingDailyBillable_thenDoctorAmountIsReturned() {
    // given
    when(doctor.calculateDailyBillable(TODAY)).thenReturn(DAILY_RATE);

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  private void forADoctor() {
    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(doctor);
  }

  private ProcedureInfo givenLocalHospitalProcedure(LocalDateTime startTime, int hoursWorked) {
    LocalDateTime endTime = startTime.plusHours(hoursWorked);
    return new ProcedureInfo(LOCAL_HOSPITAL, startTime, endTime);
  }
}
