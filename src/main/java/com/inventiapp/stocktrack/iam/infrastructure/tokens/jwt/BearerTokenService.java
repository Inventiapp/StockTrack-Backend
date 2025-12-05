package com.inventiapp.stocktrack.iam.infrastructure.tokens.jwt;

import com.inventiapp.stocktrack.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * Extended token service for Bearer token operations
 */
public interface BearerTokenService extends TokenService {

    /**
     * Extract Bearer token from HTTP request
     * @param request The HTTP request
     * @return The token string or null if not found
     */
    String getBearerTokenFrom(HttpServletRequest request);

    /**
     * Generate token from Spring Security Authentication
     * @param authentication The authentication object
     * @return The generated token
     */
    String generateToken(Authentication authentication);
}

