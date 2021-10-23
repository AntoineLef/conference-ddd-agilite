package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Procedure {

  private String id;
  private String hospitalName;
  private Period period;

  public Procedure(String id, String hospitalName, LocalDateTime startTime, LocalDateTime endTime) {
    this.id = id;
    this.hospitalName = hospitalName;
    this.period = new Period(startTime, endTime);
  }

  public String getHospitalName() {
    return hospitalName;
  }

  public boolean from(LocalDate wantedDate) {
    return period.isSameDate(wantedDate);
  }

  public Duration duration() {
    return period.calculateDuration();
  }

}
