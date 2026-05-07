<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="TMDB Search Results"/>
<jsp:include page="/head.jsp"/>

<body>
<nav class="navbar navbar-light bg-light mb-3">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Movie Tracker</a>
        <div>
            <a href="${pageContext.request.contextPath}/addRecommendation" class="btn btn-outline-secondary btn-sm">New Search</a>
            <a href="${pageContext.request.contextPath}/addRecommendation?page=manual" class="btn btn-outline-secondary btn-sm">Add Manually</a>
        </div>
    </div>
</nav>
<div class="container">
    <h2>Results for "${searchQuery}"</h2>
    <c:choose>
        <c:when test="${empty results}">
            <div class="alert alert-warning mt-4">No results found. <a href="${pageContext.request.contextPath}/addRecommendation?page=manual">Add manually</a>.</div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered table-sm mt-3">
                <thead class="table-light">
                <tr>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Year</th>
                    <th>Description</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="result" items="${results}">
                    <tr>
                        <td>${not empty result.title ? result.title : result.name}</td>
                        <td>${not empty result.mediaType ? result.mediaType : 'movie'}</td>
                        <%--Display different properties based on whether its a movie or tv show--%>
                        <td>
                            <c:choose>
                                <c:when test="${result.mediaType eq 'tv'}">
                                    <c:out value="${not empty result.firstAirDate && result.firstAirDate.length() >= 4 ? result.firstAirDate.substring(0,4) : ''}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${not empty result.releaseDate && result.releaseDate.length() >= 4 ? result.releaseDate.substring(0,4) : ''}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${result.overview}</td>
                        <td>
                            <form method="POST" action="${pageContext.request.contextPath}/addRecommendation">
                                <input type="hidden" name="action" value="select-tmdb">
                                <input type="hidden" name="tmdbId" value="${result.id}">
                                <button type="submit" class="btn btn-primary btn-sm">Select</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
