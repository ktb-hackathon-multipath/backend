package rookies.MultiPath.gametype.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rookies.MultiPath.empathybutton.entity.EmpathyButton;

import java.util.List;

@Entity
@Table(name = "game_types")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String person;

    @Column
    private String era;

    @Column
    private String image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_type_id")  // foreign key로 사용될 column
    private List<EmpathyButton> empathyButtons;

    @Builder
    public GameType(String title, String person, String era, String image, List<EmpathyButton> empathyButtons) {
        this.title = title;
        this.person = person;
        this.era = era;
        this.image = image;
        this.empathyButtons = empathyButtons;
    }

    public static GameType createGameType(String title, String person, String era, String image, List<EmpathyButton> empathyButtons) {
        return GameType.builder()
                .title(title)
                .person(person)
                .era(era)
                .image(image)
                .empathyButtons(empathyButtons)
                .build();
    }

    public void updateGameType(String title, String person, String era) {
        this.title = title;
        this.person = person;
        this.era = era;
    }

    public void addEmpathyButton(EmpathyButton button) {
        this.empathyButtons.add(button);
    }

    public void removeEmpathyButton(EmpathyButton button) {
        this.empathyButtons.remove(button);
    }
}