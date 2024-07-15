package cms.backend.responses;

import cms.backend.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public abstract class BaseResponsePayload {
    private final String message;
    private final Severity severity;

    public String getSeverity() {
        return severity.name().toLowerCase();
    }
}
