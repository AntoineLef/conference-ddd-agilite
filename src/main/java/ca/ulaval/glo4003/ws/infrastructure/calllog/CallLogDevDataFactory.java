package ca.ulaval.glo4003.ws.infrastructure.calllog;

import ca.ulaval.glo4003.ws.domain.calllog.CallLog;
import com.google.common.collect.Lists;

import java.util.List;

public class CallLogDevDataFactory {

    public List<CallLog> createMockData() {
        List<CallLog> callLogs = Lists.newArrayList();
        CallLog callLog1 = new CallLog();
        callLog1.setId("1");
        callLog1.setTelephoneNumber("514-999-0000");
        callLog1.setDate("2016-07-31T16:45:00Z");
        callLog1.setDurationInSeconds(65);
        callLogs.add(callLog1);

        CallLog callLog2 = new CallLog();
        callLog2.setId("2");
        callLog2.setTelephoneNumber("418-682-3092");
        callLog2.setDate("2016-06-31T15:29:00Z");
        callLog2.setDurationInSeconds(99);
        callLogs.add(callLog2);

        CallLog callLog3 = new CallLog();
        callLog3.setId("3");
        callLog3.setTelephoneNumber("581-671-0992");
        callLog3.setDate("2016-07-30T08:32:33Z");
        callLog3.setDurationInSeconds(22);
        callLogs.add(callLog3);

        return callLogs;
    }
}
