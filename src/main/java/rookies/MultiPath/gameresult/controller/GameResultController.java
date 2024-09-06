package rookies.MultiPath.gameresult.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rookies.MultiPath.core.auth.filter.JwtUtil;
import rookies.MultiPath.gameresult.service.GameResultService;

@RestController
@RequestMapping(path = "/public/game-result")
public class GameResultController {

    private final GameResultService gameService;
    private final JwtUtil jwtUtil;

    public GameResultController(GameResultService gameService, JwtUtil jwtUtil) {
        this.gameService = gameService;
        this.jwtUtil = jwtUtil;
    }

    // 게임 결과 저장 (로그인한 사용자만)
    @PostMapping("/save")
    public ResponseEntity<String> saveGameResult(@RequestHeader("Authorization") String token, @RequestBody String result, @RequestBody String resultImage) {
        token = token.substring(7);  // "Bearer " 부분 제거
        String userIdString = jwtUtil.getUserId(token);  // JWT에서 userId를 String추출
        Long userId = Long.parseLong(userIdString);
        String gameId = jwtUtil.getGameId(token);  // JWT에서 gameId 추출

        gameService.saveGameFromRedisToDB(gameId, userId, result, resultImage);
        return ResponseEntity.ok("Game result saved successfully.");
    }

    // 저장된 게임 결과 조회
    @GetMapping("/saved")
    public ResponseEntity<Object> getSavedGames(@RequestHeader("Authorization") String token) {
        String userIdString = jwtUtil.getUserId(token);  // JWT에서 userId 추출
        Long userId = Long.parseLong(userIdString);
        return ResponseEntity.ok(gameService.getGamesByUser(userId));
    }
}
