package ca.nexapp.conf.ddd.ws.infrastructure.md;

import java.util.ArrayList;
import java.util.List;

import ca.nexapp.conf.ddd.ws.domain.md.ProcedureRepository;
import ca.nexapp.conf.ddd.ws.domain.md.doctor.procedure.Procedure;

public class InMemoryProcedureRepository implements ProcedureRepository {

  private List<Procedure> procedures = new ArrayList<>();

  @Override
  public void add(Procedure procedure) {

    procedures.add(procedure);
  }

  @Override
  public List<Procedure> findAll() {
    return procedures;
  }

}
