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
                    <jsp:param name="command" value="/controller?action=addTest"/>
                </jsp:include>
            </div>
        </nav>

        <div id="addingTestDIV">
            <div id="addingTest" class="card">
                <form method="post" action="${pageContext.request.contextPath}/controller?action=submitTestInfo">
                    <h3><fmt:message key="addTest.enterTestInfo.label"/></h3><br>
                    <div class="form-row">
                        <div class="form-group col-md-3 mb-3">
                            <label><fmt:message key="registrationForm.name.label"/></label>
                            <input type="text" name="name" class="form-control" required
                                   placeholder="<fmt:message key="registrationForm.name.placeholder"/>"
                                   oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                                   oninput="this.setCustomValidity('')">
                        </div>

                        <div class="form-group col-md-3 mb-3">
                            <label><fmt:message key="testCard.subject.label"/></label>
                            <input type="text" name="subject" class="form-control" required
                                   placeholder="<fmt:message key="testCard.subject.placeholder"/>"
                                   oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                                   oninput="this.setCustomValidity('')">
                        </div>

                        <div class="form-group col-md-2">
                            <label><fmt:message key="testCard.duration.label"/>(<fmt:message key="testCard.minutes"/>)</label>
                            <input type="number" name="duration" class="form-control" value="1"
                                   placeholder="1" min="1">
                        </div>

                        <div class="form-group col-md-2">
                            <label><fmt:message key="testCard.difficulty.label"/></label>
                            <select name="difficulty" class="form-control">
                                <option value="0" selected><fmt:message key="testCard.difficulty.easy"/></option>
                                <option value="1"><fmt:message key="testCard.difficulty.medium"/></option>
                                <option value="2"><fmt:message key="testCard.difficulty.hard"/></option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-sm-5 col-form-label"><fmt:message key="addTest.addAmountOfQuestions"/>:</label>
                        <div class="col-md-2">
                            <input type="number" name="numOfQuestions" class="form-control"
                                   value="3" placeholder="3" min="3">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary" id="submitTestInfoButton"><fmt:message key="button.submit"/></button>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>
</html>