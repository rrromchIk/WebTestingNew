<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Admin-main"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
            <div id="navbar-table" class="collapse navbar-collapse">
                <div>
                    <a href="${pageContext.request.contextPath}/controller?action=addTest"
                       class="float-right btn btn-success"><fmt:message key="button.addTest"/></a>
                </div>
                <div class="item-wrapper">
                    <a href="${pageContext.request.contextPath}/controller?action=logOut"
                       class="float-right btn btn-outline-danger"><fmt:message key="button.logOut"/></a>
                </div>
                <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                    <jsp:param name="command"
                               value="/controller?action=adminMain&tab=${requestScope.activeTab}&page=${sessionScope.activePage}"/>
                </jsp:include>
            </div>
        </nav>

        <div id="choice">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a id="tests-choice"
                       class="nav-link <c:if test="${requestScope.activeTab eq 'tests'}">active</c:if>"
                       href="${pageContext.request.contextPath}/controller?action=adminMain&tab=tests"><fmt:message key="tab.tests"/></a>
                </li>
                <li class="nav-item">
                    <a id="users-choice" class="nav-link <c:if test="${requestScope.activeTab eq 'users'}">active</c:if>"
                       href="${pageContext.request.contextPath}/controller?action=adminMain&tab=users"><fmt:message key="tab.users"/></a>
                </li>
            </ul>
        </div>

        <div id="tests">
            <c:forEach var="test" items="${requestScope.tests}">
                <div id="test-item" class="card">
                    <h5 class="card-header">${test.name}</h5>
                    <div class="card-body">
                        <h3 class="card-title">${test.subject}</h3>
                        <p class="card-text"><span class="spanName"><fmt:message key="testCard.difficulty.label"/>: </span>
                            <df:difficulty diff="${test.difficulty}"/>
                        </p>
                        <p class="card-text"><span class="spanName"><fmt:message key="testCard.duration.label"/>: </span>
                                ${test.duration} <fmt:message key="testCard.minutes"/>
                        </p>
                        <p class="card-text"><span class="spanName"><fmt:message key="testCard.numOfQuest.label"/>: </span>
                                ${test.numberOfQuestions}
                        </p>
                        <a href="${pageContext.request.contextPath}/controller?action=testInfo&testId=${test.id}"
                           class="btn btn-primary"><fmt:message key="button.editTest"/></a>
                        <a href="${pageContext.request.contextPath}/controller?action=deleteTest&testId=${test.id}"
                           class="btn btn-danger"><fmt:message key="button.delete"/></a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div id="users">
            <c:forEach var="user" items="${requestScope.users}">
                <div id="test-item" class="card">
                    <h5 class="card-header"><fmt:message key="registrationForm.login.label"/> : ${user.login}</h5>
                    <div class="card-body">
                        <p class="card-text">
                            <span class="spanName">
                                <fmt:message key="registrationForm.name.label"/>:
                            </span>
                                ${user.name}
                        </p>
                        <p class="card-text">
                            <span class="spanName">
                                <fmt:message key="registrationForm.surname.label"/>:
                            </span>
                                ${user.surname}
                        </p>
                        <p class="card-text">
                            <span class="spanName">
                                <fmt:message key="registrationForm.email.label"/>:
                            </span>
                                ${user.email}
                        </p>
                        <p class="card-text">
                            <span class="spanName">
                                <fmt:message key="userCard.role.label"/>:
                            </span>
                            <rl:role role="${user.role}"/>
                        </p>
                        <p class="card-text">
                            <span class="spanName">
                                <fmt:message key="userCard.status.label"/>:
                            </span>
                            <st:status status="${user.status}"/>
                        </p>
                        <a href="${pageContext.request.contextPath}/controller?action=userInfo&userId=${user.id}" class="btn btn-primary"><fmt:message key="userCard.editUser.button"/></a>
                        <a href="${pageContext.request.contextPath}/controller?action=changeUserStatus&userId=${user.id}" class="btn btn-danger">
                            <c:if test="${user.status.name eq 'blocked'}"><fmt:message key="userCard.unblock.button"/></c:if>
                            <c:if test="${user.status.name eq 'active'}"><fmt:message key="userCard.block.button"/></c:if>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <pg:pagination contextPath="${pageContext.request.contextPath}"
                       activePage="${sessionScope.activePage}"
                       amountOfPages="${requestScope.amountOfPages}"
                       activeTab="${requestScope.activeTab}"
                       action="adminMain"
                       sortMethod="${null}"
                       groupBy="${null}"/>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>