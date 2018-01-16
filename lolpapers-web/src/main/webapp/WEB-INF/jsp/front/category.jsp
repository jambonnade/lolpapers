<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.CategoryPage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>


<div class="page-header">
    <h1>${fn:escapeXml(myPage.category.title)}</h1>
</div>

<div class="category-head ${fn:escapeXml(myPage.category.code)}"></div>

<jsp:useBean id="articleList" class="de.jambonna.lolpapers.web.model.page.content.FinalArticleList" scope="page">
    <jsp:setProperty name="articleList" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="articleList" property="articles" value="${myPage.articles}" />
    <jsp:setProperty name="articleList" property="emphaseFirst" value="true" />
</jsp:useBean>
<%@ include file="/WEB-INF/jspf/front/content/final-article-list.jspf" %>


<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
