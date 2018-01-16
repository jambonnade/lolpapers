<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.HelpPage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="À propos" />
    <jsp:setProperty name="myPage" property="bodyClass" value="help-page" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<div class="page-header">
    <h1>À propos</h1>
</div>

<div class="lead">
    <p><em>LolPapers</em> est un site qui propose du contenu altéré par les utilisateurs en remplaçant, comme un jeu de <em>cadavre exquis</em>, des groupes de mots dans le texte par d'autres groupes de mots ayant la même fonction.</p>
    <p>Des articles de presse constituent le matériau de base (d'où <em>papers</em>) mais d'autres types de contenu pourraient être intégrés au système.</p>
    <p>Un utilisateur prépare un article en choisissant quels groupes de mots peuvent être remplacés et par quoi, et un autre utilisateur saisit les nouveaux groupes de mots sans connaître l'article d'origine.</p>
    <p>Consulter l'<a href="${myPage.helpUrl}">aide</a> pour des conseils sur comment bien préparer un article et effectuer le remplacement.</p>
</div>

<p>Site réalisé par Guillaume "Gruik" P.</p>


<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
