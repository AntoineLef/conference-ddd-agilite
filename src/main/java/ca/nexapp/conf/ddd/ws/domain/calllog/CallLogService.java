package ca.nexapp.conf.ddd.ws.domain.calllog;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ca.nexapp.conf.ddd.ws.api.calllog.dto.CallLogDto;

public class CallLogService {
  private static final Logger logger = Logger.getLogger(CallLogService.class.getName());

  private CallLogRepository callLogRepository;
  private CallLogAssembler callLogAssembler;

  public CallLogService(CallLogRepository callLogRepository, CallLogAssembler callLogAssembler) {
    this.callLogRepository = callLogRepository;
    this.callLogAssembler = callLogAssembler;
  }

  public List<CallLogDto> findAllCallLogs() {
    logger.info("Get all call logs");
    List<CallLog> callLogs = callLogRepository.findAll();
    return callLogs.stream().map(callLogAssembler::create).collect(Collectors.toList());
  }

  public void deleteCallLog(String id) {
    logger.info(String.format("Delete call log with id %s", id));
    callLogRepository.remove(id);
  }
}
