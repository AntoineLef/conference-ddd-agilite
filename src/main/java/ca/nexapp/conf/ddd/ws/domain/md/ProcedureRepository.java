package ca.nexapp.conf.ddd.ws.domain.md;

import java.util.List;

public interface ProcedureRepository {

  void add(Procedure procedure);

  List<Procedure> findAll();

}
