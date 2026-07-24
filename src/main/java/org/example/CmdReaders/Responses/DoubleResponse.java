package org.example.CmdReaders.Responses;

public class DoubleResponse extends StringResponse {
    public double doubleData = 0;

    public DoubleResponse(StringResponse stringResponse) {
        this.state = stringResponse.state;
        this.stringData = new String(stringResponse.stringData);
        this.errorMessage = new String(stringResponse.errorMessage);
    }
}
