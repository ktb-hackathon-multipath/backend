package rookies.MultiPath.gameresult.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookies.MultiPath.gameresult.entity.GameResult;
import rookies.MultiPath.gameresult.repository.GameResultRepository;
import rookies.MultiPath.user.entity.User;
import rookies.MultiPath.user.repository.UserRepository;

import java.util.List;

@Service
public class GameResultService {

    private final GameResultRepository gameRepository;
    private final RedisService redisService;
    private final UserRepository userRepository;

    public GameResultService(GameResultRepository gameRepository, RedisService redisService, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.redisService = redisService;
        this.userRepository = userRepository;
    }

    // Redis에 저장된 게임 데이터를 DB에 영구 저장
    @Transactional
    public void saveGameFromRedisToDB(String gameId, Long userId, String result, String resultImage) {
        // Redis에서 데이터 조회
        String storyData = redisService.getUserChoices(gameId, userId.toString());

        if (storyData == null) {
            throw new IllegalStateException("No game data found in Redis");
        }

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // DB에 게임 데이터 저장
        GameResult game = GameResult.createGame(user, storyData, result, resultImage);
        gameRepository.save(game);

        // Redis에서 게임 데이터 삭제
        redisService.deleteUserChoices(gameId, userId.toString());
    }

    // 저장된 게임 결과 조회
    public List<GameResult> getGamesByUser(Long userId) {
        return gameRepository.findByUserId(userId);
    }
}
