package com.providesupportLLC.model;


import com.providesupportLLC.model.enums.Values;
import com.providesupportLLC.utils.NumericUtils;

public class MonitoringSource {
    private String sourceURL;
    private int monitoringPeriod;
    private boolean isMonitoring = true;

    private int expectedHTTPStatusCode;

    private long warningConnectionTime;
    private long criticalConnectionTime;

    private int minContentLength;
    private int maxContentLength;

    public MonitoringSource(String sourceURL, String monitoringPeriod, String expectedHTTPStatusCode,
                            String warningConnectionTime, String criticalConnectionTime,
                            String minContentLength, String maxContentLength) {
        if (!sourceURL.isEmpty()) {
            if (!sourceURL.contains("http"))
                sourceURL = "https://" + sourceURL;
            this.sourceURL = sourceURL;
        }

        int period = 5000;
        period = NumericUtils.parseIfPossible(monitoringPeriod, period);
        this.monitoringPeriod = period;

        int statusCode = 200;
        statusCode = NumericUtils.parseIfPossible(expectedHTTPStatusCode, statusCode);
        this.expectedHTTPStatusCode = statusCode;

        long warnTime = 1000;
        warnTime = NumericUtils.parseIfPossible(warningConnectionTime, warnTime);
        this.warningConnectionTime = warnTime;

        long critTime = 2000;
        critTime = NumericUtils.parseIfPossible(criticalConnectionTime, critTime);
        this.criticalConnectionTime = critTime;

        int minContLength = -1;
        minContLength = NumericUtils.parseIfPossible(minContentLength, minContLength);
        this.minContentLength = minContLength;

        int maxContLength = Integer.MAX_VALUE - 1;
        maxContLength = NumericUtils.parseIfPossible(maxContentLength, maxContLength);
        this.maxContentLength = maxContLength;
    }

    public MonitoringSource(String sourceURL, int monitoringPeriod, boolean isMonitoring,
                            int expectedHTTPStatusCode, long warningConnectionTime, long criticalConnectionTime,
                            int minContentLength, int maxContentLength) {
        this.sourceURL = sourceURL;
        this.monitoringPeriod = monitoringPeriod;
        this.isMonitoring = isMonitoring;
        this.expectedHTTPStatusCode = expectedHTTPStatusCode;
        this.warningConnectionTime = warningConnectionTime;
        this.criticalConnectionTime = criticalConnectionTime;
        this.minContentLength = minContentLength;
        this.maxContentLength = maxContentLength;
    }

    public MonitoringSource(String sourceURL) {
        this.sourceURL = sourceURL;
        this.monitoringPeriod = Values.DEFAULT_MONITORING_PERIOD.value;
        this.expectedHTTPStatusCode = Values.DEFAULT_EXPECTED_HTTP_STATUS_CODE.value;
        this.warningConnectionTime = Values.DEFAULT_WARNING_CONNECTION_TIME.value;
        this.criticalConnectionTime = Values.DEFAULT_CRITICAL_CONNECTION_TIME.value;
        this.minContentLength = Values.DEFAULT_MIN_CONTENT_LENGTH.value;
        this.maxContentLength = Values.DEFAULT_MAX_CONTENT_LENGTH.value;
    }

    public MonitoringSource() {
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public int getMonitoringPeriod() {
        return monitoringPeriod;
    }

    public void setMonitoringPeriod(int monitoringPeriod) {
        this.monitoringPeriod = monitoringPeriod;
    }

    public boolean isMonitoring() {
        return isMonitoring;
    }

    public void setMonitoring(boolean monitoring) {
        isMonitoring = monitoring;
    }

    public int getExpectedHTTPStatusCode() {
        return expectedHTTPStatusCode;
    }

    public void setExpectedHTTPStatusCode(int expectedHTTPStatusCode) {
        this.expectedHTTPStatusCode = expectedHTTPStatusCode;
    }

    public long getWarningConnectionTime() {
        return warningConnectionTime;
    }

    public void setWarningConnectionTime(long warningConnectionTime) {
        this.warningConnectionTime = warningConnectionTime;
    }

    public long getCriticalConnectionTime() {
        return criticalConnectionTime;
    }

    public void setCriticalConnectionTime(long criticalConnectionTime) {
        this.criticalConnectionTime = criticalConnectionTime;
    }

    public int getMinContentLength() {
        return minContentLength;
    }

    public void setMinContentLength(int minContentLength) {
        this.minContentLength = minContentLength;
    }

    public int getMaxContentLength() {
        return maxContentLength;
    }

    public void setMaxContentLength(int maxContentLength) {
        this.maxContentLength = maxContentLength;
    }

    @Override
    public String toString() {
        return "MonitoringSource{" +
                "sourceURL='" + sourceURL + '\'' +
                ", monitoringPeriod=" + monitoringPeriod +
                ", isMonitoring=" + isMonitoring +
                ", expectedHTTPStatusCode=" + expectedHTTPStatusCode +
                ", warningConnectionTime=" + warningConnectionTime +
                ", criticalConnectionTime=" + criticalConnectionTime +
                ", minContentLength=" + minContentLength +
                ", maxContentLength=" + maxContentLength +
                '}';
    }
}
