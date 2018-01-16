<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.content.FinalArticlePage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="bodyClass" value="final-article" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>


<div class="page-header">
    <h1>${fn:escapeXml(myPage.article.articleTitle)}</h1>
</div>

<c:set var="imageUrl" value="${myPage.getArticleImageUrl(1200, 300)}" />
<c:if test="${!empty imageUrl}">
<p><img src="${fn:escapeXml(imageUrl)}" alt="${fn:escapeXml(myPage.article.articleTitle)}" class="img-responsive" /></p>
</c:if>

<c:if test="${myPage.article.articleWithDescription}">
<p class="article-descr lead">
    ${fn:escapeXml(myPage.article.articleDescription)}
</p>
</c:if>
<div class="article-body">
    ${myPage.article.articleContent}
</div>

<p class="article-infos">
    <fmt:message key="finalArticle.infoLine.createdBy" />
    <c:set var="fcp" value="${myPage.creator}" />
    <c:set var="fcpUrl" value="${myPage.getParticipantUrl(fcp)}" />
    <c:choose>
    <c:when test="${!empty fcpUrl}">
    <a href="${fn:escapeXml(fcpUrl)}">${fn:escapeXml(fcp.user.screenName)}</a>
    </c:when>
    <c:when test="${!empty fcp}">
    <del>${fn:escapeXml(fcp.user.screenName)}</del>
    </c:when>
    <c:otherwise><del>???</del></c:otherwise>
    </c:choose>
    
    <fmt:message key="finalArticle.infoLine.completedBy" />
    <c:forEach var="fcp" items="${myPage.replaceParticipants}" varStatus="status">
    <c:if test="${!status.first}">
    <c:choose>
    <c:when test="${status.last}"><fmt:message key="finalArticle.infoLine.authorLastSep" /></c:when>
    <c:otherwise><fmt:message key="finalArticle.infoLine.authorSep" /></c:otherwise>
    </c:choose>
    </c:if>
    
    <c:set var="fcpUrl" value="${myPage.getParticipantUrl(fcp)}" />
    <c:choose>
    <c:when test="${!empty fcpUrl}">
    <a href="${fn:escapeXml(fcpUrl)}">${fn:escapeXml(fcp.user.screenName)}</a>
    </c:when>
    <c:when test="${!empty fcp}">
    <del>${fn:escapeXml(fcp.user.screenName)}</del>
    </c:when>
    <c:otherwise><del>???</del></c:otherwise>
    </c:choose>
    </c:forEach>
    
    <fmt:message key="finalArticle.infoLine.dateFmt" var="dateFmt" />
    <fmt:formatDate pattern="${dateFmt}" timeZone="${myPage.timezone}" value="${myPage.article.createdAt}" var="formattedDate" />
    <fmt:message key="finalArticle.infoLine.lastPart">
        <fmt:param value="${formattedDate}" />
    </fmt:message>
    
    <button class="btn btn-default btn-xs upvote-btn ${myPage.upvotedAlready ? 'active' : ''}" type="button">
        <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span> ${myPage.article.upvotes}
    </button>
</p>

<form action="${fn:escapeXml(myPage.upvoteUrl)}" method="post" class="upvote-form">
    <input type="hidden" name="id" value="${fn:escapeXml(myPage.article.finalContentId)}" />
    <input type="hidden" name="upvote" value="${myPage.upvotedAlready ? '0' : '1'}" />
</form>

<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
