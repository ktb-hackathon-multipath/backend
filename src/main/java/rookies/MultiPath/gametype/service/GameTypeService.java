package rookies.MultiPath.gametype.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rookies.MultiPath.gametype.dto.GameTypeRequest;
import rookies.MultiPath.gametype.dto.GameTypeResponse;
import rookies.MultiPath.gametype.entity.GameType;
import rookies.MultiPath.gametype.repository.GameTypeRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GameTypeService {

    private final GameTypeRepository gameTypeRepository;

    public List<GameTypeResponse> getAllGameTypes() {
        return gameTypeRepository.findAll().stream()
                .map(GameTypeResponse::from)
                .toList();
    }

    public GameTypeResponse updateGameType(Long gameTypeId, GameTypeRequest gameTypeRequest) {
        GameType gameType = gameTypeRepository.findById(gameTypeId).orElseThrow(() -> new IllegalArgumentException("GameType not found with GameType Id : " + gameTypeId));
        gameType.updateGameType(gameTypeRequest.title(), gameTypeRequest.person(), gameTypeRequest.era());
        return GameTypeResponse.from(gameType);
    }

    public void deleteGameType(Long id) {
        gameTypeRepository.deleteById(id);
    }

    public GameTypeResponse getGameType(Long gameTypeId) {
        GameType gameType = gameTypeRepository.findById(gameTypeId).orElseThrow(() -> new IllegalArgumentException("GameType not found with GameType Id : " + gameTypeId));
        return GameTypeResponse.from(gameType);
    }
}