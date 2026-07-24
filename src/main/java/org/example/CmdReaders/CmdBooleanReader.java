package org.example.CmdReaders;

import org.example.CmdReaders.Responses.BooleanResponse;
import org.example.CmdReaders.Responses.StringResponse;

public class CmdBooleanReader extends CmdStringReader {
    public static synchronized BooleanResponse getBooleanData() {
        StringResponse stringResponse = getStringData();
        BooleanResponse booleanResponse = new BooleanResponse(stringResponse);
        if (booleanResponse.state != StringResponse.States.OK)
            return booleanResponse;
        try {
            booleanResponse.booleanData = Boolean.parseBoolean(booleanResponse.stringData);
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to boolean, reason: " + e.getMessage());
            booleanResponse.errorMessage = e.getMessage();
            booleanResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return booleanResponse;
    }
}
