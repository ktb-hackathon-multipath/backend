package rookies.MultiPath.gameresult.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rookies.MultiPath.user.entity.User;

@Entity
@Table(name = "games")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 게임을 진행한 사용자

    @Column(name = "story", columnDefinition = "TEXT", nullable = false)
    private String story;  // 게임 진행 중 생성된 스토리

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;  // 게임 결과

    @Column(name = "result_image")
    private String resultImage; // 게임 결과 이미지

    @Builder
    public GameResult(User user, String story, String result, String resultImage) {
        this.user = user;
        this.story = story;
        this.result = result;
        this.resultImage = resultImage;
    }

    public static GameResult createGame(User user, String story, String result, String resultImage) {
        return GameResult.builder()
                .user(user)
                .story(story)
                .result(result)
                .build();
    }

    public void updateResult(String result) {
        this.result = result;  // 게임 결과 업데이트
    }
}
