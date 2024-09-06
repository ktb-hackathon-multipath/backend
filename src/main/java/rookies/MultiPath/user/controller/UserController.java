package rookies.MultiPath.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rookies.MultiPath.user.dto.UserRegisterRequest;
import rookies.MultiPath.user.dto.UserResponse;
import rookies.MultiPath.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    // 사용자 등록 API
//    @PostMapping("/register")
//    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
//        UserResponse userResponse = userService.createUser(userRegisterRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
//    }

    // 사용자 정보 조회 API (UserId 기반)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    // 사용자 정보 조회 API (Email 기반)
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    // 사용자 비밀번호 변경 API
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam Long userId, @RequestParam String newPassword) {
        userService.changePassword(userId, newPassword);
        return ResponseEntity.ok("Password updated successfully.");
    }

    // 사용자 삭제 API (논리적 삭제)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // 임시 사용자 정보 조회
    @GetMapping("/temporary")
    public ResponseEntity<UserResponse> getTemporaryUser(@RequestHeader("Authorization") String token) {
        token = token.substring(7); // "Bearer " 부분 제거
        UserResponse userResponse = userService.getTemporaryUserFromToken(token);
        return ResponseEntity.ok(userResponse);
    }
}