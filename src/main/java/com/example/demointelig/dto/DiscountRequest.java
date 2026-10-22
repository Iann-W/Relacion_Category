package com.example.demointelig.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DiscountRequest(
        @NotNull(message = "El porcentaje es obligatorio")
        @Min(value = 1, message = "El porcentaje de descuento debe ser mayor que 0")
        @Max(value = 100, message = "El porcentaje de descuento debe ser menor o igual que 100")
        Double percentage
) {
}