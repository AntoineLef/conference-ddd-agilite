package ca.nexapp.conf.ddd.ws.api.calllog;

import java.util.List;

import ca.nexapp.conf.ddd.ws.api.calllog.dto.CallLogDto;
import ca.nexapp.conf.ddd.ws.domain.calllog.CallLogService;

public class CallLogResourceImpl implements CallLogResource {

  private CallLogService callLogService;

  public CallLogResourceImpl(CallLogService callLogService) {
    this.callLogService = callLogService;
  }

  @Override
  public List<CallLogDto> getCallLogs() {
    return callLogService.findAllCallLogs();
  }

  @Override
  public void deleteCallLog(String id) {
    callLogService.deleteCallLog(id);
  }
}
