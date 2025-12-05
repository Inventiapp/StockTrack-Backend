package com.inventiapp.stocktrack.iam.application.internal.outboundservices.hashing;

/**
 * Service interface for password hashing operations
 */
public interface HashingService {

    /**
     * Encode a raw password
     * @param rawPassword The raw password to encode
     * @return The encoded password
     */
    String encode(CharSequence rawPassword);

    /**
     * Check if a raw password matches an encoded password
     * @param rawPassword The raw password
     * @param encodedPassword The encoded password
     * @return true if they match, false otherwise
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);
}

