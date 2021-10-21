package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.LocalDateTime;

public class Procedure {

  private String hospitalName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String doctorId;

  public Procedure(String doctorId, String hospitalName, LocalDateTime startTime, LocalDateTime endTime) {
    this.doctorId = doctorId;
    this.hospitalName = hospitalName;
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

}
