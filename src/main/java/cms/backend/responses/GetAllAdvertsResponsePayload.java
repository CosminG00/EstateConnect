package cms.backend.responses;

import cms.backend.dtos.responses.AdvertResponseDto;

import java.util.List;

public record GetAllAdvertsResponsePayload(List<AdvertResponseDto> adverts) {
}
