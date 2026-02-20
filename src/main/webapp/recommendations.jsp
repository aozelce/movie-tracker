<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recommendations for ${user.username}</title>
</head>
<body>
<h1>Recommendations for ${user.username}</h1>

<table border="1">
    <tr>
        <th>Media</th>
        <th>Type</th>
        <th>Source</th>
        <th>Notes</th>
        <th>Watched</th>
    </tr>
    <c:forEach var="rec" items="${recommendations}">
        <tr>
            <td>${rec.media.title}</td>
            <td>${rec.media.mediaType}</td>
            <td>${rec.source.name}</td>
            <td>${rec.notes}</td>
            <td>${rec.watched ? 'Yes' : 'No'}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>