package rookies.MultiPath.gameresult.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rookies.MultiPath.gameresult.service.RedisService;

@RestController
@RequestMapping(path = "/public/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getUserChoices(@PathVariable String gameId, @PathVariable String userId) {
        String choices = redisService.getUserChoices(gameId, userId);
        return ResponseEntity.ok(choices);
    }
}
