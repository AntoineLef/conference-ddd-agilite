package ca.nexapp.conf.ddd.ws.domain.md;

public class BilledAmount {

  private Double total;

  private BilledAmount(Double total) {
    this.total = total;
  }

  public BilledAmount() {
    this.total = 0.0;
  }

  public BilledAmount addProcedure(double rate, double hourWorkedRatio) {
    return new BilledAmount(total + rate * hourWorkedRatio);
  }

  public double value() {
    return total;
  }

}
