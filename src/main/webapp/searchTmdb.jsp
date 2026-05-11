<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="Search TMDB"/>
<jsp:include page="/head.jsp"/>

<body>
<main>
<jsp:include page="/nav.jsp"/>
<div class="container">
    <h2>Search for a Movie or TV Show</h2>
    <form method="POST" action="${pageContext.request.contextPath}/addRecommendation" class="mb-3">
        <input type="hidden" name="action" value="search-tmdb" />
        <div class="mb-2">
            <input type="text" class="form-control" name="query" placeholder="Enter movie or show title">
        </div>
        <button type="submit" class="btn btn-primary btn-sm">Search</button>
        <a href="${pageContext.request.contextPath}/addRecommendation?page=manual" class="btn btn-outline-secondary btn-sm">Add Manually</a>
    </form>
    <c:if test="${not empty message}">
        <div class="alert alert-warning">${message}</div>
    </c:if>
    <div class="text-muted mt-3">Data provided by <a href="https://www.themoviedb.org/" target="_blank">TMDB</a>.</div>
</div>
</main>
<jsp:include page="/footer.jsp"/>
</body>
</html>
