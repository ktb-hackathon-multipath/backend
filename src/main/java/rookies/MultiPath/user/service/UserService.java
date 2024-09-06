package rookies.MultiPath.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rookies.MultiPath.core.auth.filter.JwtUtil;
import rookies.MultiPath.user.dto.UserRegisterRequest;
import rookies.MultiPath.user.dto.UserResponse;
import rookies.MultiPath.user.entity.Role;
import rookies.MultiPath.user.entity.User;
import rookies.MultiPath.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponse createUser(UserRegisterRequest userRegisterRequest) {
        // 이미 존재하는 이메일인지 확인
        if (userRepository.findByEmail(userRegisterRequest.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.findByNickname(userRegisterRequest.nickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.password());
        User user = userRegisterRequest.toEntity();
        user.changePassword(encodedPassword);
        return UserResponse.from(userRepository.save(user));
    }

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user id : " + userId));
        // 비밀번호 암호화 및 변경
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.changePassword(encodedPassword);

        userRepository.save(user);
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user id: " + userId));
        return UserResponse.from(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserResponse.from(user);
    }

    // 사용자 삭제 (논리적 삭제 - archived 필드를 true로 변경)
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user id: " + userId));
        user.archive();
        userRepository.save(user);
    }

    // 임시 사용자 정보 반환
    public UserResponse getTemporaryUserFromToken(String token) {
        if (jwtUtil.isTemporaryToken(token)) {
            String userId = jwtUtil.getUserId(token);
            return new UserResponse(Long.parseLong(userId), "guest@guest.com", "Guest User", Role.GUEST);
        }
        throw new IllegalArgumentException("Invalid token");
    }
}
