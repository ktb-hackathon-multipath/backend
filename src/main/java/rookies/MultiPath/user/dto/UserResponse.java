package rookies.MultiPath.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import rookies.MultiPath.user.entity.Role;
import rookies.MultiPath.user.entity.User;

@Builder
public record UserResponse(

        @Schema(description = "User ID", example = "1")
        Long id,

        @Schema(description = "User Email", example = "sunny@gmail.com")
        String email,

        @Schema(description = "User Nickname", example = "초코바")
        String nickname,

        @Schema(description = "User Role", example = "USER")
        Role role

) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
