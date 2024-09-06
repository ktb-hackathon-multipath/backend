package rookies.MultiPath.empathybutton.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import rookies.MultiPath.empathybutton.entity.EmpathyButton;

@Schema(description = "Empathy Button Response DTO")
public record EmpathyButtonResponse(
        @Schema(description = "Empathy Button ID", example = "1")
        Long empathyButtonId,

        @Schema(description = "Button Type", example = "fun")
        String buttonType,

        @Schema(description = "Click Count", example = "100")
        Long clickCount
) {
    public static EmpathyButtonResponse from(EmpathyButton empathyButton) {
        return new EmpathyButtonResponse(
                empathyButton.getId(),
                empathyButton.getButtonType(),
                empathyButton.getClickCount()
        );
    }

    public EmpathyButton toEntity() {
        return EmpathyButton.builder()
                .buttonType(this.buttonType)
                .clickCount(this.clickCount)
                .build();
    }
}
