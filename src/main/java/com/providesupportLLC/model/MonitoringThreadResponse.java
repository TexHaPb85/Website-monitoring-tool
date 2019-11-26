package com.providesupportLLC.model;


import com.providesupportLLC.model.enums.URLState;
import com.providesupportLLC.model.enums.Values;

import java.io.IOException;
import java.net.*;

public class MonitoringThreadResponse extends Thread {
    private String url;
    private int HTTPStatusCode;
    private long connectionTime = -1;
    private int contentLength = -1;
    private URLState monitoringState = URLState.OK;
    private MonitoringSource source;

    private String info;

    public MonitoringThreadResponse(String url, int HTTPStatusCode, long connectionTime, int contentLength, URLState monitoringState) {
        this.url = url;
        this.HTTPStatusCode = HTTPStatusCode;
        this.connectionTime = connectionTime;
        this.contentLength = contentLength;
        this.monitoringState = monitoringState;
        this.info = "";
    }

    public MonitoringThreadResponse(MonitoringSource monitoringSource) {
        this.url = monitoringSource.getSourceURL();
        this.source = monitoringSource;
        this.info = "";
    }

    @Override
    public void run() {
        while (source.isMonitoring()) {
            try {
                resetData();
                Thread.sleep(source.getMonitoringPeriod());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetData() {
        try {
            long startConnectionTine = System.currentTimeMillis();
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

            connection.setConnectTimeout(Values.DEFAULT_CRITICAL_CONNECTION_TIME.value);
            connection.setReadTimeout(Values.MAX_READ_TIMEOUT.value);
            connection.getInputStream();

            long finishConnectionTime = System.currentTimeMillis();

            this.setConnectionTime(finishConnectionTime - startConnectionTine);
            this.setHTTPStatusCode(connection.getResponseCode());
            this.setContentLength(connection.getContentLength()); //-1 if the content length is not known, or if the content length is greater than Integer.MAX_VALUE.
            stateControl();
            connection.disconnect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            if (!info.contains("UnknownHostException")) {
                info += "UnknownHostException, ";
            }
            this.source.setMonitoring(false);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            if (!info.contains("SocketTimeoutException")) {
                info += "SocketTimeoutException, ";
            }
            this.monitoringState = URLState.CRITICAL;
        } catch (ConnectException e) {
            e.printStackTrace();
            if (!info.contains("ConnectException")) {
                info += "ConnectException, ";
            }
            this.monitoringState = URLState.CRITICAL;
        } catch (IOException e) {
            e.printStackTrace();
            if (!info.contains("IOException")) {
                info += "IOException, ";
            }
            this.monitoringState = URLState.CRITICAL;
        }
    }

    private void stateControl() {
        setMonitoringState(URLState.OK);
        info = "";
        if (contentLength < source.getMinContentLength() || contentLength > source.getMaxContentLength()) {
            monitoringState = URLState.CRITICAL;
            info += "bad content length, ";
        } else if (connectionTime > source.getWarningConnectionTime()) {
            if (connectionTime > source.getCriticalConnectionTime()) {
                info += "critical connection time, ";
                setMonitoringState(URLState.CRITICAL);
            } else {
                info += "warning connection time, ";
                setMonitoringState(URLState.WARNING);
            }
        } else if (HTTPStatusCode != source.getExpectedHTTPStatusCode()) {
            info += "bad HTTP status code";
            monitoringState = URLState.CRITICAL;
        }
    }

    public int getHTTPStatusCode() {
        return HTTPStatusCode;
    }

    public void setHTTPStatusCode(int HTTPStatusCode) {
        this.HTTPStatusCode = HTTPStatusCode;
    }

    public long getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(long connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public URLState getMonitoringState() {
        return monitoringState;
    }

    public void setMonitoringState(URLState monitoringState) {
        this.monitoringState = monitoringState;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MonitoringSource getSource() {
        return source;
    }

    public void setSource(MonitoringSource source) {
        this.source = source;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
