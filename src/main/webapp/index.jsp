<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<c:set var="pageTitle" value="Movie Tracker"/>
<jsp:include page="/head.jsp"/>

<body class="bg-light min-vh-100 d-flex flex-column">
    <%-- Check for userName in both request attribute (from Auth servlet) and session --%>
    <c:set var="displayName" value="${not empty userName ? userName : sessionScope.user.username}" />

    <!-- Header/Hero Section -->
    <header style="background-color: #004b49;" class="text-white py-5 mb-4 shadow-sm">
        <div class="container text-center">
            <h1 class="display-4 fw-bold mb-2">Movie Tracker</h1>
            <p class="lead mb-0">Track your movie and TV show recommendations with ease.</p>
        </div>
    </header>

    <!-- Main Content -->
    <main class="container flex-grow-1 d-flex flex-column align-items-center justify-content-start">
        <div class="card w-100" style="max-width: 480px;">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty displayName}">
                        <div class="text-center">
                            <p class="mb-4 fs-5">Sign in to start tracking your recommendations!</p>
                            <a href="logIn" class="btn btn-primary btn-lg w-75">Log in</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center">
                            <h2 class="mb-4 fw-bold">Welcome, <span class="text-primary">${displayName}</span>!</h2>
                            <div class="d-grid gap-3">
                                <a class="btn btn-success btn-lg" href="addRecommendation">Add Recommendation</a>
                                <a class="btn btn-info btn-lg text-white" href="recommendations">View Recommendations</a>
                                <a class="btn btn-outline-danger btn-lg" href="logOut">Logout</a>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <footer class="bg-white border-top text-center py-3 mt-4 small text-muted">
        <jsp:include page="/footer.jsp"/>
    </footer>
</body>
</html>
