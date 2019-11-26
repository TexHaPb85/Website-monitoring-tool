package com.providesupportLLC.repositories;

import com.providesupportLLC.model.MonitoringSource;
import com.providesupportLLC.utils.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SourceRepositoryJDBC implements SourceRepository {
    private static SourceRepositoryJDBC repositoryJDBC;
    private Connection connection;

    private SourceRepositoryJDBC() {
        connection = DBConnectionFactory.getConnection();
    }

    public static SourceRepositoryJDBC getInstance() {
        if (repositoryJDBC == null) {
            repositoryJDBC = new SourceRepositoryJDBC();
        }
        return repositoryJDBC;
    }

    @Override
    public void saveSource(MonitoringSource source) {
        try {
            String saveStatement = "INSERT INTO \"monitoring_tool;\".monitoring_sources " +
                    "(source_url, monitoring_period, is_monitoring, expected_http_status_code, warning_connection_time, " +
                    "critical_connection_time, min_content_length, max_content_length) " +
                    "Values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(saveStatement);
            preparedStatement.setString(1, source.getSourceURL());
            preparedStatement.setInt(2, source.getMonitoringPeriod());
            preparedStatement.setBoolean(3, source.isMonitoring());
            preparedStatement.setInt(4, source.getExpectedHTTPStatusCode());
            preparedStatement.setLong(5, source.getWarningConnectionTime());
            preparedStatement.setLong(6, source.getCriticalConnectionTime());
            preparedStatement.setInt(7, source.getMinContentLength());
            preparedStatement.setInt(8, source.getMaxContentLength());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteSource(String sourceUrl) {
        try {
            String deleteStatement = "DELETE FROM \"monitoring_tool;\".monitoring_sources WHERE source_url = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setString(1, sourceUrl);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void resetMonitoringOfSource(String sourceURL, boolean isMonitoring) {
        try {
            String resetBoolStatement = "UPDATE \"monitoring_tool;\".monitoring_sources SET is_monitoring=? WHERE source_url = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(resetBoolStatement);
            preparedStatement.setBoolean(1, isMonitoring);
            preparedStatement.setString(2, sourceURL);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateSource(MonitoringSource updatingSource) {
        try {
            String statement = "UPDATE \"monitoring_tool;\".monitoring_sources SET " +
                    "monitoring_period = ?, " +
                    "expected_http_status_code = ?," +
                    "warning_connection_time = ?," +
                    "critical_connection_time = ?," +
                    "min_content_length = ?," +
                    "max_content_length = ?" +
                    " WHERE source_url = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, updatingSource.getMonitoringPeriod());
            preparedStatement.setInt(2, updatingSource.getExpectedHTTPStatusCode());
            preparedStatement.setLong(3, updatingSource.getWarningConnectionTime());
            preparedStatement.setLong(4, updatingSource.getCriticalConnectionTime());
            preparedStatement.setInt(5, updatingSource.getMinContentLength());
            preparedStatement.setInt(6, updatingSource.getMaxContentLength());
            preparedStatement.setString(7, updatingSource.getSourceURL());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<MonitoringSource> getAllSources() {
        ArrayList<MonitoringSource> tasks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"monitoring_tool;\".monitoring_sources");
            while (resultSet.next()) {
                String url = resultSet.getString("source_url");
                int requestFrequency = resultSet.getInt("monitoring_period");
                boolean isMonitoring = resultSet.getBoolean("is_monitoring");
                int expectedHTTP = resultSet.getInt("expected_http_status_code");
                long warningTime = resultSet.getLong("warning_connection_time");
                long criticalTime = resultSet.getLong("critical_connection_time");
                int minLength = resultSet.getInt("min_content_length");
                int maxLength = resultSet.getInt("max_content_length");

                MonitoringSource source = new MonitoringSource(url, requestFrequency, isMonitoring, expectedHTTP, warningTime, criticalTime, minLength, maxLength);
                tasks.add(source);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tasks;
    }
}
