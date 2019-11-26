package com.providesupportLLC.services;

import com.providesupportLLC.model.MonitoringSource;
import com.providesupportLLC.model.MonitoringThreadResponse;
import com.providesupportLLC.repositories.SourceRepository;
import com.providesupportLLC.repositories.SourceRepositoryJDBC;

import java.util.List;
import java.util.stream.Collectors;

public class WebRequestService {
    private static WebRequestService webRequestService;

    private final SourceRepository sourceRepository = SourceRepositoryJDBC.getInstance();
    private List<MonitoringThreadResponse> responses;

    private WebRequestService() {
        refresh();
    }

    public static WebRequestService getInstance() {
        if (webRequestService == null) {
            webRequestService = new WebRequestService();
        }
        return webRequestService;
    }

    public void refresh() {
        this.responses = sourceRepository
                .getAllSources()
                .parallelStream()
                .map(MonitoringThreadResponse::new)
                .collect(Collectors.toList());
        this.responses.forEach(Thread::start);
    }

    public void updateSource(MonitoringSource updatingSource) {
        System.out.println("updating source:" + updatingSource.toString() + " from service");
        sourceRepository.updateSource(updatingSource);
    }

    public void resetMonitoring(String sourceUrl) {
        MonitoringThreadResponse response = responses
                .stream()
                .filter(res -> res.getUrl().equals(sourceUrl))
                .findFirst()
                .orElseThrow(NullPointerException::new);

        boolean newValue = !response.getSource().isMonitoring();
        sourceRepository.resetMonitoringOfSource(sourceUrl, newValue);
    }

    public void deleteSource(String url) {
        sourceRepository.deleteSource(url);
    }

    public List<MonitoringSource> getSources() {
        return sourceRepository.getAllSources();
    }

    public List<MonitoringThreadResponse> getMonitorinResponses() {
        return responses;
    }

    public void saveSource(MonitoringSource source) {
        sourceRepository.saveSource(source);
    }
}
