package cms.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateFavoriteRequestDto {
    private UUID advertId;
}
