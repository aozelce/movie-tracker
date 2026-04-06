<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Recommendation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light mb-3">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Movie Tracker</a>
        <div>
            <a href="${pageContext.request.contextPath}/tmdbSearch" class="btn btn-outline-secondary btn-sm">Back to Search</a>
        </div>
    </div>
</nav>
<div class="container">
    <h2>Confirm Recommendation</h2>
    <form method="POST" action="${pageContext.request.contextPath}/addRecommendation">
        <input type="hidden" name="action" value="select-tmdb">
        <input type="hidden" name="tmdbId" value="${media.tmdbId}">
        <input type="hidden" name="title" value="${media.title}">
        <input type="hidden" name="mediaType" value="${media.mediaType}">
        <input type="hidden" name="year" value="${media.year}">
        <input type="hidden" name="posterPath" value="${media.posterPath}">
        <input type="hidden" name="overview" value="${media.overview}">
        <input type="hidden" name="genres" value="${media.genres}">
        <div class="mb-2">
            <label><strong>Title:</strong> ${media.title}</label>
        </div>
        <div class="mb-2">
            <label><strong>Type:</strong> ${media.mediaType}</label>
        </div>
        <div class="mb-2">
            <label><strong>Year:</strong> ${media.year}</label>
        </div>
        <div class="mb-2">
            <label><strong>Description:</strong> ${media.overview}</label>
        </div>
        <div class="mb-2">
            <label for="sourceName">Who recommended it?</label>
            <input type="text" class="form-control" id="sourceName" name="sourceName" placeholder="e.g., Friend, Reddit">
        </div>
        <div class="mb-2">
            <label for="notes">Notes</label>
            <textarea class="form-control" id="notes" name="notes" rows="2"></textarea>
        </div>
        <div class="form-check mb-2">
            <input class="form-check-input" type="checkbox" id="isWatched" name="isWatched">
            <label class="form-check-label" for="isWatched">Already watched?</label>
        </div>
        <button type="submit" class="btn btn-primary btn-sm">Add Recommendation</button>
    </form>
</div>
</body>
</html>
