<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.Page" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-start.jspf" %>

<div class="container main" role="main">

    <c:set var="statusCode" value="${requestScope['javax.servlet.error.status_code']}" />
    <div class="jumbotron">
        <c:choose>
        <c:when test="${!empty errorRef}">
        <h1><fmt:message key="error.exception.title" /></h1>
        <p><fmt:message key="error.exception.refMessage"><fmt:param>${errorRef}</fmt:param></fmt:message></p>
        </c:when>
        <c:when test="${statusCode == '404'}">
        <h1>${statusCode} <small><fmt:message key="error.404.message" /></small></h1>
        </c:when>
        <c:when test="${!empty statusCode}">
        <h1>${statusCode}</h1>
        </c:when>
        <c:otherwise>
        <h1><fmt:message key="error.exception.title" /></h1>
        </c:otherwise>
        </c:choose>
        <p><a href="${myPage.baseUrl}"><fmt:message key="error.backToSite" /></a></p>
    </div>

    <c:if test="${!empty param.errerr1}">
        err1
        ${myPage.testException}
    </c:if>
    <c:if test="${!empty param.errerr2}">
        err2
        ${myPage.testException2}
    </c:if>

</div>

<%@ include file="/WEB-INF/jspf/front/page-html-end.jspf" %>
