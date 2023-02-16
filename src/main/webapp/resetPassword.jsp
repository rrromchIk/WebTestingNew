<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Change password"/>
    </jsp:include>

    <body>
    <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
        <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
        <div id="navbar-table" class="collapse navbar-collapse">
            <div class="item-wrapper">
                <a href="logIn.jsp" class="float-right btn btn-primary">&#8592 <fmt:message key="button.back"/></a>
            </div>
            <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                <jsp:param name="command" value="/resetPassword.jsp?token=${param.token}"/>
            </jsp:include>
        </div>
    </nav>
        <h1 id="forgotPasswordText"><fmt:message key="resetPassword.label"/></h1>
        <div id="forgotPasswordDIV">
            <div id="signInForm">
                <form method="post"
                      action="${pageContext.request.contextPath}/controller?action=updatePassword&token=${param.token}">
                    <div class="form-group">
                        <label class="color"><fmt:message key="resetPassword.newPassword.label"/></label>
                        <input id="newPassword" class="form-control" type="password" name="newPassword" required
                               placeholder="<fmt:message key="registrationForm.password.placeholder"/>" maxlength="25"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>
                    <div class="form-group">
                        <label class="color"><fmt:message key="resetPassword.confirmPassword.label"/></label>
                        <input id="confirmPassword" class="form-control" type="password" required
                               placeholder="<fmt:message key="registrationForm.password.placeholder"/>" maxlength="25"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>
                    <input type="hidden" class="is-invalid">
                    <div hidden id="feedBack" class="invalid-feedback">
                        <h6>Passwords not equals</h6>
                    </div>

                    <button disabled id="resetButton" type="submit" class="btn btn-primary btn-block"><fmt:message key="registrationForm.reset.button"/></button>
                    <c:if test="${sessionScope.invalid eq true}">
                        <input type="hidden" class="is-invalid">
                        <div class="invalid-feedback">
                            <h6 id="invalidFeedbackId"></h6>
                        </div>
                        <c:remove var="invalid" scope="session"/>
                    </c:if>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/reset-password.js"></script>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>
