package ca.nexapp.conf.ddd.ws.domain.md.doctor.procedure;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ca.nexapp.conf.ddd.ws.domain.md.BilledAmount;
import ca.nexapp.conf.ddd.ws.domain.md.doctor.DoctorDailyRateSelector;

public class Procedure {

  private String id;
  private String hospitalName;
  private Period period;

  public Procedure(String id, String hospitalName, LocalDateTime startTime, LocalDateTime endTime) {
    this.id = id;
    this.hospitalName = hospitalName;
    this.period = new Period(startTime, endTime);
  }

  public boolean isFrom(LocalDate wantedDate) {
    return period.isSameDate(wantedDate);
  }

  public BilledAmount addToTotalBilled(BilledAmount totalBilled,
                                       double hoursOfWorkingDay,
                                       DoctorDailyRateSelector rateSelector)
  {
    double hourWorkedRatio = duration().toHours() / hoursOfWorkingDay;
    double rate = rateSelector.getRateForHospital(hospitalName);
    return totalBilled.addBillable(rate, hourWorkedRatio);
  }

  private Duration duration() {
    return period.calculateDuration();
  }
}
