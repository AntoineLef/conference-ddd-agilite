package ca.nexapp.conf.ddd.ws.domain.md.doctor;

public class DoctorDailyRateSelector {

  private double localDailyRate;
  private double awayDailyRate;
  private String localHospital;

  public DoctorDailyRateSelector(double localDailyRate, double awayDailyRate, String localHospital) {
    this.localDailyRate = localDailyRate;
    this.awayDailyRate = awayDailyRate;
    this.localHospital = localHospital;

  }

  public double getRateForHospital(String hospitalName) {
    double rate = localDailyRate;
    if (!localHospital.equals(hospitalName)) {
      rate = awayDailyRate;
    }
    return rate;
  }

}
