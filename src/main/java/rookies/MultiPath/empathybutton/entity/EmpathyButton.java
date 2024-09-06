package rookies.MultiPath.empathybutton.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rookies.MultiPath.gametype.entity.GameType;

@Entity
@Table(name = "empathy_buttons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmpathyButton {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "button_type", nullable = false)
    private String buttonType;

    @Column(name = "click_count", nullable = false)
    private Long clickCount;

    @ManyToOne
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

    @Builder
    public EmpathyButton(String buttonType, Long clickCount, GameType gameType) {
        this.buttonType = buttonType;
        this.clickCount = clickCount;
        this.gameType = gameType;
    }

    public Long incrementClickCount() {
        this.clickCount++;
        return this.clickCount;
    }
}
