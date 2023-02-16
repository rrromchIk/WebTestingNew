<%@ attribute name="status" type="com.epam.testing.model.entity.user.UserStatus" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:choose>
    <c:when test="${status.name eq 'active'}">
        <fmt:message key="userCard.status.active"/>
    </c:when>
    <c:when test="${status.name eq 'blocked'}">
        <fmt:message key="userCard.status.blocked"/>
    </c:when>
</c:choose>