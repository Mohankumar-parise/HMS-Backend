package com.mohan.authservice.dtos;

import lombok.Data;

@Data
public class APIResponse<T> {
    private String message;
    private int statusCode;
    private T data;
}
