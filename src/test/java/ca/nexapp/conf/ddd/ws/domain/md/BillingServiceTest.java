package ca.nexapp.conf.ddd.ws.domain.md;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.truth.Truth;

public class BillingServiceTest {

  private static final int QUARTER_OF_A_DAY = 2;
  private static final String DOCTOR_ID = "1";
  private static final String LOCAL_HOSPITAL = "CHUDEQUEBEC";
  private static final String LICENSE_NUMBER = "A_PERMIT_NUMBER";
  private static final LocalDate TODAY = LocalDate.now();
  private static final Double DAILY_RATE = 600.0;
  private static final String PROCEDURE_ID = "21";

  @Mock
  private ProcedureRepository procedureRepo;

  @Mock
  private DoctorRepository doctorRepository;

  private BillingService billingService;

  @BeforeEach
  public void Setup() {
    MockitoAnnotations.initMocks(this);

    billingService = new BillingService(doctorRepository, procedureRepo);
  }

  @Test
  public void givenADoctor_whenAddingAProcedure_thenProcedureIsAddedToDoctorBilling() throws DoctorNotFoundException {
    // given
    Doctor doctor = forADoctor();
    ProcedureInfo procedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), 8);

    // when
    billingService.addNewProcedure(DOCTOR_ID, procedureInfo);

    // then
    verify(doctorRepository).update(doctor);
  }

  @Test
  public void givenADoctorWithOneFullDayLocalProcedure_whenCalculatingPay_thenProcedureIsAddedToDoctorBilling() {
    // given
    forADoctor();
    ProcedureInfo procedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), 8);
    when(procedureRepo.findAll()).thenReturn(Arrays.asList(new Procedure(PROCEDURE_ID,
                                                                         DOCTOR_ID,
                                                                         LOCAL_HOSPITAL,
                                                                         procedureInfo.startTime,
                                                                         procedureInfo.endTime)));

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  @Test
  public void givenADoctorWithTodayAndYesterDayLocalProcedure_whenCalculatingPay_thenOnlyTodayProcedureIsAddedToDoctorBilling() {
    // given
    forADoctor();
    ProcedureInfo todayProcedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), 8);
    ProcedureInfo yesterdayProcedureInfo = givenLocalHospitalProcedure(LocalDateTime.now().minusDays(1), 8);
    Procedure todayProcedure = new Procedure(PROCEDURE_ID,
                                             DOCTOR_ID,
                                             LOCAL_HOSPITAL,
                                             todayProcedureInfo.startTime,
                                             todayProcedureInfo.endTime);
    Procedure yesterdayProcedure = new Procedure(PROCEDURE_ID,
                                                 DOCTOR_ID,
                                                 LOCAL_HOSPITAL,
                                                 yesterdayProcedureInfo.startTime,
                                                 yesterdayProcedureInfo.endTime);
    when(procedureRepo.findAll()).thenReturn(Arrays.asList(todayProcedure, yesterdayProcedure));

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  @Test
  public void givenADoctorWithTwoYesterDayLocalProcedure_whenCalculatingPay_thenDoctorBillingIs0() {
    // given
    forADoctor();
    ProcedureInfo yesterdayProcedureInfo = givenLocalHospitalProcedure(LocalDateTime.now().minusDays(1), 8);

    Procedure firstYesterdayProcedure = new Procedure(PROCEDURE_ID,
                                                      DOCTOR_ID,
                                                      LOCAL_HOSPITAL,
                                                      yesterdayProcedureInfo.startTime,
                                                      yesterdayProcedureInfo.endTime);
    Procedure secondYesterdayProcedure = new Procedure(PROCEDURE_ID,
                                                       DOCTOR_ID,
                                                       LOCAL_HOSPITAL,
                                                       yesterdayProcedureInfo.startTime,
                                                       yesterdayProcedureInfo.endTime);
    when(procedureRepo.findAll()).thenReturn(Arrays.asList(firstYesterdayProcedure, secondYesterdayProcedure));

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(0);
  }

  @Test
  public void givenADoctorWithTwoQuarterDayLocalProcedure_whenCalculatingPay_thenDoctorBillingIsAtHalfDailyRate() {
    // given
    forADoctor();
    with2QuaterProcedures();

    // when
    double amount = billingService.dailyTotalOf(DOCTOR_ID, TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(2 * (QUARTER_OF_A_DAY / 8.0) * DAILY_RATE);
  }

  private void with2QuaterProcedures() {
    ProcedureInfo todayProcedureInfo = givenLocalHospitalProcedure(LocalDateTime.now(), QUARTER_OF_A_DAY);

    Procedure firstProcedure = new Procedure(PROCEDURE_ID,
                                             DOCTOR_ID,
                                             LOCAL_HOSPITAL,
                                             todayProcedureInfo.startTime,
                                             todayProcedureInfo.endTime);
    Procedure secondProcedure = new Procedure(PROCEDURE_ID,
                                              DOCTOR_ID,
                                              LOCAL_HOSPITAL,
                                              todayProcedureInfo.startTime,
                                              todayProcedureInfo.endTime);
    when(procedureRepo.findAll()).thenReturn(Arrays.asList(firstProcedure, secondProcedure));
  }

  private Doctor forADoctor() {
    Doctor doctor = new Doctor(DOCTOR_ID, LOCAL_HOSPITAL, LICENSE_NUMBER);
    when(doctorRepository.findById(DOCTOR_ID)).thenReturn(doctor);
    return doctor;
  }

  private ProcedureInfo givenLocalHospitalProcedure(LocalDateTime startTime, int hoursWorked) {
    LocalDateTime endTime = startTime.plusHours(hoursWorked);
    return new ProcedureInfo(LOCAL_HOSPITAL, startTime, endTime);
  }
}
