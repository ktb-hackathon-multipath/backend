package rookies.MultiPath.gametype.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rookies.MultiPath.gametype.dto.GameTypeResponse;
import rookies.MultiPath.gametype.service.GameTypeService;

import java.util.List;

@RestController
@RequestMapping(path = "/public/game-type")
@RequiredArgsConstructor
public class GameTypeController {

    private final GameTypeService gameTypeService;

    @Operation(summary = "Get all Game Types", description = "모든 게임 종류 조회")
    @GetMapping
    public ResponseEntity<List<GameTypeResponse>> getAllGameTypes() {
        return ResponseEntity.ok(gameTypeService.getAllGameTypes());
    }

    @Operation(summary = "Get all Game Types", description = "게임 종류 조회")
    @GetMapping(path = "/{gameTypeId}")
    public ResponseEntity<GameTypeResponse> getGameType(@PathVariable("gameTypeId") Long gameTypeId) {
        return ResponseEntity.ok(gameTypeService.getGameType(gameTypeId));
    }

}
