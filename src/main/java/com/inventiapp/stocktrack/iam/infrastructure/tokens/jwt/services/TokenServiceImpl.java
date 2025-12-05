package com.inventiapp.stocktrack.iam.infrastructure.tokens.jwt.services;

import com.inventiapp.stocktrack.iam.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JWT implementation of TokenService
 */
@Service
public class TokenServiceImpl implements BearerTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int TOKEN_BEGIN_INDEX = 7;

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    /**
     * Get the signing key from the secret
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Build a JWT token with the given user information
     */
    private String buildTokenWithDefaultParameters(Long userId, String email, List<String> roles) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + (long) expirationDays * 24 * 60 * 60 * 1000);

        var builder = Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .issuedAt(issuedAt)
                .expiration(expiration);

        // Add roles claim if provided
        if (roles != null && !roles.isEmpty()) {
            builder.claim("roles", roles);
        }

        return builder.signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateToken(Long userId, String email, List<String> roles) {
        return buildTokenWithDefaultParameters(userId, email, roles);
    }

    @Override
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        return buildTokenWithDefaultParameters(null, email, new ArrayList<>());
    }

    @Override
    public Long getUserIdFromToken(String token) {
        String subject = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            LOGGER.error("Token subject is not a valid user ID: {}", subject);
            return null;
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        // Try to get email from claim first
        String email = claims.get("email", String.class);
        if (email != null) {
            return email;
        }
        
        // Fallback: if email is not in claims, subject might be email (backward compatibility)
        String subject = claims.getSubject();
        if (subject != null && subject.contains("@")) {
            return subject;
        }
        
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
        }
        
        return new ArrayList<>();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(TOKEN_BEGIN_INDEX);
        }
        return null;
    }
}

