package com.inventiapp.stocktrack.iam.infrastructure.authorization.sfs.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Builder for UsernamePasswordAuthenticationToken
 */
public class UsernamePasswordAuthenticationTokenBuilder {

    /**
     * Build an authentication token from UserDetails and HTTP request
     * @param userDetails The user details
     * @param request The HTTP request
     * @return UsernamePasswordAuthenticationToken
     */
    public static UsernamePasswordAuthenticationToken build(UserDetails userDetails, 
                                                             HttpServletRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }
}

