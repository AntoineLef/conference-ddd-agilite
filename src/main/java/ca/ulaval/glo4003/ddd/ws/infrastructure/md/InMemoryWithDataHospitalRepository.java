package ca.ulaval.glo4003.ddd.ws.infrastructure.md;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ca.ulaval.glo4003.ddd.ws.domain.md.Hospital;
import ca.ulaval.glo4003.ddd.ws.domain.md.HospitalRepository;

public class InMemoryWithDataHospitalRepository implements HospitalRepository {

  private Map<UUID, Hospital> hospitals;

  public InMemoryWithDataHospitalRepository() {
    this.hospitals = new HashMap();
  }

  @Override
  public void save(Hospital hospital) {
    hospitals.put(hospital.hospitalId(), hospital);
  }

  @Override
  public Hospital getByName(String name) {

    return hospitals.values()
                    .stream()
                    .filter(h -> h.hospitalName().equals(name))
                    .findFirst()
                    .orElseThrow();
  }

  static InMemoryWithDataHospitalRepository init() {
    InMemoryWithDataHospitalRepository repo =
                                            new InMemoryWithDataHospitalRepository();
    repo.save(new Hospital(UUID.randomUUID(), "chudequebec", 1.15));
    return repo;
  }
}
