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
                    <a href="${pageContext.request.contextPath}/controller?action=testInfo&testId=${requestScope.fullTest.id}"
                       class="float-right btn btn-outline-primary"><fmt:message key="testInfo.label"/></a>
                </div>
            </div>
        </nav>

        <div class="card" id="addQuestionsMain">
            <h3 class="card-header"><fmt:message key="registrationForm.name.label"/>: ${requestScope.fullTest.name}</h3>
            <div class="card-body">
                <p class="card-text">
                    <span class="spanName"><fmt:message key="testCard.subject.label"/>: </span>
                    ${requestScope.fullTest.subject}
                </p>
                <p class="card-text"><span class="spanName"><fmt:message key="testCard.difficulty.label"/>: </span>
                    <df:difficulty diff="${requestScope.fullTest.difficulty}"/>
                </p>
                <p class="card-text"><span class="spanName"><fmt:message key="testCard.duration.label"/>: </span>
                    ${requestScope.fullTest.duration} <fmt:message key="testCard.minutes"/></p>
                <p class="card-text"><span class="spanName"><fmt:message key="testCard.numOfQuest.label"/>: </span>
                    ${requestScope.fullTest.numberOfQuestions}</p>
            </div>

            <div class="card-body" id="questionsDiv">
                <form method="post" id="addQuestionsForm"
                      action="${pageContext.request.contextPath}/controller?action=submitQuestionInfo&testId=${requestScope.testId}">
                    <hr>
                    <h3 class="testHeader"><fmt:message key="testPassing.question.label"/> ${requestScope.questionNumber}/${requestScope.fullTest.numberOfQuestions}</h3>
                    <textarea id="questionText" class="form-control" name="questionText" required
                              placeholder="<fmt:message key="addQuestions.question.placeholder"/>"
                              oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                              oninput="this.setCustomValidity('')"></textarea>
                    <div class="form-check">
                        <span><fmt:message key="addQuestions.grade.label"/>: </span>
                        <input type="number" id="gradeInput" name="maxScore" value="1" placeholder="1" min="1"/>
                        <span class="amountOfQuestionSpan">
                                <button type="button" class="btn btn-success" id="addTest">+</button>
                                <button type="button" class="btn btn-danger" id="deleteTest">-</button>
                            </span>
                        <h3 class="answerHeader"><fmt:message key="addQuestions.answers.label"/>:</h3>
                    </div>

                    <div id="answersDiv">
                        <span id="maxAmountOfAnswers" hidden>${requestScope.maxAmountOfAnswers}</span>
                        <span id="placeholderValue" hidden><fmt:message key="addQuestions.answer.placeholder"/></span>
                        <span id="validationText" hidden><fmt:message key="validation.fillThisField"/></span>
                        <div class="form-check" id='formCheckDiv1'>
                            <input class="form-check-input"  type="checkbox" name="answer1correct">
                            <input class="form-control" type="text" name="answer1" required
                                   placeholder="<fmt:message key="addQuestions.answer.placeholder"/>"
                                   oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary" id="submitTestInfoButton"><fmt:message key="button.submit"/></button>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/add-questions.js"></script>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>

</html>