package com.aia.pdf_learning.model.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Product(
        @NotNull(message = "Product Name cannot be null.")
        @Size(min = 1, message = "Product Name cannot be empty.")
        String name,
        @NotNull(message = "Quantity cannot be null.")
        @Size(min = 1, message = "Quantity cannot be empty.")
        String quantity,
        @NotNull(message = "Price cannot be null.")
        @Size(min = 1, message = "Price cannot be empty.")
        String price
) {}
