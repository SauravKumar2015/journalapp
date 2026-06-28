package com.saurav.journalApp.filter;

import com.saurav.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // 🔥 Use servlet path (correct with context-path)
        String path = request.getServletPath();

        // ✅ Allow public endpoints without JWT
        if (path.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // ✅ Extract JWT from Authorization header (case-insensitive Bearer)
        if (authorizationHeader != null) {
            String headerValue = authorizationHeader.trim();
            if (headerValue.regionMatches(true, 0, "Bearer ", 0, 7)) {
                jwt = headerValue.substring(7).trim();
            } else if (!headerValue.contains(" ")) {
                jwt = headerValue;
            }
            if (jwt != null && !jwt.isBlank()) {
                username = jwtUtil.extractUsername(jwt);
            }
        }

        // ✅ Only try JWT authentication when a valid token is present
        if (jwt == null || jwt.isBlank()) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Authenticate user
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // 🔥 CRITICAL LINE
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
