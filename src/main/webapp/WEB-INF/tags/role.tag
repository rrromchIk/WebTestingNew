<%@ attribute name="role" type="com.epam.testing.model.entity.user.UserRole" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:choose>
    <c:when test="${role.name eq 'client'}">
        <fmt:message key="userCard.role.client"/>
    </c:when>
    <c:when test="${role.name eq 'admin'}">
        <fmt:message key="userCard.role.admin"/>
    </c:when>
</c:choose>