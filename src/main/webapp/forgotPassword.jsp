<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Forgot password"/>
    </jsp:include>

    <body>
    <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
        <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
        <div id="navbar-table" class="collapse navbar-collapse">
            <div class="item-wrapper">
                <a href="logIn.jsp" class="float-right btn btn-primary">&#8592 <fmt:message key="button.back"/></a>
            </div>
            <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                <jsp:param name="command" value="/forgotPassword.jsp"/>
            </jsp:include>
        </div>
    </nav>

    <h1 id="forgotPasswordText"><fmt:message key="forgotPasswordForm.label"/></h1>

    <div id="forgotPasswordDIV">
        <div id="signInForm">
            <form method="post" action="${pageContext.request.contextPath}/controller?action=resetPassword">
                <div class="form-group">
                    <label class="color"><fmt:message key="forgotPasswordForm.text"/></label>
                    <input class="form-control" type="text" name="email"  required title=""
                           pattern="[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*"
                           placeholder="<fmt:message key="registrationForm.email.placeholder"/>"  maxlength="45"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <button type="submit" class="btn btn-primary btn-block"><fmt:message key="forgotPasswordForm.sendEmail.button"/></button>
                <c:if test="${sessionScope.invalid eq true}">
                    <input type="hidden" class="is-invalid">
                    <div class="invalid-feedback">
                        <h6 id="invalidFeedbackId"><fmt:message key="${sessionScope.msg}"/></h6>
                    </div>
                    <c:remove var="invalid" scope="session"/>
                    <c:remove var="msg" scope="session"/>
                </c:if>
                <c:if test="${sessionScope.success eq true}">
                    <input type="hidden" class="is-valid">
                    <div class="valid-feedback">
                        <h6><fmt:message key="forgotPasswordForm.success.label"/></h6>
                    </div>
                    <c:remove var="success" scope="session"/>
                </c:if>

            </form>
        </div>


    </div>
        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>
