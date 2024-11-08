package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.LocalDate;
import java.util.List;

public class Doctor {
  private String id;
  private String permitNumber;
  private String name;
  private String localHospital;
  private Procedures procedures;

  public Doctor(String doctorId,
                String localHospital,
                String permitNumber,
                List<Procedure> procedures)
  {
    this.id = doctorId;
    this.localHospital = localHospital;
    this.permitNumber = permitNumber;
    this.procedures = new Procedures(procedures);
  }

  public double calculateDailyWage(LocalDate wantedDate, HospitalPrimeRateFetcher primeRateFetcher) {
    double total = 0.0;
    
    for (Procedure procedure : procedures.fetchProceduresOnDate(wantedDate)) {
        total += procedure.calculateProcedurePay(primeRateFetcher, localHospital);
    }
    return total;
  }

  public void addProcedure(Procedure procedure) {
    this.procedures.add(procedure); 
  }
}
