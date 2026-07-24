package org.example.CmdReaders;

import org.example.CmdReaders.Responses.IntResponse;
import org.example.CmdReaders.Responses.StringResponse;

public class CmdIntReader extends CmdStringReader{
    public static synchronized IntResponse getIntData() {
        StringResponse stringResponse = getStringData();
        IntResponse longResponse = new IntResponse(stringResponse);
        if (longResponse.state != StringResponse.States.OK)
            return longResponse;
        try {
            longResponse.intData = Integer.parseInt(longResponse.stringData.replace(" ",""));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to int. " + e.getMessage());
            longResponse.errorMessage = e.getMessage();
            longResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return longResponse;
    }
}
