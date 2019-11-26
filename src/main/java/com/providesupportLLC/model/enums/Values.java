package com.providesupportLLC.model.enums;

public enum Values {
    MIN_RESPONSE_LENGTH(1),
    MAX_RESPONSE_LENGTH(Integer.MAX_VALUE - 1),

    DEFAULT_MONITORING_PERIOD(5000),
    DEFAULT_EXPECTED_HTTP_STATUS_CODE(200),
    DEFAULT_WARNING_CONNECTION_TIME(2000),
    DEFAULT_CRITICAL_CONNECTION_TIME(5000),
    DEFAULT_MIN_CONTENT_LENGTH(-1),
    DEFAULT_MAX_CONTENT_LENGTH(Integer.MAX_VALUE - 1),

    MAX_READ_TIMEOUT(3000);

    public int value;

    Values(int value) {
        this.value = value;
    }

}
