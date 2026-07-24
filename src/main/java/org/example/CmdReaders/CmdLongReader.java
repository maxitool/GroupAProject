package org.example.CmdReaders;

import org.example.CmdReaders.Responses.LongResponse;
import org.example.CmdReaders.Responses.StringResponse;

public class CmdLongReader extends CmdStringReader{
    public static synchronized LongResponse getLongData() {
        StringResponse stringResponse = getStringData();
        LongResponse longResponse = new LongResponse(stringResponse);
        if (longResponse.state != StringResponse.States.OK)
            return longResponse;
        try {
            longResponse.longData = Long.parseLong(longResponse.stringData);
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to long, reason: " + e.getMessage());
            longResponse.errorMessage = e.getMessage();
            longResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return longResponse;
    }
}
