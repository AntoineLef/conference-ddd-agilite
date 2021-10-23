package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Doctor {

  private static final double DAILY_RATE = 600.0;

  private static final double DAILY_WORKED_HOURS = 8.0;

  private String id;
  private String permitNumber;
  private String name;
  private String localHospital;

  private List<Procedure> procedures = new ArrayList<>();

  public Doctor(String doctorId, String localHospital, String permitNumber) {
    this.id = doctorId;
    this.localHospital = localHospital;
    this.permitNumber = permitNumber;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocalHospital() {
    return localHospital;
  }

  public List<Procedure> getProcedures() {
    return procedures;
  }

  public void addProcedure(Procedure procedure) {
    procedures.add(procedure);
  }

  public double calculateDailyBillable(LocalDate wantedDate) {
    BilledAmount totalBilled = new BilledAmount();

    for (Procedure procedure : procedures) {

      if (procedure.from(wantedDate)) {
        Duration procedureDuration = procedure.duration();
        double hourWorkedRatio = procedureDuration.toHours() / DAILY_WORKED_HOURS;
        totalBilled = totalBilled.addBillable(DAILY_RATE, hourWorkedRatio);
      }
    }

    return totalBilled.value();
  }
}
