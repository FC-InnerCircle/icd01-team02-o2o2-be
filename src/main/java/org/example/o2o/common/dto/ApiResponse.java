package org.example.o2o.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	private T response;
	private String code;
	private String message;

	public static <T> ApiResponse<T> success(T response) {
		return new ApiResponse<T>(response, "200", "성공");
	}

	public static <T> ApiResponse<T> success(T response, String message) {
		return new ApiResponse<T>(response, "200", message);
	}

	public static ApiResponse<Void> success() {
		return new ApiResponse<>(null, "200", "성공");
	}
}
