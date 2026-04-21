<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="pageTitle" value="Error"/>
<jsp:include page="/head.jsp"/>

<body>
    <h2>An Error Occurred</h2>
    <p>
        An unexpected error occurred. Please try again later or contact support if the problem persists.
    </p>
    <p><a href="logIn">Return to Login</a></p>
<jsp:include page="/footer.jsp"/>
</body>
</html>
