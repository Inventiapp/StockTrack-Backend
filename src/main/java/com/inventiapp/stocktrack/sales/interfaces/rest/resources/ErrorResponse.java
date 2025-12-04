package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

public record ErrorResponse(String message, String error) {
    public ErrorResponse(String message) {
        this(message, "Bad Request");
    }
}

