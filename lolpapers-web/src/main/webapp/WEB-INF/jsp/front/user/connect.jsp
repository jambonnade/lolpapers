<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<fmt:message key="user.connect.pageTitle" var="pageTitle" />

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.Page" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="${pageTitle}" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<p><a href="${fn:escapeXml(myPage.userConnectTwitterUrl)}"><fmt:message key="user.connect.signInWithTwitter"></fmt:message></a></p>

<c:set var="tsd" value="${myPage.requestContext.userSession.twitterSigninData}" />
<c:if test="${!empty tsd && !empty tsd.requestToken}">
<p>${tsd.requestToken.token}</p>
</c:if>
<c:if test="${!empty tsd && !empty tsd.accessToken}">
<p>${tsd.accessToken.token}</p>
</c:if>
<c:if test="${!empty tsd}">
<p>user ${tsd.userId}</p>
<p>name ${tsd.screenName}</p>
</c:if>

<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
