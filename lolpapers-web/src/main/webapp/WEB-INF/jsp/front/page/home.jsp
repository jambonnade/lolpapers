<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.HomePage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>


<div class="jumbotron">
    <h1><fmt:message key="home.welcome"><fmt:param value="${fn:escapeXml(myPage.website.name)}" /></fmt:message></h1>
</div>

<div class="page-header">
    <h2><fmt:message key="home.lastArticles.title" /></h2>
</div>

<jsp:useBean id="articleList" class="de.jambonna.lolpapers.web.model.page.content.FinalArticleList" scope="page">
    <jsp:setProperty name="articleList" property="servletRequest" value="${pageContext.request}" />
</jsp:useBean>
<jsp:setProperty name="articleList" property="articles" value="${myPage.lastArticles}" />
<jsp:setProperty name="articleList" property="emphaseFirst" value="true" />
<%@ include file="/WEB-INF/jspf/front/content/final-article-list.jspf" %>

<div class="page-header">
    <h2><fmt:message key="home.topArticles.title" /></h2>
</div>

<jsp:setProperty name="articleList" property="articles" value="${myPage.topArticles}" />
<jsp:setProperty name="articleList" property="emphaseFirst" value="false" />
<%@ include file="/WEB-INF/jspf/front/content/final-article-list.jspf" %>


<c:if test="${!empty param.errhome1}">
    ${myPage.testException}
</c:if>
<c:if test="${!empty param.errhome2}">
    ${myPage.testException2}
</c:if>

<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
