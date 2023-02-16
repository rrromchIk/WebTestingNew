<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/templates/_head.jsp">
        <jsp:param name="title" value="Profile"/>
    </jsp:include>

    <body>
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light">
            <a id="navbar-logo" class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">TestPortal</a>
            <div id="navbar-table" class="collapse navbar-collapse">
                <div class="item-wrapper">
                    <a href="${pageContext.request.contextPath}/controller?action=userMain"
                       class="float-right btn btn-outline-primary">&#8592 <fmt:message key="button.back"/></a>
                </div>
                <jsp:include page="/WEB-INF/templates/_lang-drop-down.jsp">
                    <jsp:param name="command"
                               value="/controller?action=profile"/>
                </jsp:include>
            </div>
        </nav>

        <h1 class="color"><fmt:message key="profile.label"/></h1>

        <div class="personal-image">
            <label class="label">
                <form method="post" action="${pageContext.request.contextPath}/controller?action=uploadAvatar" enctype="multipart/form-data">
                    <input class="fileInput" type="file" value="Change avatar" name="file" onchange="this.form.submit()" accept="image/*">
                </form>
                <figure class="personal-figure">
                    <av:avatar-image img="${requestScope.userAvatar}"
                                     styleClass="personal-avatar"
                                     onClickViewProfile="${false}"/>
                    <figcaption class="personal-figcaption">
                        <img src="https://raw.githubusercontent.com/ThiagoLuizNunes/angular-boilerplate/master/src/assets/imgs/camera-white.png">
                    </figcaption>
                </figure>
            </label>
        </div>

        <div id="profileForm">


            <form method="post" action="${pageContext.request.contextPath}/controller?action=editProfile">
                <input type="hidden" name="login" value="${requestScope.fullUser.login}">
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.login.label"/></label>
                    <input class="form-control" type="text" value="${requestScope.fullUser.login}" readonly
                           required maxlength="25">
                </div>
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.name.label"/></label>
                    <input class="form-control" type="text" name="name" value="${requestScope.fullUser.name}" readonly
                           placeholder="<fmt:message key="registrationForm.name.placeholder"/>" required maxlength="25"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.surname.label"/></label>
                    <input class="form-control" type="text" name="surname" value="${requestScope.fullUser.surname}" readonly
                           placeholder="<fmt:message key="registrationForm.surname.placeholder"/>" required maxlength="25"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.fillThisField"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label class="color"><fmt:message key="registrationForm.email.label"/></label>
                    <input class="form-control" type="email" name="email" value="${requestScope.fullUser.email}" readonly
                           placeholder="<fmt:message key="registrationForm.email.placeholder"/>" required maxlength="25"
                           oninvalid="this.setCustomValidity('<fmt:message key="validation.badEmail"/>')"
                           oninput="this.setCustomValidity('')">
                </div>
                <a href="${pageContext.request.contextPath}/controller?action=resetAvatar"
                   class="btn btn-outline-danger"><fmt:message key="profile.resetAvatar.button"/></a>
                <button class="btn btn-primary" id="edit-profile-btn" type="button" ><fmt:message key="button.edit"/></button>
                <button class="btn btn-primary" id="submit-changes-btn" disabled type="submit" ><fmt:message key="button.submitChanges"/></button>


            </form>
        </div>


        <jsp:include page="/WEB-INF/templates/_footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
    </body>

</html>