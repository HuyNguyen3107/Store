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
        String email = request.getHeader("Email");

        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Không có token, truy cập bị từ chối");
            return false;
        }

        if (email == null || email.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Không có email, truy cập bị từ chối");
            return false;
        }

        token = token.substring(7);
        User user = userRepository.findByEmailAndToken(email, token);
        if (user != null) {
            // request.setAttribute("user", user.get()); // Lưu user vào request để dùng sau này
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
            return false;
        }
    }
}
