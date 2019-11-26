<%@ page import="com.providesupportLLC.model.enums.Values" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Website Monitoring Tool</title>
    <style>
        p {
            margin-top: 1px; /* Отступ сверху */
            margin-bottom: 0px; /* Отступ снизу */
        }
        table {
            width: 100%; /* Ширина таблицы */
            background: #cfd7ff; /* Цвет фона таблицы */
            color: #000000; /* Цвет текста */
            border-spacing: 1px; /* Расстояние между ячейками */
        }
        td, th {
            background: #9fd4e0; /* Цвет фона ячеек */
            padding: 5px; /* Поля вокруг текста */
        }
    </style>
</head>
<body>
    <table>
        <tr>
            <th>URL</th>
            <th>HTTP Status Code</th>
            <th>Connection Time</th>
            <th>Content Length</th>
            <th>Monitoring State</th>
            <th>Thread State</th>
            <th>Additional info</th>
        </tr>
        <tbody>
            <c:forEach var="res" items="${resultList}">
                <tr>
                    <td><a href=${res.url}>${res.url}</a></td>
                    <td>${res.HTTPStatusCode}</td>
                    <td>${res.connectionTime}</td>
                    <td>${res.contentLength}</td>
                    <td>${res.getMonitoringState()}</td>
                    <td>${res.getState()}</td>
                    <td>${res.info}</td>
                    <%--<td>
                        <a class="btn btn-primary btn-sm" href='<c:url value="?action=edit&url=${res.url}" />'>Edit</a>
                    </td>--%>
                    <td>
                        <a class="btn btn-primary btn-sm" href='<c:url value="?action=delete&url=${res.url}" />'>Delete</a>
                    </td>
                    <%--<audio src="audio/warning.mp3"></audio>--%>
                    <td>
                        <a class="btn btn-primary btn-sm" href='<c:url value="?action=setMonitoring&url=${res.url}" />'>
                            <c:if test="${res.getSource().isMonitoring()}">Stop monitoring</c:if>
                            <c:if test="${!res.getSource().isMonitoring()}">Continue Monitoring</c:if>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
     </table>

    <form method = "post">
        <p>URL: <input type = "text" placeholder="https://site-for-monitoring.com" size="40" name = "url"></p>
        <p>Monitoring period: <input type = "text" placeholder=<%=Values.DEFAULT_MONITORING_PERIOD.value%> size="8" name = "period" /></p>
        <p>Expected HTTP status <input type = "text" placeholder=<%=Values.DEFAULT_EXPECTED_HTTP_STATUS_CODE.value%> size="5" name = "statusCode" /></p>
        <p>Warning connection time <input type = "text" placeholder=<%=Values.DEFAULT_WARNING_CONNECTION_TIME.value%> size="5" name = "wTime" /></p>
        <p>Critical connection time <input type = "text" placeholder=<%=Values.DEFAULT_CRITICAL_CONNECTION_TIME.value%> size="5" name = "cTime" /></p>
        <p>Min content length in bytes <input type = "text" placeholder=<%=Values.DEFAULT_MIN_CONTENT_LENGTH.value%> size="10" name = "minContent" /></p>
        <p>Max content length in bytes <input type = "text" placeholder=<%=Values.DEFAULT_MAX_CONTENT_LENGTH.value%> size="10" name = "maxContent" /></p>
        <input type = "submit" value="start monitoring"/>
    </form>

</body>
</html>