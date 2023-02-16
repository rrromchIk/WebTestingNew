<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="User-main"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>

            <div id="navbar-table" class="collapse navbar-collapse">
                <div class="item-wrapper">
                    <a href="${pageContext.request.contextPath}/controller?action=logOut"
                       class="float-right btn btn-outline-danger"><fmt:message key="button.logOut"/></a>
                </div>
            </div>

            <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                <jsp:param name="command"
                           value="/controller?action=userMain&tab=${requestScope.activeTab}&sortMethod=${requestScope.sortMethod}&page=${requestScope.activePage}&groupBy=${requestScope.selectedSubject}"/>
            </jsp:include>

            <av:avatar-image img="${requestScope.userAvatar}"
                             styleClass="avatar"
                             onClickViewProfile="${true}"/>

        </nav>
        <div id="choice">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a id="tests-choice" class="nav-link <c:if test="${requestScope.activeTab eq 'tests'}">active</c:if>"
                       href="${pageContext.request.contextPath}/controller?action=userMain&tab=tests&sortMethod=default"><fmt:message key="tab.tests"/></a>
                </li>
                <li class="nav-item">
                    <a id="users-choice" class="nav-link <c:if test="${requestScope.activeTab eq 'passedTests'}">active</c:if>"
                       href="${pageContext.request.contextPath}/controller?action=userMain&tab=passedTests"><fmt:message key="tab.passedTests"/></a>
            </ul>



            <c:if test="${requestScope.activeTab eq 'tests'}">
                <div class="sort">
                    <span><fmt:message key="userMain.sortBy.label"/>: </span>
                    <a href="${pageContext.request.contextPath}/controller?action=userMain&tab=tests&sortMethod=name"
                       class="sort"><fmt:message key="userMain.sortBy.name"/> &#8693</a>
                    <a href="${pageContext.request.contextPath}/controller?action=userMain&tab=tests&sortMethod=difficulty"
                       class="sort"><fmt:message key="userMain.sortBy.difficulty"/> &#8693</a>
                    <a href="${pageContext.request.contextPath}/controller?action=userMain&tab=tests&sortMethod=numOfQuest"
                       class="sort"><fmt:message key="userMain.sortBy.numOfQuestions"/> &#8693</a>
                    <span><fmt:message key="userMain.onPartSubj.label"/>: </span>

                    <select name="choice" onchange="window.location.href=this.options[this.selectedIndex].value">
                        <option>              </option>
                        <c:forEach var="subject" items="${requestScope.subjects}">
                            <option VALUE="${pageContext.request.contextPath}/controller?action=userMain&tab=tests&groupBy=${subject}"
                            <c:if test="${requestScope.selectedSubject eq subject}">selected</c:if>>${subject}</option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
        </div>

        <div id="tests">
            <c:forEach var="test" items="${requestScope.tests}">
                <div id="test-item" class="card">
                    <h5 class="card-header">${test.name}</h5>
                    <div class="card-body">
                        <h4 class="card-title">${test.subject}</h4>
                        <p class="card-text"><span class="spanName"><fmt:message key="testCard.difficulty.label"/>: </span>
                            <df:difficulty diff="${test.difficulty}"/>
                        </p>
                        <p class="card-text"><span class="spanName"><fmt:message key="testCard.duration.label"/>: </span>${test.duration} <fmt:message key="testCard.minutes"/></p>
                        <p class="card-text"><span class="spanName"><fmt:message key="testCard.numOfQuest.label"/>: </span>${test.numberOfQuestions}</p>

                        <c:choose>
                            <c:when test="${test.status.value eq 'not_started'}">
                                <a href="${pageContext.request.contextPath}/controller?action=startTest&testId=${test.id}"
                                   class="btn btn-success"><fmt:message key="testCard.button.startTest"/></a>
                            </c:when>

                            <c:when test="${test.status.value eq 'started'}">
                                <a href="${pageContext.request.contextPath}/controller?action=passTest&testId=${test.id}"
                                   class="btn btn-primary"><fmt:message key="testCard.button.continue"/></a>
                            </c:when>

                            <c:when test="${test.status.value eq 'passed'}">
                                <button type="button" class="btn btn-secondary" disabled><fmt:message key="testCard.button.passed"/></button>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div id="passedTests">
            <c:forEach var="testInfo" items="${requestScope.passedTests}">
                <div id="test-item" class="card">
                    <h5 class="card-header">${testInfo.testName}</h5>
                    <div class="card-body">
                        <h3 class="card-title">${testInfo.testSubject}</h3>
                        <p class="card-text">
                            <span class="spanName"><fmt:message key="testCard.difficulty.label"/>: </span>
                            <df:difficulty diff="${testInfo.testDifficulty}"/>
                        </p>
                        <p class="card-text">
                            <span class="spanName"><fmt:message key="testCard.startingTime.label"/>: </span>
                                ${testInfo.startingTime}
                        </p>
                        <p class="card-text">
                            <span class="spanName"><fmt:message key="testCard.endingTime.label"/>: </span>
                            <c:choose>
                                <c:when  test="${testInfo.endingTime eq 'not ended'}">
                                    <fmt:message key="testCard.notEnded"/>
                                </c:when>
                                <c:otherwise>
                                    ${testInfo.endingTime}
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p class="card-text">
                            <span class="spanName"><fmt:message key="testCard.result.label"/>: </span>
                                ${testInfo.result} %
                        </p>
                        <c:if test="${testInfo.endingTime != 'not ended'}">
                            <a href="${pageContext.request.contextPath}/controller?action=testResult&testId=${testInfo.testId}"
                               class="btn btn-primary" target="_blank" >
                                <fmt:message key="testCard.checkOutAnswers.label"/>
                            </a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

        <pg:pagination contextPath="${pageContext.request.contextPath}"
                       activePage="${requestScope.activePage}"
                       amountOfPages="${requestScope.amountOfPages}"
                       activeTab="${requestScope.activeTab}"
                       action="userMain"
                       sortMethod="${requestScope.sortMethod}"
                       groupBy="${requestScope.groupBy}"/>


        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>    