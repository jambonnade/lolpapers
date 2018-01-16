<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.Page" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-start.jspf" %>


<div class="jumbotron">
    <h1><fmt:message key="root.welcome" /></h1>
</div>

<%@ include file="/WEB-INF/jspf/front/page-html-end.jspf" %>
