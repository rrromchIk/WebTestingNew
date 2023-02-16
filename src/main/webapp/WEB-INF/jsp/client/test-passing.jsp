<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Pass test"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
            <div id="navbar-table" class="collapse navbar-collapse">
                <div class="item-wrapper">
                    <a href="${pageContext.request.contextPath}/controller?action=userMain"
                       class="float-right btn btn-outline-primary"><fmt:message key="testPassing.button.allTests"/></a>
                </div>
            </div>
        </nav>

        <div id="main-screen">
            <div class="card rounder-3 questionCard">
                <ul id="question-sidebar" class="list-group list-group-flush">
                    <c:forEach begin="0" varStatus="loop" var="question" items="${requestScope.questions}">
                        <li class="list-group-item question-number <c:if test="${loop.index eq requestScope.currentQuestion.number - 1}">active</c:if>">
                            <a href="${pageContext.request.contextPath}/controller?action=passTest&testId=${requestScope.test.id}&renderQuestion=${question.id}"
                               class="btn">
                                <fmt:message key="testPassing.question.label"/> ${question.number}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div id="question">
                <div class="card">
                    <h5 class="card-header">
                        ${requestScope.test.name}
                    </h5>
                    <div class="card-body">
                        <h6 class="card-title">
                            <fmt:message key="testPassing.question.label"/> ${requestScope.currentQuestion.number}
                        </h6>
                        <h4 class="card-text">
                            ${requestScope.currentQuestion.text}?
                        </h4>
                        <br>
                        <form method="post" action="${pageContext.request.contextPath}/controller?action=submitAnswers&testId=${requestScope.test.id}&questionId=${requestScope.currentQuestion.id}">
                            <div id="radio-btn-group">
                                <c:forEach var="answer" items="${requestScope.answers}" varStatus="loop">
                                    <c:if test="${requestScope.questionType eq 'radio'}">
                                        <c:set var="name" scope="page" value="0"/>
                                    </c:if>
                                    <c:if test="${requestScope.questionType eq 'checkbox'}">
                                        <c:set var="name" scope="page" value="${loop.index}"/>
                                    </c:if>
                                    <input type="${requestScope.questionType}" id="${loop.index}"
                                           name="answer${pageScope.name}" value='${answer.text}' <c:if test="${answer.checked eq true}">checked</c:if>>
                                    <label for="${loop.index}">${answer.text}</label><br>
                                </c:forEach>
                            </div>

                            <button type="submit" class="btn btn-primary"><fmt:message key="button.submit"/></button>
                            <a id="endAttemptButton" href="${pageContext.request.contextPath}/controller?action=endTest&testId=${requestScope.test.id}"
                               class="btn btn-danger float-right">
                                <fmt:message key="testPassing.button.endAttempt"/>
                            </a>
                        </form>
                    </div>
                </div>
            </div>

            <div class="timer">
                <div class="timer__items">
                    <div hidden class id="millis">${requestScope.timer}</div>
                    <a id="endTestButton" href="${pageContext.request.contextPath}/controller?action=endTest&testId=${requestScope.test.id}" hidden></a>
                    <div class="timer__item timer__hours">00</div>
                    <div class="timer__item timer__minutes">00</div>
                    <div class="timer__item timer__seconds">00</div>
                </div>
            </div>
        </div>

        <!--<div id="endAttemptButtonDIV">
            <a id="endAttemptButton" href="${pageContext.request.contextPath}/controller?action=endTest&testId=${requestScope.test.id}"
               class="btn btn-danger float-right">
                <fmt:message key="testPassing.button.endAttempt"/>
            </a>
        </div>-->




        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/test-passing.js"></script>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>    