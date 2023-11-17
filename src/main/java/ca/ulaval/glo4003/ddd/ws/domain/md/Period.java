package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Period {

  private LocalDateTime endTime;
  private LocalDateTime startTime;

  public Period(LocalDateTime startTime, LocalDateTime endTime) {
    if (endTime.isBefore(startTime)) {
      throw new InvalidPeriodException();
    }
    this.endTime = endTime;
    this.startTime = startTime;
  }

  public boolean isForDate(LocalDate wantedDate) {

    return startTime.toLocalDate().isEqual(wantedDate);
  }

  public Duration calculateDuration() {

    return Duration.between(startTime, endTime);
  }
}
