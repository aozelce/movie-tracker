<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="Error"/>
<jsp:include page="/head.jsp"/>
<body class="bg-light">
<main>
<div class="container py-5">
    <div class="row">
        <div class="col-lg-6">
            <h2 class="mb-3" style="color:#7a8894;">An Error Occurred</h2>
            <c:choose>
                <c:when test="${not empty requestScope['javax.servlet.error.message']}">
                    <p class="mb-3">${requestScope['javax.servlet.error.message']}</p>
                </c:when>
                <c:otherwise>
                    <p class="mb-3">
                        An unexpected error occurred. Please try again later or contact support if the problem persists.
                    </p>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty requestScope['javax.servlet.error.status_code']}">
                <p class="text-muted"><small>Error code: ${requestScope['javax.servlet.error.status_code']}</small></p>
            </c:if>

            <a href="logIn" class="btn btn-secondary mt-2">Return to Login</a>
        </div>
    </div>
</div>
</main>
<jsp:include page="/footer.jsp"/>
</body>
</html>
