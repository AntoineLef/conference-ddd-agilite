package ca.nexapp.conf.ddd.ws.domain.md.doctor.procedure;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Period {

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public Period(LocalDateTime startTime, LocalDateTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Duration calculateDuration() {
    return Duration.between(startTime, endTime);
  }

  public boolean isSameDate(LocalDate wantedDate) {
    return startTime.toLocalDate().isEqual(wantedDate);
  }
}
