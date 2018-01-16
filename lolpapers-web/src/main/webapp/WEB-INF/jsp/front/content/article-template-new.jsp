<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<fmt:message key="articleTemplate.new.pageTitle" var="pageTitle" />

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.Page" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="${pageTitle}" />
</jsp:useBean>

<c:set var="baseHtmlId" value="${myPage.uniqueHtmlId}" />

<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<div class="page-header">
    <h2><fmt:message key="articleTemplate.new.fromSources.title" /></h2>
</div>
<form action="${fn:escapeXml(myPage.newArticleTemplateUrl)}" method="post">
    <p><fmt:message key="articleTemplate.new.fromSources.msg" /></p>
    <button type="submit" class="btn btn-default"><fmt:message key="articleTemplate.new.fromSources.submit" /></button>
</form>

<div class="page-header">
    <h2><fmt:message key="articleTemplate.new.fromUrl.title" /></h2>
</div>
<form action="${fn:escapeXml(myPage.newArticleTemplateUrl)}" method="post">
    <div class="form-group">
        <label for="${baseHtmlId}-form-url-input"><fmt:message key="articleTemplate.new.fromUrl.urlLabel" /></label>
        <input type="text" name="url" class="form-control" id="${baseHtmlId}-form-url-input">
        <input type="hidden" name="from_url" value="1">
    </div>
    <div class="form-group">
        <label for="${baseHtmlId}-form-category-input"><fmt:message key="articleTemplate.new.fromUrl.categoryLabel" /></label>
        <select class="form-control" name="category_id" id="${baseHtmlId}-form-category-input">
            <option value="">-</option>
            <c:forEach var="cat" items="${myPage.website.categories}">
            <option value="${fn:escapeXml(cat.id)}">${fn:escapeXml(cat.title)}</option>
            </c:forEach>
        </select>
    </div>

    <button type="submit" class="btn btn-default"><fmt:message key="articleTemplate.new.fromUrl.submit" /></button>
</form>

<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
