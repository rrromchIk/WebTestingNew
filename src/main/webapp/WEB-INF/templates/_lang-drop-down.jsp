<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<div id="lang-dropdown" class="dropdown">
    <button id="dropdownMenuButton" class="btn btn-secondary dropdown-toggle" type="button"
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <fmt:message key="lang"/>
    </button>
    <div class="dropdown-menu" id="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <p onclick="window.location.href='${pageContext.request.contextPath}/controller?action=i18n&en&command=${param.command}'"
           id="engDropdown" class="dropdown-item"><img src="${pageContext.request.contextPath}/img/enFlag.webp" alt="ENG"></p>
        <p onclick="window.location.href='${pageContext.request.contextPath}/controller?action=i18n&ua&command=${param.command}'"
           id="uaDropdown" class="dropdown-item"><img src="${pageContext.request.contextPath}/img/uaFlag.png" alt="UA"></p>
    </div>
</div>
