package ca.nexapp.conf.ddd.ws.domain.md;

import java.util.UUID;

public class ProcedureIdGenerator {

  public static String generateId() {
    return UUID.randomUUID().toString();
  }

}
