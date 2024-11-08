package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

// POJO - Data Container
// Livre ouvert
public class Procedure {

  private static final double WEEK_DAYS_FULL_DAY_AMOUNT_PAID = 2000.0;
  public static final double HOURS_FOR_FULL_WORKDAY = 8.0;

  private String hospitalName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Period period;

  public Procedure(String hospitalName, LocalDateTime startTime, LocalDateTime endTime) {
    this.hospitalName = hospitalName;
    this.period = new Period(startTime, endTime);
  }

  public String getHospitalName() {
    return hospitalName;
  }

  public Period getPeriod() {
    return period;
  }

  public double calculateProcedurePay(HospitalPrimeRateFetcher primeRateFetcher, String localHospital) {
    double wage = WEEK_DAYS_FULL_DAY_AMOUNT_PAID;
    
    
    if(period.onWeekend()) {
       wage = 3000.0;
    }
    if(!localHospital.equals(hospitalName)) {
      double primeRate = primeRateFetcher.findExternalHospitalRate(hospitalName);
    }
    
    return wage * (period.calculateRatioOfHours(HOURS_FOR_FULL_WORKDAY));
  }

  public boolean isOnDate(LocalDate wantedDate) {
    return period.isOnDate(wantedDate);
  }

}
