package com.fairfood.user.service;

import com.fairfood.user.dto.*;
import com.fairfood.user.model.User;
import com.fairfood.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

/**
 * 用户服务实现类
 * 处理用户注册、登录、认证等业务逻辑
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private Long jwtExpiration;

    /**
     * 用户注册
     * @param request 注册请求
     * @return 认证响应(包含JWT)
     */
    public AuthResponse register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建用户
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .userType(request.getUserType() != null ? request.getUserType() : "USER")
                .build();

        user = userRepository.save(user);
        log.info("用户注册成功: {}", user.getUsername());

        // 生成JWT令牌
        String token = generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .userType(user.getUserType())
                .expiresIn(jwtExpiration / 1000)
                .build();
    }

    /**
     * 用户登录
     * @param request 登录请求
     * @return 认证响应(包含JWT)
     */
    public AuthResponse login(LoginRequest request) {
        // 查找用户
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        log.info("用户登录成功: {}", user.getUsername());

        // 生成JWT令牌
        String token = generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .userType(user.getUserType())
                .expiresIn(jwtExpiration / 1000)
                .build();
    }

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息响应
     */
    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .createdAt(user.getCreatedAt())
                .build();
    }

    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 用户实体
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 生成JWT令牌
     * @param user 用户实体
     * @return JWT令牌字符串
     */
    private String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("userType", user.getUserType())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }
}
