package ca.ulaval.glo4003.ddd.ws.domain.md;

public interface HospitalPrimeRateFetcher {
  double findExternalHospitalRate(String hospitalName);
}
