package com.example.store.interceptor;

import com.example.store.model.User;
import com.example.store.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.*;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public TokenInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Không có token, truy cập bị từ chối");
            return false;
        }

        token = token.substring(7);
        User user = userRepository.findByToken(token);
        if (user != null) {
            // request.setAttribute("user", user.get()); // Lưu user vào request để dùng sau này
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
            return false;
        }
    }
}
