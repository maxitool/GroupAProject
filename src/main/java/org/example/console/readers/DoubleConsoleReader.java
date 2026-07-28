package org.example.console.readers;

import org.example.console.readers.responses.DoubleResponse;
import org.example.console.readers.responses.StringResponse;

public class DoubleConsoleReader extends StringConsoleReader {
    public static synchronized DoubleResponse getDoubleData() {
        StringResponse stringResponse = getStringData();
        DoubleResponse doubleResponse = new DoubleResponse(stringResponse);
        if (doubleResponse.state != StringResponse.States.OK)
            return doubleResponse;
        try {
            doubleResponse.doubleData = Double.parseDouble(doubleResponse.stringData.replace(" ","").replace(",","."));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to double. " + e.getMessage());
            doubleResponse.errorMessage = e.getMessage();
            doubleResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return doubleResponse;
    }
}
