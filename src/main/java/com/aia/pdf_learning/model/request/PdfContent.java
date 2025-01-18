package com.aia.pdf_learning.model.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PdfContent(
        @NotNull(message = "Order id cannot be null.")
        @Size(min = 1, message = "Order id cannot be empty.")
        String orderId,
        @NotNull(message = "Order id cannot be null.")
        @Size(min = 1, message = "Order id cannot be empty.")
        String customerName,
        @NotNull(message = "Order id cannot be null.")
        @Size(min = 1, message = "Order id cannot be empty.")
        String customerPhoneNumber,
        String customerAddress,
        @Valid
        List<Product> products
) {}
