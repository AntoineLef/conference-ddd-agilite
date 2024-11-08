package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.LocalDate;
import java.util.List;

public class Procedures {

  private List<Procedure> procedures;

  public Procedures(List<Procedure> procedures) {
    this.procedures = procedures;
  }

  public List<Procedure> fetchProceduresOnDate(LocalDate wantedDate) {
    return procedures.stream().filter(procedure -> procedure.isOnDate(wantedDate)).toList();
  }

  public void add(Procedure procedure) {
    this.procedures.add(procedure);
  }
}
