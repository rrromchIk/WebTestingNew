<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Add test"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand">TestsPortal</a>

            <div id="navbar-table" class="collapse navbar-collapse">
                <div class="item-wrapper">
                    <a href="${pageContext.request.contextPath}/controller?action=adminMain"
                       class="float-right btn btn-outline-primary">&#8592 <fmt:message key="button.back"/></a>
                </div>
                <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                    <jsp:param name="command" value="/controller?action=testInfo&testId=${requestScope.fullTest.id}"/>
                </jsp:include>
            </div>
        </nav>

        <div id="addingTest" class="card">
            <form method="post" action="${pageContext.request.contextPath}/controller?action=editTest&testId=${requestScope.fullTest.id}">
                <h3><fmt:message key="testInfo.label"/></h3><br>
                <div class="form-row">
                    <div class="form-group col-md-3 mb-3">
                        <label><fmt:message key="registrationForm.name.label"/></label>
                        <input class="form-control" name="name" type="text" value="${requestScope.fullTest.name}" required readonly>
                    </div>

                    <div class="form-group col-md-3 mb-3">
                        <label><fmt:message key="testCard.subject.label"/></label>
                        <input class="form-control" name="subject" type="text" value="${requestScope.fullTest.subject}"
                               required placeholder="<fmt:message key="testCard.subject.placeholder"/>"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-group col-md-2">
                        <label><fmt:message key="testCard.duration.label"/>(<fmt:message key="testCard.minutes"/>)</label>
                        <input class="form-control" type="number" name="duration" value="${requestScope.fullTest.duration}"
                               placeholder="1" min="1">
                    </div>

                    <div class="form-group col-md-2">
                        <label for="inputDifficulty"><fmt:message key="testCard.difficulty.label"/></label>
                        <select id="inputDifficulty" name="difficulty" class="form-control">
                            <option value="0" <c:if test="${requestScope.fullTest.difficulty.value eq 0}">selected</c:if>>
                                <fmt:message key="testCard.difficulty.easy"/></option>
                            <option value="1" <c:if test="${requestScope.fullTest.difficulty.value  eq 1}">selected</c:if>>
                                <fmt:message key="testCard.difficulty.medium"/></option>
                            <option value="2" <c:if test="${requestScope.fullTest.difficulty.value  eq 2}">selected</c:if>>
                                <fmt:message key="testCard.difficulty.hard"/></option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <label id="amountOfQuestionsLabel" ><fmt:message key="testInfo.amountOfQuestionsInTheTest"/>:</label>
                    <div class="form-group col-md-2">
                        <input class="form-control" name="numOfQuestions"  type="number"
                               value="${requestScope.fullTest.numberOfQuestions}" placeholder="3" min="1">
                    </div>
                </div>

                <button class="btn btn-primary" type="submit" id="submitTestInfoButton">
                    <fmt:message key="button.submit"/>
                </button>
            </form>

            <h3><fmt:message key="testInfo.questions.label"/>: </h3>
            <c:forEach var="question" items="${requestScope.questions}">
                <div id="test-item" class="card">
                    <div class="card-body">
                        <p class="card-text"><span class="spanName"><fmt:message key="testInfo.text.label"/>: </span>${question.text}</p>
                        <p class="card-text"><span class="spanName"><fmt:message key="testInfo.maxScore.label"/>: </span>${question.maxScore}</p>

                        <a href="${pageContext.request.contextPath}/controller?action=deleteQuestion&questionId=${question.id}&testId=${requestScope.fullTest.id}"
                           class="btn btn-danger"><fmt:message key="button.delete"/></a>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${requestScope.addQuestions eq true}">
                    <a href="${pageContext.request.contextPath}/controller?action=addQuestions&testId=${requestScope.fullTest.id}"
                       class="btn btn-success"><fmt:message key="testInfo.addQuestions.button"/></a>
            </c:if>
        </div>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>