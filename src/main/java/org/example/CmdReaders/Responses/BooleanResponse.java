package org.example.CmdReaders.Responses;

public class BooleanResponse extends StringResponse{
    public boolean booleanData = false;

    public BooleanResponse(StringResponse stringResponse) {
        this.state = stringResponse.state;
        this.stringData = new String(stringResponse.stringData);
        this.errorMessage = new String(stringResponse.errorMessage);
    }
}
