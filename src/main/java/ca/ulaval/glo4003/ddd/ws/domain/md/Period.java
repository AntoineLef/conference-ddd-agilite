package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.Duration;
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

}
