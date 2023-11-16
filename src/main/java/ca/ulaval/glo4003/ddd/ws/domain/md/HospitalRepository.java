package ca.ulaval.glo4003.ddd.ws.domain.md;

public interface HospitalRepository {
  void save(Hospital hospital);

  Hospital getByName(String name);
}
