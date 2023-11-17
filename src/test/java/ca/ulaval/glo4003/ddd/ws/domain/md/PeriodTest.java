package ca.ulaval.glo4003.ddd.ws.domain.md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PeriodTest {
  private static final LocalDateTime ANY_DATE_TIME = LocalDateTime.now();

  @Test
  public void givenAPeriodForToday_whenValidatingIfForToday_thenItIs() {
    // given
    Period period = new Period(LocalDateTime.now(), ANY_DATE_TIME);

    // when
    boolean isForToday = period.isForDate(LocalDate.now());

    // then
    assertTrue(isForToday);
  }

  @Test
  public void givenAPeriodForAGivenDate_whenValidatingIfForAnyOtherDay_thenItIsNot() {
    // given
    Period period = new Period(LocalDateTime.now().minusDays(1), ANY_DATE_TIME);

    // when
    boolean isForToday = period.isForDate(LocalDate.now());

    // then
    assertFalse(isForToday);
  }

  @Test
  public void givenAPeriodWithSameStartAndEndTime_whenCalculatingDuration_thenDurationIsZero() {
    // given
    Period period = new Period(ANY_DATE_TIME, ANY_DATE_TIME);
    Duration expectedDuration = Duration.ZERO;

    // when
    Duration calculatedDuration = period.calculateDuration();

    // then
    assertEquals(expectedDuration, calculatedDuration);
  }

  @Test
  public void givenAPeriodWith8HoursBetweenEndAndStart_whenCalculatingDuration_thenDurationIsEight() {
    // given
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusHours(8);
    Period period = new Period(startTime, endTime);
    Duration expectedDuration = Duration.between(startTime, endTime);

    // when
    Duration calculatedDuration = period.calculateDuration();

    // then
    assertEquals(expectedDuration, calculatedDuration);
  }

  @Test
  public void givenAPeriodWith54HoursBetweenEndAndStart_whenCalculatingDuration_theDurationIs54() {
    // given
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusHours(54);
    Period period = new Period(startTime, endTime);

    // when
    Duration calculatedDuration = period.calculateDuration();

    // then
    assertEquals(54L, calculatedDuration.toHours());
  }

  @Test
  public void givenEndTimeBeforeStartTime_whenCreatingPeriod_thenExceptionThrown() {
    // given
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.minusHours(1);

    assertThrows(InvalidPeriodException.class,
                 () -> new Period(startTime, endTime));
  }
}
