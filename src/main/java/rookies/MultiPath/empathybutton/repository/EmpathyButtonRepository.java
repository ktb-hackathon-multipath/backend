package rookies.MultiPath.empathybutton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rookies.MultiPath.empathybutton.entity.EmpathyButton;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookies.MultiPath.empathybutton.entity.EmpathyButton;

import java.util.List;

@Repository
public interface EmpathyButtonRepository extends JpaRepository<EmpathyButton, Long> {

    // 특정 게임 타입에 속한 모든 공감 버튼을 조회
    @Query("SELECT e FROM EmpathyButton e WHERE e.gameType.id = :gameTypeId")
    List<EmpathyButton> findByGameTypeId(@Param("gameTypeId") Long gameTypeId);

    // 특정 게임 타입과 버튼 ID에 해당하는 공감 버튼을 조회
    @Query("SELECT e FROM EmpathyButton e WHERE e.gameType.id = :gameTypeId AND e.id = :buttonId")
    EmpathyButton findByGameTypeIdAndButtonId(@Param("gameTypeId") Long gameTypeId, @Param("buttonId") Long buttonId);

    // 특정 게임 타입에 속한 모든 공감 버튼의 클릭 수 합계를 조회
    @Query("SELECT SUM(e.clickCount) FROM EmpathyButton e WHERE e.gameType.id = :gameTypeId")
    Long findTotalClickCountByGameTypeId(@Param("gameTypeId") Long gameTypeId);
}