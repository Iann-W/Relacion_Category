package com.example.demointelig.dto;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        int stock,

        String categoryName,
        Long categoryId
){

}