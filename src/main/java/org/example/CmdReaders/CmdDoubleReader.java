package org.example.CmdReaders;

import org.example.CmdReaders.Responses.DoubleResponse;
import org.example.CmdReaders.Responses.StringResponse;

public class CmdDoubleReader extends CmdStringReader {
    public static synchronized DoubleResponse getDoubleData() {
        StringResponse stringResponse = getStringData();
        DoubleResponse doubleResponse = new DoubleResponse(stringResponse);
        if (doubleResponse.state != StringResponse.States.OK)
            return doubleResponse;
        try {
            doubleResponse.doubleData = Double.parseDouble(doubleResponse.stringData);
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to double, reason: " + e.getMessage());
            doubleResponse.errorMessage = e.getMessage();
            doubleResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return doubleResponse;
    }
}
