package cms.backend.responses;

import cms.backend.dtos.responses.AdvertResponseDto;
import cms.backend.enums.Severity;
import lombok.Getter;

@Getter
public class CreateAdvertResponsePayload extends MessageResponsePayload {
    private final AdvertResponseDto advertResponseDto;

    public CreateAdvertResponsePayload(String message, Severity severity, AdvertResponseDto advertResponseDto) {
        super(message, severity);
        this.advertResponseDto = advertResponseDto;
    }

    public static CreateAdvertResponsePayload success(String message, AdvertResponseDto advertResponseDto) {
        return new CreateAdvertResponsePayload(message, Severity.SUCCESS, advertResponseDto);
    }
}
