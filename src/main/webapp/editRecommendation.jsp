<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="Edit Recommendation - Movie Tracker"/>
<jsp:include page="/head.jsp"/>
<body>
<jsp:include page="/nav.jsp"/>
<main>
<div class="container">
    <div class="row">
        <div class="col-md-10 mx-auto">
            <h1 class="mb-4">Edit Recommendation</h1>
            <form method="POST" action="${pageContext.request.contextPath}/editRecommendation">
                <input type="hidden" name="id" value="${recommendation.id}" />
                <fieldset class="mb-4 p-3 bg-light rounded">
                    <legend class="fs-5 mb-3">Media Details</legend>
                    <div class="row">
                        <div class="col-md-3">
                            <c:choose>
                                <c:when test="${not empty recommendation.media.posterPath}">
                                    <img src="https://image.tmdb.org/t/p/w300${recommendation.media.posterPath}"
                                         class="img-fluid rounded" alt="${recommendation.media.title}">
                                </c:when>
                                <c:otherwise>
                                    <div class="bg-secondary text-white rounded d-flex align-items-center justify-content-center"
                                         style="height: 300px;">
                                        <span>No Poster</span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-md-9">
                            <h3>${recommendation.media.title}</h3>
                            <p class="text-muted">
                                <span class="badge bg-secondary">${recommendation.media.mediaType}</span>
                                <c:if test="${not empty recommendation.media.year}">
                                    <span class="ms-2">${recommendation.media.year}</span>
                                </c:if>
                            </p>
                            <c:if test="${not empty recommendation.media.overview}">
                                <p><strong>Description:</strong></p>
                                <p>${recommendation.media.overview}</p>
                            </c:if>
                            <c:if test="${not empty recommendation.media.genres}">
                                <p><strong>Genres:</strong> ${recommendation.media.genres}</p>
                            </c:if>
                        </div>
                    </div>
                </fieldset>
                <fieldset class="mb-4">
                    <legend class="fs-5 mb-3">Recommendation Details</legend>
                    <div class="mb-3">
                        <label for="sourceName" class="form-label">Who recommended it?</label>
                        <input type="text" class="form-control" id="sourceName" name="sourceName"
                               placeholder="e.g., Friend, Movie Review, Reddit, etc."
                               list="existingSources"
                               value="${recommendation.source != null ? recommendation.source.name : ''}" />
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
                                  placeholder="Why are you interested in this? What did you hear about it?...">${recommendation.notes}</textarea>
                    </div>
                    <div class="form-check mb-3">
                        <input class="form-check-input" type="checkbox" id="isWatched" name="isWatched"
                               <c:if test="${recommendation.watched}">checked</c:if> />
                        <label class="form-check-label" for="isWatched">Already watched?</label>
                    </div>
                </fieldset>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <button type="submit" class="btn btn-primary btn-lg">Save Changes</button>
                    <a href="${pageContext.request.contextPath}/recommendations" class="btn btn-outline-secondary btn-lg">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
</main>
<jsp:include page="/footer.jsp"/>
</body>
</html>

