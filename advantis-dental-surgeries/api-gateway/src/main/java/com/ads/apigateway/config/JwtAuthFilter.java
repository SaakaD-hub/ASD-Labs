package com.ads.apigateway.config;


import com.ads.apigateway.utl.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        // ✅ Skip JWT validation for public endpoints
        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // ✅ NEW - Extract role and enforce RBAC
        String role = jwtUtil.extractRole(token);
        String username = jwtUtil.extractUsername(token);
        String userId = jwtUtil.extractUserId(token);

        // ✅ NEW - Role-Based Access Control
        if (!hasPermission(path, method, role)) {
            System.out.println("Access denied for user: " + username + " with role: " + role + " to " + path);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // ✅ NEW - Add user info to headers for downstream services
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .header("X-Username", username)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    // ✅ NEW - Permission checking logic
    private boolean hasPermission(String path, String method, String role) {
        // Office Manager endpoints - only ROLE_MANAGER
        if (path.startsWith("/api/v1/manager")) {
            return role.equals("ROLE_MANAGER") || role.equals("ROLE_ADMIN");
        }

        // Dentist endpoints
        if (path.startsWith("/api/v1/dentists")) {
            // Managers can do everything
            if (role.equals("ROLE_MANAGER") || role.equals("ROLE_ADMIN")) {
                return true;
            }
            // Dentists can only GET (view)
            if (role.equals("ROLE_DENTIST") && method.equals("GET")) {
                return true;
            }
            return false;
        }

        // Patient endpoints
        if (path.startsWith("/api/v1/patients")) {
            // Managers and dentists can access
            if (role.equals("ROLE_MANAGER") || role.equals("ROLE_DENTIST") || role.equals("ROLE_ADMIN")) {
                return true;
            }
            // Patients can only access their own data (checked at service level)
            if (role.equals("ROLE_PATIENT") && method.equals("GET")) {
                return true;
            }
            return false;
        }

        // Appointment endpoints
        if (path.startsWith("/api/v1/appointments")) {
            // Everyone can create/view appointments (service will enforce own data access)
            return true;
        }

        // Surgery endpoints
        if (path.startsWith("/api/v1/surgeries")) {
            // Only managers can manage surgeries
            return role.equals("ROLE_MANAGER") || role.equals("ROLE_ADMIN");
        }

        // Billing endpoints
        if (path.startsWith("/api/v1/bills")) {
            // Managers can do everything
            if (role.equals("ROLE_MANAGER") || role.equals("ROLE_ADMIN")) {
                return true;
            }
            // Patients can view their own bills
            if (role.equals("ROLE_PATIENT") && method.equals("GET")) {
                return true;
            }
            return false;
        }

        // Default: deny
        return false;
    }
    @Override
    public int getOrder() {
        return -1; // ensure it runs before the routing filters
    }
}
