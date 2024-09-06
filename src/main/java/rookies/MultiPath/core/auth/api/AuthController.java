package rookies.MultiPath.core.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rookies.MultiPath.core.auth.filter.JwtUtil;
import rookies.MultiPath.user.dto.UserLoginRequest;
import rookies.MultiPath.user.dto.UserRegisterRequest;
import rookies.MultiPath.user.dto.UserResponse;
import rookies.MultiPath.user.service.CustomUserDetails;
import rookies.MultiPath.user.service.CustomUserDetailsService;
import rookies.MultiPath.user.service.UserService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserResponse userResponse = userService.createUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.email(), userLoginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 사용자 정보 로드 및 JWT 토큰 생성
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userLoginRequest.email());
            Long userId = userDetails.getUserId();
            String username = userDetails.getUsername();
            String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String token = jwtUtil.generateAccessToken(userId, username, role);

            // Jwt 토큰 반환
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Login Successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 클라이언트 측에서 Jwt 토큰을 삭제하는 방식으로 로그아웃 처리 (백에선 인증정보 삭제)
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout Successful");
    }

    // 임시 토큰 발급을 위한 API
    @PostMapping("/tokens/temp")
    public ResponseEntity<String> generateTemporaryToken(@RequestHeader("GameId") String gameId) {
        String temporaryToken = jwtUtil.generateTemporaryToken(gameId);
        return ResponseEntity.ok(temporaryToken);
    }

}
