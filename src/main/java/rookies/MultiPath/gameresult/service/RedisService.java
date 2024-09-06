package rookies.MultiPath.gameresult.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 게임 데이터 저장 (gameId와 userId 기반으로 저장)
    public void saveUserChoice(String gameId, String userId, String storyChoice) {
        redisTemplate.opsForValue().set("game:" + gameId + ":user:" + userId, storyChoice);
    }

    // 게임 데이터 조회 (gameId와 userId 기반으로 조회)
    public String getUserChoices(String gameId, String userId) {
        return redisTemplate.opsForValue().get("game:" + gameId + ":user:" + userId);
    }

    // 게임 데이터 삭제 (게임 종료 후)
    public void deleteUserChoices(String gameId, String userId) {
        redisTemplate.delete("game:" + gameId + ":user:" + userId);
    }

    // 게임 종료 후 Redis에서 모든 데이터 삭제
    public void clearGameData(String gameId, String userId) {
        String keyPattern = "game:" + gameId + ":user:" + userId;
        redisTemplate.delete(keyPattern);  // 해당 게임에 대한 모든 Redis 데이터 삭제
    }

}