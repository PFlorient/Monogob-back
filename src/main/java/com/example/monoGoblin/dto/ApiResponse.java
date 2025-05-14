package com.example.monoGoblin.dto;

public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;

    public ApiResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(data, message, true);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<T>(null, message, false);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
