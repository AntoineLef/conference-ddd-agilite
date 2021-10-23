package ca.nexapp.conf.ddd.ws.domain.md;

import java.util.List;

import ca.nexapp.conf.ddd.ws.domain.md.doctor.procedure.Procedure;

public interface ProcedureRepository {

  void add(Procedure procedure);

  List<Procedure> findAll();

}
