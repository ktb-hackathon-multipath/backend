package rookies.MultiPath.gameresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rookies.MultiPath.gameresult.entity.GameResult;

import java.util.List;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {

    @Query("""
            SELECT g
            FROM GameResult g
            WHERE g.user.id = :userId
  
            """)
    List<GameResult> findByUserId(@Param("userId") Long userId);  // 특정 사용자의 게임 기록 조회
}
