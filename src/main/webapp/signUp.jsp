<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Sign up"/>
    </jsp:include>

    <body>
        <nav class="navbar navbar-expand-lg navbar-light" id="navbar" >
            <a class="navbar-brand" id="navbar-logo"  href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
            <div class="collapse navbar-collapse" id="navbar-table" >
                <div class="item-wrapper">
                    <a href="logIn.jsp" class="float-right btn btn-primary"><fmt:message key="button.signIn"/></a>
                </div>
                <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                    <jsp:param name="command" value="/signUp.jsp"/>
                </jsp:include>
            </div>
        </nav>

        <h1 id="signUpText"><fmt:message key="registrationForm.signUp.label"/></h1>

        <div id="signUpFormDIV">
            <div id="signUpForm">
                <form method="post" action="${pageContext.request.contextPath}/controller?action=signUp">
                    <div class="form-group">
                        <label class="color"><fmt:message key="registrationForm.login.label"/></label>
                        <input type="text" name="login" class="form-control" aria-describedby="loginHelp" required
                               placeholder="<fmt:message key="registrationForm.login.placeholder"/>" maxlength="25"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                        <c:if test="${sessionScope.invalid eq true}">
                            <input type="hidden" class="is-invalid">
                            <div class="invalid-feedback">
                                <h6><fmt:message key="${sessionScope.msg}"/></h6>
                            </div>
                            <c:remove var="invalid" scope="session"/>
                            <c:remove var="msg" scope="session"/>
                        </c:if>
                    </div>

                    <div class="form-group">
                        <label class="color"><fmt:message key="registrationForm.password.label"/></label>
                        <input class="form-control" type="password" name="password"  required title=""
                               placeholder="<fmt:message key="registrationForm.password.placeholder"/>" maxlength="100"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-group">
                        <label class="color"><fmt:message key="registrationForm.name.label"/></label>
                        <input class="form-control" type="text" name="name"  required title=""
                               placeholder="<fmt:message key="registrationForm.name.placeholder"/>" maxlength="25"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-group">
                        <label class="color"><fmt:message key="registrationForm.surname.label"/></label>
                        <input class="form-control" type="text" name="surname"  required title=""
                               placeholder="<fmt:message key="registrationForm.surname.placeholder"/>" maxlength="25"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-group">
                        <label class="color"><fmt:message key="registrationForm.email.label"/></label>
                        <input class="form-control" type="email" name="email"  required title=""
                               placeholder="<fmt:message key="registrationForm.email.placeholder"/>" maxlength="25"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.badEmail"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>
                    <div class="g-recaptcha"
                         data-sitekey="6Lf3vl4kAAAAACIW7IFZyqB5jLw646UmnvXTq6XY">
                    </div>

                    <button class="btn btn-primary" id="signUpButton" type="submit" ><fmt:message key="registrationForm.signUp.button"/></button>
                </form>

            </div>
            <div class="registrTip">
                <span class="registrTipText"><fmt:message key="registrationForm.haveAlreadyAnAccount.label"/></span>
                <a href="logIn.jsp"><fmt:message key="button.signIn"/></a>
            </div>
        </div>

        <script src="https://www.google.com/recaptcha/api.js"></script>
        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>

