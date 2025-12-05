package com.inventiapp.stocktrack.iam.application.internal.outboundservices.tokens;

/**
 * Service interface for token operations
 */
public interface TokenService {

    /**
     * Generate a token for an email
     * @param email The email (used as subject in JWT)
     * @return The generated token
     */
    String generateToken(String email);

    /**
     * Extract email from a token
     * @param token The token
     * @return The email (subject from JWT)
     */
    String getUsernameFromToken(String token);

    /**
     * Validate a token
     * @param token The token to validate
     * @return true if valid, false otherwise
     */
    boolean validateToken(String token);
}

