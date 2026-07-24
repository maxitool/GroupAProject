package org.example.CmdReaders.Responses;

public class LongResponse extends StringResponse {
    public long longData = 0;

    public LongResponse(StringResponse stringResponse) {
        this.state = stringResponse.state;
        this.stringData = new String(stringResponse.stringData);
        this.errorMessage = new String(stringResponse.errorMessage);
    }
}
