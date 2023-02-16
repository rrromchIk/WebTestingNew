<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="User info"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
            <div id="navbar-table" class="collapse navbar-collapse">
                <div class="item-wrapper">
                    <a href="${pageContext.request.contextPath}/controller?action=adminMain&tab=users&page=${sessionScope.activePage}"
                       class="float-right btn btn-outline-primary">&#8592 <fmt:message key="button.back"/></a>
                </div>
                <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                    <jsp:param name="command"
                               value="/controller?action=userInfo&userId=${requestScope.fullUser.id}"/>
                </jsp:include>
            </div>
        </nav>

        <h1 style="color: silver"><fmt:message key="userInfo.label"/>: ${requestScope.fullUser.login}</h1>

        <div id="profileForm">
            <form method="post" action="${pageContext.request.contextPath}/controller?action=editUser&userId=${requestScope.fullUser.id}">
                <input type="hidden" name="login" value="${requestScope.fullUser.login}">
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.login.label"/></label>
                    <input class="form-control" type="text" value="${requestScope.fullUser.login}" readonly
                           required maxlength="25">
                </div>
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.name.label"/></label>
                    <input class="form-control" name="name" type="text" value="${requestScope.fullUser.name}" readonly
                           placeholder="<fmt:message key="registrationForm.name.placeholder"/>" required maxlength="25"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.surname.label"/></label>
                    <input class="form-control" name="surname" type="text" value="${requestScope.fullUser.surname}" readonly
                           placeholder="<fmt:message key="registrationForm.surname.placeholder"/>" required maxlength="25"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.email.label"/></label>
                    <input class="form-control" name="email" type="email" value="${requestScope.fullUser.email}" readonly
                           placeholder="<fmt:message key="registrationForm.email.placeholder"/>" required maxlength="25"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.badEmail"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <button id="edit-profile-btn" type="button" class="btn btn-primary"><fmt:message key="button.edit"/></button>
                <button id="submit-changes-btn" disabled="true" type="submit" class="btn btn-primary"><fmt:message key="button.submitChanges"/></button>
            </form>
        </div>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>