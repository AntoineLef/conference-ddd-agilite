package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.LocalDateTime;

// POJO - Data Container
// Livre ouvert
public class Procedure {

  private String hospitalName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String doctorId;
  private Period period;

  public Procedure(String doctorId, String hospitalName, LocalDateTime startTime, LocalDateTime endTime) {
    this.doctorId = doctorId;
    this.hospitalName = hospitalName;
    this.period = new Period(startTime, endTime);
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public String getHospitalName() {
    return hospitalName;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public Period getPeriod() {
    return period;
  }

}
