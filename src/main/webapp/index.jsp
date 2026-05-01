<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<c:set var="pageTitle" value="Movie Tracker"/>
<jsp:include page="/head.jsp"/>

<body>
<%-- Check for userName in both request attribute (from Auth servlet) and session --%>
<c:set var="displayName" value="${not empty userName ? userName :
sessionScope.user.username}" />

<c:choose>
    <c:when test="${empty displayName}">
        <h2>Movie Tracker</h2>
        <p>Track your movie and TV show recommendations!</p>
        <a href="logIn">Log in</a>
    </c:when>
    <c:otherwise>
        <h2>Welcome, ${displayName}!</h2>
        <nav>
            <a href="addRecommendation">Add Recommendation</a> |
            <a href="recommendations">View Recommendations</a> |
            <a href="logOut">Logout</a>
        </nav>
    </c:otherwise>
</c:choose>
<jsp:include page="/footer.jsp"/>
</body>
</html>
