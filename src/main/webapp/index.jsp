<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Testing Patform"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand">TestPortal</a>

            <div id="navbar-table" class="collapse navbar-collapse">
                <div class="item-wrapper">
                    <a href="logIn.jsp" class="float-right btn btn-primary"><fmt:message key="button.signIn"/> </a>
                </div>
                <div class="item-wrapper">
                    <a href="signUp.jsp" class="float-right btn btn-outline-primary"><fmt:message key="button.signUp"/> </a>
                </div>
                <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                    <jsp:param name="command" value="/index.jsp"/>
                </jsp:include>
            </div>
        </nav>
        <div id="main-content">
            <h1><fmt:message key="mainPage.content"/> </h1>
        </div>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>