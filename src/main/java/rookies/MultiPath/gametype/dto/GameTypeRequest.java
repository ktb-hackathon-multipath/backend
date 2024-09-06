package rookies.MultiPath.gametype.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import rookies.MultiPath.empathybutton.dto.EmpathyButtonRequest;
import rookies.MultiPath.gametype.entity.GameType;

import java.util.List;

@Builder
public record GameTypeRequest(
        @Schema(description = "테마 제목", example = "스티브 잡스가 되어보자")
        String title,

        @Schema(description = "테마 인물", example = "스티브 잡스")
        String person,

        @Schema(description = "테마 시대배경", example = "조선시대")
        String era,

        @Schema(description = "공감 버튼 목록", example = "[{buttonType: 'fun', clickCount: 0}, {buttonType: 'great', clickCount: 0}]")
        List<EmpathyButtonRequest> empathyButtons
) {}
