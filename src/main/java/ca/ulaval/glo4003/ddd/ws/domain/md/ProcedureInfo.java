package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.LocalDateTime;

public class ProcedureInfo {

  public LocalDateTime startTime;
  public LocalDateTime endTime;
  public String hospitalName;

  public ProcedureInfo(String hospitalName, LocalDateTime startTime, LocalDateTime endTime) {
    this.hospitalName = hospitalName;
    this.startTime = startTime;
    this.endTime = endTime;
  }

}
