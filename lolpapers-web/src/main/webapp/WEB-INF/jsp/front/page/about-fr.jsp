<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.HelpPage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="� propos" />
    <jsp:setProperty name="myPage" property="bodyClass" value="help-page" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<div class="page-header">
    <h1>� propos</h1>
</div>

<div class="lead">
    <p><em>LolPapers</em> est un site qui propose du contenu alt�r� par les utilisateurs en rempla�ant, comme un jeu de <em>cadavre exquis</em>, des groupes de mots dans le texte par d'autres groupes de mots ayant la m�me fonction.</p>
    <p>Des articles de presse constituent le mat�riau de base (d'o� <em>papers</em>) mais d'autres types de contenu pourraient �tre int�gr�s au syst�me.</p>
    <p>Un utilisateur pr�pare un article en choisissant quels groupes de mots peuvent �tre remplac�s et par quoi, et un autre utilisateur saisit les nouveaux groupes de mots sans conna�tre l'article d'origine.</p>
    <p>Consulter l'<a href="${myPage.helpUrl}">aide</a> pour des conseils sur comment bien pr�parer un article et effectuer le remplacement.</p>
</div>

<p>Site r�alis� par Guillaume "Gruik" P.</p>


<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
