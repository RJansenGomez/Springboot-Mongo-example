package org.rjansen.common.repository.exception;

public class FilterNotValidRuntimeException extends RuntimeException {
    private static final String MESSAGE = "Filter %s not valid";

    public FilterNotValidRuntimeException(String filter) {
        super(String.format(MESSAGE, filter));
    }
}
