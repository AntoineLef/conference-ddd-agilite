package ca.ulaval.glo4003.ws.domain.calllog;

import java.util.List;

public interface CallLogRepository {
  List<CallLog> findAll();

  void save(CallLog callLog);

  void remove(String id);
}
