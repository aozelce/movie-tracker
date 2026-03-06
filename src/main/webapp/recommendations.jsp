<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recommendations for ${user.username}</title>
</head>
<body>
<nav>
    <a href="${pageContext.request.contextPath}/">Home</a> |
    <a href="${pageContext.request.contextPath}/logOut">Logout</a>
</nav>

<h1>Recommendations for ${user.username}</h1>

<c:choose>
    <c:when test="${empty recommendations}">
        <p>You don't have any recommendations yet. Start adding some!</p>
    </c:when>
    <c:otherwise>
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
                    <td>${rec.media != null ? rec.media.title : 'N/A'}</td>
                    <td>${rec.media != null ? rec.media.mediaType : 'N/A'}</td>
                    <td>${rec.source != null ? rec.source.name : 'N/A'}</td>
                    <td>${rec.notes}</td>
                    <td>${rec.watched ? 'Yes' : 'No'}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>