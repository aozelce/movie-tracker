<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="My Recommendations"/>
<jsp:include page="/head.jsp"/>

<body>
<nav class="navbar navbar-light bg-light mb-3">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Movie Tracker</a>
        <div>
            <a href="${pageContext.request.contextPath}/addRecommendation" class="btn btn-outline-primary btn-sm">Search TMDB</a>
            <a href="${pageContext.request.contextPath}/addRecommendation?page=manual" class="btn btn-outline-secondary btn-sm">Add Manually</a>
            <a href="${pageContext.request.contextPath}/logOut" class="btn btn-outline-danger btn-sm">Logout</a>
        </div>
    </div>
</nav>
<div class="container">
    <h2>My Recommendations</h2>
    <c:choose>
        <c:when test="${empty recommendations}">
            <div class="alert alert-info mt-4">No recommendations yet. <a
                    href="${pageContext.request.contextPath}/addRecommendation">Add one</a>.
            </div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mt-3">
                <c:forEach var="rec" items="${recommendations}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <%-- Use TMDB base URL for poster images. --%>
                            <c:if test="${rec.media != null && rec.media.posterPath != null && rec.media.posterPath != ''}">
                                <img src="https://image.tmdb.org/t/p/w500${rec.media.posterPath}" class="card-img-top" alt="Movie Poster" style="object-fit:cover; height:320px;">
                            </c:if>
                            <c:if test="${rec.media == null || rec.media.posterPath == null || rec.media.posterPath == ''}">
                                <svg class="bd-placeholder-img card-img-top" width="100%" height="320" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="No poster" preserveAspectRatio="xMidYMid slice" focusable="false"><title>No poster</title><rect width="100%" height="100%" fill="#e9ecef"/><text x="50%" y="50%" fill="#adb5bd" dy=".3em" text-anchor="middle">No Image</text></svg>
                            </c:if>
                            <div class="card-body">
                                <h5 class="card-title mb-1">${rec.media != null ? rec.media.title : 'N/A'}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${rec.media != null ? rec.media.mediaType : 'N/A'}
                                    <c:if test="${rec.media != null && rec.media.year != null}">(${rec.media.year})</c:if>
                                </h6>
                                <div class="mb-2">
                                    <span class="badge bg-secondary">${rec.media != null ? rec.media.genres : ''}</span>
                                </div>
                                <p class="mb-1"><strong>Source:</strong> ${rec.source != null ? rec.source.name : ''}</p>
                                <p class="mb-1"><strong>Notes:</strong> ${rec.notes}</p>
                                <p class="mb-2"><strong>Watched:</strong> <span class="${rec.watched ? 'text-success' : 'text-danger'}">${rec.watched ? 'Yes' : 'No'}</span></p>
                            </div>
                            <div class="card-footer bg-white border-0 d-flex justify-content-between gap-2">
                                <a href="${pageContext.request.contextPath}/editRecommendation?id=${rec.id}"
                                   class="btn btn-warning btn-sm flex-fill">Edit</a>
                                <a href="${pageContext.request.contextPath}/deleteRecommendation?id=${rec.id}"
                                   class="btn btn-danger btn-sm flex-fill"
                                   onclick="return confirm('Are you sure you want to delete this recommendation?');">
                                    Delete
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>