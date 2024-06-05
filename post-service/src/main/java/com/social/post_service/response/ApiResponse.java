package com.social.post_service.response;


import java.util.Map;

public record ApiResponse<T>(boolean success, Map<String, T> data,String message) {
}
