package ca.nexapp.conf.ddd.ws.domain.md;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
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
}
