package rookies.MultiPath.empathybutton.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record EmpathyButtonRequest(
        @Schema(description = "버튼 타입", example = "fun")
        String buttonType,

        @Schema(description = "초기 클릭 수", example = "0")
        Long clickCount
) {}