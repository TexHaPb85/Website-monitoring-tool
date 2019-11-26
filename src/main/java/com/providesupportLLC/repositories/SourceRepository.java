package com.providesupportLLC.repositories;

import com.providesupportLLC.model.MonitoringSource;

import java.util.List;

public interface SourceRepository {
    void saveSource(MonitoringSource source);

    void deleteSource(String sourceUrl);

    void resetMonitoringOfSource(String sourceURL, boolean isMonitoring);

    void updateSource(MonitoringSource updatingSource);

    List<MonitoringSource> getAllSources();
}
