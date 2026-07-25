package org.example.ConsoleReaders.Responses;

public class DoubleResponse extends StringResponse {
    public double doubleData = 0;

    public DoubleResponse(StringResponse stringResponse) {
        this.state = stringResponse.state;
        this.stringData = stringResponse.stringData;
        this.errorMessage = stringResponse.errorMessage;
    }
}
