package rookies.MultiPath.gametype.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import rookies.MultiPath.empathybutton.dto.EmpathyButtonResponse;
import rookies.MultiPath.gametype.entity.GameType;

import java.util.List;

@Builder
public record GameTypeResponse(

        @Schema(description = "Game Type ID", example = "1")
        Long gameTypeId,

        @Schema(description = "Title of the Game Type", example = "Adventure")
        String title,

        @Schema(description = "Person associated with the Game Type", example = "Hero")
        String person,

        @Schema(description = "게임의 시대 배경", example = "조선시대")
        String era,

        @Schema(description = "List of Empathy Buttons associated with this Game Type")
        List<EmpathyButtonResponse> empathyButtons

) {

    public static GameTypeResponse from(GameType gameType) {
        return GameTypeResponse.builder()
                .gameTypeId(gameType.getId())
                .title(gameType.getTitle())
                .person(gameType.getPerson())
                .era(gameType.getEra())
                .empathyButtons(gameType.getEmpathyButtons() != null
                        ? gameType.getEmpathyButtons().stream()
                        .map(EmpathyButtonResponse::from)
                        .toList()
                        : null)
                .build();
    }
}
