package org.example.o2o.config.exception.enums;

public interface ErrorCode<T> {

	int getHttpStatusCode();

	String getErrorCode();

	String getErrorMessage();

}
