package rookies.MultiPath.gametype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rookies.MultiPath.gametype.entity.GameType;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Long> {
}