package rookies.MultiPath.empathybutton.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookies.MultiPath.empathybutton.dto.EmpathyButtonResponse;
import rookies.MultiPath.empathybutton.entity.EmpathyButton;
import rookies.MultiPath.empathybutton.repository.EmpathyButtonRepository;
import rookies.MultiPath.gametype.repository.GameTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpathyButtonService {

    private final EmpathyButtonRepository empathyButtonRepository;

    @Transactional(readOnly = true)
    public List<EmpathyButtonResponse> getButtonsByGameType(Long gameTypeId) {
        List<EmpathyButton> empathyButtons = empathyButtonRepository.findByGameTypeId(gameTypeId);
        return empathyButtons.stream()
                .map(EmpathyButtonResponse::from)
                .toList();
    }

    @Transactional
    public Long incrementClickCount(Long gameTypeId, Long buttonId) {
        EmpathyButton empathyButton = empathyButtonRepository.findByGameTypeIdAndButtonId(gameTypeId, buttonId);
        Long clickCount = empathyButton.incrementClickCount();
        empathyButtonRepository.save(empathyButton);
        return clickCount;
    }
}