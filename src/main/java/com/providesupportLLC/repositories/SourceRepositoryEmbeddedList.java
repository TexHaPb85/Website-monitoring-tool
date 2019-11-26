package com.providesupportLLC.repositories;

import com.providesupportLLC.model.MonitoringSource;

import java.util.ArrayList;
import java.util.List;

public class SourceRepositoryEmbeddedList implements SourceRepository {

    private List<MonitoringSource> sources;

    public SourceRepositoryEmbeddedList() {
        this.sources = new ArrayList<>();
        sources.add(new MonitoringSource("http://google.com"));
        sources.add(new MonitoringSource("https://devcolibri.com/"));
        sources.add(new MonitoringSource("https://stackoverflow.com/"));
        sources.add(new MonitoringSource("https://shop.bukovel.info/"));
    }

    @Override
    public void saveSource(MonitoringSource source) {
        sources.add(source);
    }

    @Override
    public void deleteSource(String sourceUrl) {
        sources.removeIf(source -> source.getSourceURL().equals(sourceUrl));
    }

    @Override
    public void resetMonitoringOfSource(String sourceURL, boolean isMonitoring) {
        sources
                .stream()
                .filter(source -> sourceURL.equals(source.getSourceURL()))
                .forEach(source -> source.setMonitoring(isMonitoring));

    }

    @Override
    public void updateSource(MonitoringSource updatingSource) {
        sources
                .stream()
                .filter(source -> updatingSource.getSourceURL().equals(source.getSourceURL()))
                .forEach(source -> source = updatingSource);
    }

    @Override
    public List<MonitoringSource> getAllSources() {
        return sources;
    }
}
