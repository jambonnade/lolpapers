<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<fmt:message key="user.connectTwitter.chooseName.pageTitle" var="pageTitle" />

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.user.ConnectTwitterPage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="${pageTitle}" />
</jsp:useBean>

<c:set var="baseHtmlId" value="${myPage.uniqueHtmlId}" />

<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<form action="${fn:escapeXml(myPage.userConnectTwitterUrl)}" method="post">
    <div class="form-group">
        <label for="${baseHtmlId}-form-name-input"><fmt:message key="user.connectTwitter.chooseName.label" /></label>
        <input type="text" name="name" value="${fn:escapeXml(myPage.screenName)}" class="form-control" id="${baseHtmlId}-form-name-input">
    </div>

    <button type="submit" class="btn btn-default"><fmt:message key="user.connectTwitter.chooseName.submit" /></button>
</form>

<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
