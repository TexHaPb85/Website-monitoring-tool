package com.providesupportLLC.controllers;

import com.providesupportLLC.model.MonitoringSource;
import com.providesupportLLC.services.WebRequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    private final WebRequestService service = WebRequestService.getInstance();
    private Properties properties;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //response.setIntHeader("Refresh", 1);
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("edit")) {
            editSource(request, response);
        } else if (action != null && action.equalsIgnoreCase("delete")) {
            deleteSource(request, response);
        } else if (action != null && action.equalsIgnoreCase("setMonitoring")) {
            resetMonitoring(request, response);
        } else {
            showSourceList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        saveSource(request);
        response.sendRedirect(properties.getProperty("main_url"));
    }


    @Override
    public void init() throws ServletException {
        properties = new Properties();
        try {
            InputStream inputStream = MainServlet.class.getClassLoader().getResourceAsStream("application.properties");
            assert inputStream != null;
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }

    private void editSource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MonitoringSource source = readAndParseSource(request);
        System.out.println("edition of source:" + source.toString());
        service.updateSource(source);
        service.refresh();
        response.sendRedirect(request.getContextPath());
    }

    private void resetMonitoring(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = request.getParameter("url");
            service.resetMonitoring(url);
            response.sendRedirect(request.getContextPath());
            service.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSourceList(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("resultList", service.getMonitorinResponses());
            request.getRequestDispatcher("mainpage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSource(HttpServletRequest request, HttpServletResponse response) {
        try {
            String sourceUrl = request.getParameter("url");
            service.deleteSource(sourceUrl);
            response.sendRedirect(properties.getProperty("main_url"));
            service.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSource(HttpServletRequest request) {
        MonitoringSource savingSource = readAndParseSource(request);

        service.saveSource(savingSource);
        service.refresh();
    }

    private MonitoringSource readAndParseSource(HttpServletRequest request) {
        String url = request.getParameter("url");
        String periodStr = request.getParameter("period");
        String statusStr = request.getParameter("statusCode");
        String warnTimeStr = request.getParameter("wTime");
        String critTimeStr = request.getParameter("cTime");
        String minContLengthStr = request.getParameter("minContent");
        String maxContLengthStr = request.getParameter("maxContent");

        System.out.println(minContLengthStr);

        return new MonitoringSource(url, periodStr, statusStr, warnTimeStr,
                critTimeStr, minContLengthStr, maxContLengthStr);
    }
}
