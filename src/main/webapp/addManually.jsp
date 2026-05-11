<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="Add Movie Manually - Movie Tracker"/>
<jsp:include page="/head.jsp"/>

<body>

<jsp:include page="/nav.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-8 mx-auto">
            <h1 class="mb-4">Add Movie/Show Manually</h1>

            <c:if test="${not empty message}">
                <div class="alert alert-info" role="alert">${message}</div>
            </c:if>

            <c:if test="${not empty searchQuery}">
                <p class="alert alert-warning">
                    <strong>We couldn't find "${searchQuery}" in TMDB.</strong>
                    <br> But don’t worry! You can add it manually using the
                    form below.
                </p>
            </c:if>

            <form method="POST" action="${pageContext.request.contextPath}/addRecommendation">
                <input type="hidden" name="action" value="add-manual">

                <!-- Required Fields Section -->
                <fieldset class="mb-4">
                    <legend class="fs-5 mb-3">Required Information <span class="text-danger">*</span></legend>

                    <div class="mb-3">
                        <label for="title" class="form-label">Movie/Show Title <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="title" name="title"
                               placeholder="e.g., The Matrix">
                    </div>

                    <div class="mb-3">
                        <label for="mediaType" class="form-label">Type <span class="text-danger">*</span></label>
                        <select class="form-select" id="mediaType" name="mediaType">
                            <option value="">-- Select Type --</option>
                            <option value="movie">Movie</option>
                            <option value="tv">TV Show</option>
                        </select>
                    </div>
                </fieldset>

                <!-- Optional Details Section -->
                <fieldset class="mb-4 p-3 bg-light rounded">
                    <legend class="fs-5 mb-3">Optional Details</legend>

                    <div class="mb-3">
                        <label for="year" class="form-label">Release Year</label>
                        <input type="number" class="form-control" id="year" name="year"
                               placeholder="e.g., 1999"
                               min="1800" max="2100">
                    </div>

                    <div class="mb-3">
                        <label for="genres" class="form-label">Genres</label>
                        <input type="text" class="form-control" id="genres" name="genres"
                               placeholder="e.g., Sci-Fi, Action, Drama">
                    </div>

                    <div class="mb-3">
                        <label for="overview" class="form-label">Description</label>
                        <textarea class="form-control" id="overview" name="overview" rows="4"
                                  placeholder="Add any details you remember about this movie or show..."></textarea>
                    </div>

                    <div class="mb-3">
                        <label for="posterPath" class="form-label">Poster URL</label>
                        <input type="text" class="form-control" id="posterPath" name="posterPath"
                               placeholder="https://example.com/poster.jpg">
                    </div>
                </fieldset>

                <!-- Recommendation Details -->
                <fieldset class="mb-4">
                    <legend class="fs-5 mb-3">Recommendation Details</legend>

                    <div class="mb-3">
                        <label for="sourceName" class="form-label">Who recommended it?</label>
                        <input type="text" class="form-control" id="sourceName" name="sourceName"
                               placeholder="e.g., Friend, Movie Review, Reddit, etc."
                               list="existingSources">
                        <%-- Display the existing sources of the User--%>
                        <datalist id="existingSources">
                            <c:forEach var="source" items="${sources}">
                                <option value="${source.name}"></option>
                            </c:forEach>
                        </datalist>
                        <small class="form-text text-muted">Type a new source name or select from suggestions</small>
                    </div>

                    <div class="mb-3">
                        <label for="notes" class="form-label">Notes</label>
                        <textarea class="form-control" id="notes" name="notes" rows="3"
                                  placeholder="Why are you interested in this? What did you hear about it?..."></textarea>
                    </div>

                    <div class="form-check mb-3">
                        <input class="form-check-input" type="checkbox" id="isWatched" name="isWatched">
                        <label class="form-check-label" for="isWatched">Already watched?</label>
                    </div>
                </fieldset>

                <!-- Buttons -->
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <button type="submit" class="btn btn-primary btn-lg">Add Recommendation</button>
                    <button type="reset" class="btn btn-secondary btn-lg">Clear Form</button>
                    <a href="${pageContext.request.contextPath}/recommendations" class="btn btn-outline-secondary btn-lg">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>


