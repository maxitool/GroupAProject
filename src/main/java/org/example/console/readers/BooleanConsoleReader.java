package org.example.console.readers;

import org.example.console.readers.responses.BooleanResponse;
import org.example.console.readers.responses.StringResponse;

public class BooleanConsoleReader extends StringConsoleReader {
    public static synchronized BooleanResponse getBooleanData() {
        StringResponse stringResponse = getStringData();
        BooleanResponse booleanResponse = new BooleanResponse(stringResponse);
        if (booleanResponse.state != StringResponse.States.OK) return booleanResponse;
        try {
            booleanResponse.booleanData = Boolean.parseBoolean(booleanResponse.stringData);
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to boolean, reason: " + e.getMessage());
            booleanResponse.errorMessage = e.getMessage();
            booleanResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return booleanResponse;
    }

    public static synchronized BooleanResponse getBooleanData(String trueValue, String falseValue) {
        BooleanResponse booleanResponse = getBooleanData();
        if (booleanResponse.state != StringResponse.States.OK) return booleanResponse;
        if (booleanResponse.stringData.equals(trueValue)) {
            booleanResponse.booleanData = true;
            return booleanResponse;
        }
        if (booleanResponse.stringData.equals(falseValue)) {
            booleanResponse.booleanData = false;
            return booleanResponse;
        }
        booleanResponse.state = StringResponse.States.CANT_CONVERT;
        return booleanResponse;
    }
}
