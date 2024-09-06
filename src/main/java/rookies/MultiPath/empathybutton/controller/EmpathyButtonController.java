package rookies.MultiPath.empathybutton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rookies.MultiPath.empathybutton.dto.EmpathyButtonResponse;
import rookies.MultiPath.empathybutton.service.EmpathyButtonService;

import java.util.List;

@RestController
@RequestMapping("/public/empathy-button")
@RequiredArgsConstructor
public class EmpathyButtonController {

    private final EmpathyButtonService empathyButtonService;

    @GetMapping("{gameTypeId}")
    public ResponseEntity<List<EmpathyButtonResponse>> getButtonsByGameType(@PathVariable("gameTypeId") Long gameTypeId) {
        List<EmpathyButtonResponse> buttons = empathyButtonService.getButtonsByGameType(gameTypeId);
        return ResponseEntity.ok(buttons);
    }

    @PostMapping("/{gameTypeId}/{buttonId}")
    public ResponseEntity<Long> incrementClickCount(@PathVariable("gameTypeId") Long gameTypeId, @PathVariable("buttonId") Long buttonId) {
        Long updatedClickCount = empathyButtonService.incrementClickCount(gameTypeId, buttonId);
        return ResponseEntity.ok(updatedClickCount);
    }
}
