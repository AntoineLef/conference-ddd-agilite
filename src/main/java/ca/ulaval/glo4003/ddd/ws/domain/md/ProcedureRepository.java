package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.util.List;

public interface ProcedureRepository {

  void add(Procedure procedure);

  List<Procedure> findAll();

}
