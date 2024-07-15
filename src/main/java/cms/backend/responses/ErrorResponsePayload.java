package cms.backend.responses;


import cms.backend.enums.Severity;

public class ErrorResponsePayload extends BaseResponsePayload{
    public ErrorResponsePayload(String message) {
        super(message, Severity.ERROR);
    }
}
