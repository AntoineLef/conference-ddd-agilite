package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

public class DoctorTest {

  private static final int QUARTER_OF_A_DAY = 2;
  private static final String DOCTOR_ID = "1";
  private static final String LICENSE_NUMBER = "2";
  private static final String LOCAL_HOSPITAL = null;
  private static final String PROCEDURE_ID = "12";
  private static final LocalDate TODAY = LocalDate.now();
  private static final Double DAILY_RATE = 600.0;

  private Doctor doctor;

  @BeforeEach
  public void setup() {
    doctor = new Doctor(DOCTOR_ID, LOCAL_HOSPITAL, LICENSE_NUMBER);
  }

  @Test
  public void givenADoctorWithOneFullDayLocalProcedure_whenCalculatingPay_thenProcedureIsAddedToDoctorBilling() {
    // given
    Procedure procedure = givenLocalHospitalProcedure(LocalDateTime.now(), 8);
    doctor.addProcedure(procedure);

    // when
    double amount = doctor.calculateDailyBillable(TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  private Procedure givenLocalHospitalProcedure(LocalDateTime startTime, int hoursWorked) {
    LocalDateTime endTime = startTime.plusHours(hoursWorked);
    Procedure procedure = new Procedure(PROCEDURE_ID, DOCTOR_ID, LOCAL_HOSPITAL, startTime, endTime);
    return procedure;
  }

  @Test
  public void givenADoctorWithTodayAndYesterDayLocalProcedure_whenCalculatingPay_thenOnlyTodayProcedureIsAddedToDoctorBilling() {
    // given
    Procedure todayProcedure = givenLocalHospitalProcedure(LocalDateTime.now(), 8);
    Procedure yesterdayProcedure = givenLocalHospitalProcedure(LocalDateTime.now().minusDays(1), 8);
    doctor.addProcedure(yesterdayProcedure);
    doctor.addProcedure(todayProcedure);

    // when
    double amount = doctor.calculateDailyBillable(TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(DAILY_RATE);
  }

  @Test
  public void givenADoctorWithTwoYesterDayLocalProcedure_whenCalculatingPay_thenDoctorBillingIs0() {
    // given
    Procedure yesterdayProcedure = givenLocalHospitalProcedure(LocalDateTime.now().minusDays(1), 8);
    doctor.addProcedure(yesterdayProcedure);
    doctor.addProcedure(yesterdayProcedure);

    // when
    double amount = doctor.calculateDailyBillable(TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(0);
  }

  @Test
  public void givenADoctorWithTwoQuarterDayLocalProcedure_whenCalculatingPay_thenDoctorBillingIsAtHalfDailyRate() {
    // given
    with2QuaterProcedures();

    // when
    double amount = doctor.calculateDailyBillable(TODAY);

    // then
    Truth.assertThat(amount).isEqualTo(2 * (QUARTER_OF_A_DAY / 8.0) * DAILY_RATE);
  }

  private void with2QuaterProcedures() {
    Procedure todayProcedure = givenLocalHospitalProcedure(LocalDateTime.now(), QUARTER_OF_A_DAY);
    doctor.addProcedure(todayProcedure);
    doctor.addProcedure(todayProcedure);
  }

}
