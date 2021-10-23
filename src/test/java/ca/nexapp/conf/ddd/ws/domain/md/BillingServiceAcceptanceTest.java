package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

import ca.nexapp.conf.ddd.ws.infrastructure.md.InMemoryDoctorRepository;
import ca.nexapp.conf.ddd.ws.infrastructure.md.InMemoryProcedureRepository;

public class BillingServiceAcceptanceTest {

  private static final String DOCTOR_ID = "1";
  private static final String LOCAL_HOSPITAL = "CHUDEQUEBEC";
  private static final String LICENSE_NUMBER = "A_PERMIT_NUMBER";
  private static final LocalDate TODAY = LocalDate.now();
  private static final Double DAILY_RATE = 600.0;

  private ProcedureRepository procedureRepo = new InMemoryProcedureRepository();
  private DoctorRepository doctorRepository = new InMemoryDoctorRepository();

  private BillingService billingService;

  @BeforeEach
  public void Setup() {
    billingService = new BillingService(doctorRepository, procedureRepo);
  }

  @Test
  public void givenADoctorWithOneFullDayLocalProcedure_whenCalculatingPay_thenProcedureIsAddedToDoctorBilling()
    throws DoctorNotFoundException
  {
    // given
    forADoctor();
    ProcedureInfo procedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), 8);
    billingService.addNewProcedure(DOCTOR_ID, procedureInfo);

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  @Test
  public void givenADoctorWithTodayAndYesterDayLocalProcedure_whenCalculatingPay_thenOnlyTodayProcedureIsAddedToDoctorBilling()
    throws DoctorNotFoundException
  {
    // given
    forADoctor();
    ProcedureInfo todayProcedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), 8);
    ProcedureInfo yesterdayProcedureInfo = givenLocalHospitalProcedure(LocalDateTime.now().minusDays(1), 8);

    billingService.addNewProcedure(DOCTOR_ID, yesterdayProcedureInfo);
    billingService.addNewProcedure(DOCTOR_ID, todayProcedureInfo);

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  private void forADoctor() {
    Doctor doctor = new Doctor(DOCTOR_ID, LOCAL_HOSPITAL, LICENSE_NUMBER);
    doctorRepository.save(doctor);
  }

  private ProcedureInfo givenLocalHospitalProcedure(LocalDateTime startTime, int hoursWorked) {
    LocalDateTime endTime = startTime.plusHours(hoursWorked);
    return new ProcedureInfo(LOCAL_HOSPITAL, startTime, endTime);
  }
}
