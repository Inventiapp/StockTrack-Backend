package com.inventiapp.stocktrack.reports.interfaces.rest.resources;

import java.util.Map;

/**
 * Resource for dashboard notifications.
 * @param id Notification ID
 * @param type Type of notification (warning, alert, info)
 * @param title Title key for translation
 * @param message Message (optional)
 * @param data Additional data (product name, date, quantity, etc.)
 */
public record NotificationResource(
        String id,
        String type,
        String title,
        String message,
        Map<String, Object> data
) {}

