package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

public class ProcedureTest {

  private static final String HOSPITAL_NAME = "HOSPITAL_NAME";
  private static final String DOCTOR_ID = "DOCTOR_ID";
  private static final double EXPECTED_FULL_WORKDAY_PAY_RATE = 2000.0;
  private static final double HOURS_FOR_FULL_WORKDAY = Procedure.HOURS_FOR_FULL_WORKDAY;

  // Regle Affaire Exemple (start = end --> 0)
  @Test
  public void givenAPeriodWithoutDuration_whenCalculatingProcedurePay_thenPayIsZero() {
    // Given
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime;
    Procedure procedure = new Procedure(DOCTOR_ID, HOSPITAL_NAME, startTime, endTime);

    // When
    double amountPaid = procedure.calculateProcedurePay();

    // Then
    Truth.assertThat(amountPaid).isEqualTo(0.0);
  }

  // 2000$ ->8h
  @Test
  public void givenAProcedureAFullWorkday_whenCalculatingPay_thenFullDailyAmountPaid() {

    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = addHoursToStartTime(HOURS_FOR_FULL_WORKDAY, startTime);
    Procedure procedure = new Procedure(DOCTOR_ID, HOSPITAL_NAME, startTime, endTime);

    // When
    double amountPaid = procedure.calculateProcedurePay();

    // Then
    Truth.assertThat(amountPaid).isEqualTo(EXPECTED_FULL_WORKDAY_PAY_RATE);
  }

  // Half day = Half pay
  @Test
  public void givenAProcedureAHalfWorkday_whenCalculatingPay_thenHalfDailyAmountPaid() {
    // Given
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = addHoursToStartTime(HOURS_FOR_FULL_WORKDAY / 2, startTime);
    Procedure procedure = new Procedure(DOCTOR_ID, HOSPITAL_NAME, startTime, endTime);

    // When
    double amountPaid = procedure.calculateProcedurePay();

    // Then
    Truth.assertThat(amountPaid).isEqualTo(EXPECTED_FULL_WORKDAY_PAY_RATE / 2);
  }

  private LocalDateTime addHoursToStartTime(double hoursForFullWorkDay, LocalDateTime startTime) {
    int minutesInAnHour = 60;
    return startTime.plusMinutes((long) hoursForFullWorkDay * minutesInAnHour);
  }
}
