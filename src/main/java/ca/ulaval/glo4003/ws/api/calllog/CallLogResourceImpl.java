package ca.ulaval.glo4003.ws.api.calllog;

import ca.ulaval.glo4003.ws.api.calllog.dto.CallLogDto;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogService;

import java.util.List;

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
