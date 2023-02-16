<%@ attribute name="img" type="java.lang.String" required="true" %>
<%@ attribute name="styleClass" type="java.lang.String" required="true" %>
<%@ attribute name="onClickViewProfile" type="java.lang.Boolean" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:choose>
    <c:when test="${empty img}">
        <img src="${pageContext.request.contextPath}/img/defaultAvatar.png"
             class="${styleClass}" alt="avatar"
            <c:if test="${onClickViewProfile}">
                onclick="window.location.href='${pageContext.request.contextPath}/controller?action=profile'"
            </c:if>>
    </c:when>
    <c:otherwise>
        <img src="data:image/jpg;base64,${img}"
             <c:if test="${onClickViewProfile eq true}">
             onclick="window.location.href='${pageContext.request.contextPath}/controller?action=profile'"
            </c:if>
             class="${styleClass}" alt="avatar1">
    </c:otherwise>
</c:choose>