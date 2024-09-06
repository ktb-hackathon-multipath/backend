package rookies.MultiPath.core.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import rookies.MultiPath.core.auth.filter.JwtLoginFilter;
import rookies.MultiPath.core.auth.filter.JwtUtil;
import rookies.MultiPath.user.service.CustomUserDetailsService;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성 (인증/인가 및 로그아웃을 설정)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능을 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증을 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 기능을 비활성화
                .authorizeHttpRequests(auth -> auth // 인가 작업
                        .requestMatchers("/auth/**").permitAll() // 임시 토큰 발급 API
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/game-types/**").permitAll()
                        .requestMatchers("/game/**").permitAll() // 공개 API
                        .requestMatchers("/auth/**").permitAll() // 공개 API
                        .requestMatchers("/user/**").hasRole("USER") // 실사용자 관련 API
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .logout((logout) -> logout // 로그아웃
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer // 세션 관리 정책 설정 (Stateless)
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtLoginFilter(jwtUtil, customUserDetailsService), UsernamePasswordAuthenticationFilter.class); // jwt 토큰을 검증하는 필터 등록
        return http.build();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
