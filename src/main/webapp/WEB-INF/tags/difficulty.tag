<%@ attribute name="diff" type="com.epam.testing.model.entity.test.TestDifficulty" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:choose>
    <c:when test="${diff.value eq 0}">
        <fmt:message key="testCard.difficulty.easy"/>
    </c:when>
    <c:when test="${diff.value eq 1}">
        <fmt:message key="testCard.difficulty.medium"/>
    </c:when>
    <c:when test="${diff.value eq 2}">
        <fmt:message key="testCard.difficulty.hard"/>
    </c:when>
</c:choose>