package com.aia.pdf_learning.model.response;

import java.util.Map;

public record InvalidInputErrorResponse(
        String message,
        Integer statusCode,
        Map<String, String> errors
) {}
