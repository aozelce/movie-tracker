<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Recommendations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light mb-3">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Movie Tracker</a>
        <div>
            <a href="${pageContext.request.contextPath}/tmdbSearch" class="btn btn-outline-primary btn-sm">Search TMDB</a>
            <a href="${pageContext.request.contextPath}/addRecommendation?page=manual" class="btn btn-outline-secondary btn-sm">Add Manually</a>
            <a href="${pageContext.request.contextPath}/logOut" class="btn btn-outline-danger btn-sm">Logout</a>
        </div>
    </div>
</nav>
<div class="container">
    <h2>My Recommendations</h2>
    <c:choose>
        <c:when test="${empty recommendations}">
            <div class="alert alert-info mt-4">No recommendations yet. <a href="${pageContext.request.contextPath}/tmdbSearch">Add one</a>.</div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered table-sm mt-3">
                <thead class="table-light">
                <tr>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Year</th>
                    <th>Genres</th>
                    <th>Source</th>
                    <th>Notes</th>
                    <th>Watched</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rec" items="${recommendations}">
                    <tr>
                        <td>${rec.media != null ? rec.media.title : 'N/A'}</td>
                        <td>${rec.media != null ? rec.media.mediaType : 'N/A'}</td>
                        <td>${rec.media != null && rec.media.year != null ? rec.media.year : ''}</td>
                        <td>${rec.media != null ? rec.media.genres : ''}</td>
                        <td>${rec.source != null ? rec.source.name : ''}</td>
                        <td>${rec.notes}</td>
                        <td>${rec.watched ? 'Yes' : 'No'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>