package rookies.MultiPath.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rookies.MultiPath.user.entity.User;
import rookies.MultiPath.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email) // username 대신 email 변수명 사용
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Long userIdAsLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdAsLong)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID : " + userId));
        return new CustomUserDetails(user);
    }
}
