<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>


<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.user.ProfilePage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
</jsp:useBean>

<fmt:message key="user.profile.pageTitle" var="pageTitle">
    <fmt:param value="${myPage.profileUser.screenName}" />
</fmt:message>
<jsp:setProperty name="myPage" property="curPageTitle" value="${pageTitle}" />

<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><fmt:message key="user.profile.stats.tilte" /></h3>
    </div>
    <table class="table">
        <tbody>
            <tr>
                <th scope="row"><fmt:message key="user.profile.stats.created" /></th>
                <fmt:message key="user.profile.stats.createdDateFmt" var="dateFmt" />
                <td><fmt:formatDate pattern="${dateFmt}" timeZone="${myPage.timezone}" value="${myPage.profileUser.createdAt}" /></td>
            </tr>    
            <tr>
                <th scope="row"><fmt:message key="user.profile.stats.templatesCount" /></th>
                <td>${myPage.profileUser.finishedArticleCount}</td>
            </tr>    
            <tr>
                <th scope="row"><fmt:message key="user.profile.stats.placeholdersCount" /></th>
                <td>${myPage.profileUser.placeholderCount}</td>
            </tr>    
            <tr>
                <th scope="row"><fmt:message key="user.profile.stats.replacementCount" /></th>
                <td>${myPage.profileUser.replacementCount}</td>
            </tr>    
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
