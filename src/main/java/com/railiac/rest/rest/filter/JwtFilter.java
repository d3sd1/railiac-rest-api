package com.railiac.rest.rest.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.railiac.rest.database.model.FilterJwtConfig;
import com.railiac.rest.database.model.Role;
import com.railiac.rest.database.model.User;
import com.railiac.rest.database.repository.FilterJwtConfigRepository;
import com.railiac.rest.rest.request.RequestUser;
import com.railiac.rest.util.JwtUtil;
import io.jsonwebtoken.*;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Filter(name = "loginFilter")
@Component
@Order(1)
public class JwtFilter extends OncePerRequestFilter {

    private final List<FilterJwtConfig> urlPatterns;
    private final AntPathMatcher pathMatcher;

    private final JwtUtil jwtUtil;

    @Autowired
    private RequestUser requestUser;

    public JwtFilter(JwtUtil jwtUtil, FilterJwtConfigRepository filterJwtConfigRepository) {
        this.pathMatcher = new AntPathMatcher();
        this.jwtUtil = jwtUtil;
        this.urlPatterns = filterJwtConfigRepository.findAll();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        return urlPatterns.stream()
                .anyMatch(p -> {
                    if (p.isExcludes()) {
                        return this.pathMatcher.match(p.getUrlPattern(), request.getRequestURI());
                    }
                    return false;
                });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.equalsIgnoreCase("")) {
            this.unauthorizeCall(response, "INVALID_JWT");
            return;
        }
        jwt = jwt.replace("Bearer ", "");
        try {
            final ObjectMapper mapper = new ObjectMapper();

            Claims claims = this.jwtUtil.validate(jwt);
            User user = mapper.convertValue(claims.get("user"), User.class);
            requestUser.setUser(user);
            Set<Role> roles = mapper.convertValue(claims.get("roles"), new TypeReference<>() {
            });
            if (user == null || roles == null) {
                throw new UnsupportedJwtException("User or roles are null, decodification failed.");
            }
            List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                    authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            this.unauthorizeCall(response, "MALFORMED_JWT");
        } catch (ExpiredJwtException e) {
            this.unauthorizeCall(response, "EXPIRED_JWT");
        }
    }

    private void unauthorizeCall(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
        SecurityContextHolder.clearContext();
    }
}