<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.HelpPage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="Aide" />
    <jsp:setProperty name="myPage" property="bodyClass" value="help-page" />
</jsp:useBean>


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<div class="page-header">
    <h1 class="help-title">
        <span class="help-anchor" id="HSEC-help">&nbsp;</span>
        <a href="#HSEC-help"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Aide</a>
    </h1>
</div>

<div class="page-header">
    <h2 class="help-title">
        <span class="help-anchor" id="HSEC-general">&nbsp;</span>
        <a href="#HSEC-general"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Fonctionnement g�n�ral</a>
    </h2>
</div>

<ul>
    <li>Vous devez �tre <a href="${myPage.userConnectUrl}">connect�</a> pour pr�parer un article ou effectuer le remplacement. Actuellement seule la connexion via Twitter est possible.</li>
    <li>Vous pouvez pr�parer jusqu'� <strong>${myPage.nbArticleTemplateByDay}</strong> articles par jour.</li>
    <li>Les articles de base proviennent d'une liste de flux RSS qui pourra �voluer avec le temps.</li>
    <li>Une fois que vous avez termin� la pr�paration d'un article, vous pouvez effectuer autant de remplacements qu'il y avait d'emplacements de base dans votre article.</li>
    <li>La pr�paration d'un article et le remplacement n�cessitent de conna�tre les bases de la grammaire fran�aise (notions de base, accords en genre et en nombre, conjugaison des verbes) ; les choses plus techniques sont expliqu�es avec des bulles d'aide ainsi que dans cette page.</li>
    <li>Si en effectuant un remplacement vous compl�tez un article, vous serez redirig� sur l'article final.</li>
</ul>
    
<div class="page-header">
    <h2 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article">&nbsp;</span>
        <a href="#HSEC-prep-article"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Pr�paration d'article</a>
    </h2>
</div>

<p>Vous pouvez pr�parer un article en allant dans le menu <strong><fmt:message key="nav.create" /></strong> &gt; <strong><fmt:message key="nav.create.article" /></strong> &gt; <strong><fmt:message key="nav.create.article.new" /></strong>, 
    dans la limite autoris�e par jour et dans la limite des articles de base disponibles par rapport aux sources d'articles (RSS).</p>
<p>Vous pouvez retrouver dans le menu <strong><fmt:message key="nav.create" /></strong> &gt; <strong><fmt:message key="nav.create.article" /></strong> les articles entam�s. Pensez � <strong>rejeter</strong> les articles que vous ne comptez pas terminer.</p>
<p>La premi�re fois que vous �ditez un article, une aide de d�marrage rapide s'affiche. Si elle n'est plus affich�e, vous pourrez la retrouver dans le menu <strong><fmt:message key="articleTemplate.edit.nav.help.tuto" /></strong> &gt; <strong><fmt:message key="articleTemplate.edit.nav.help.tuto" /></strong>.</p>
<p>Comme le travail de pr�paration peut �tre long, vous avez la possibilit� de sauvegarder les modifications avec le bouton <strong><fmt:message key="articleTemplate.edit.nav.save" /></strong>.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Important&nbsp;:</strong> Si vous quittez la page sans avoir sauvegard�, les modifications sont perdues.</div>
<p>Cliquez sur le bouton <strong><fmt:message key="articleTemplate.edit.nav.finish" /></strong> pour terminer l'article.</p>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article-content">&nbsp;</span>
        <a href="#HSEC-prep-article-content"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Pr�paration du contenu et rejet</a>
    </h3>
</div>

<p>Pour chaque article de base, le syst�me essaie d'identifier le contenu principal de l'article mais peut �chouer en partie ou compl�tement.</p>
<p>Dans l'�dition de l'article vous avez la possibilit� de rejeter l'article pour mauvais contenu avec le menu <strong><fmt:message key="articleTemplate.edit.nav.cancel" /></strong> &gt; <strong><fmt:message key="articleTemplate.edit.nav.cancel.rejectWrongContent" /></strong>, il ne sera alors plus propos� � personne.</p>
<p>Vous avez aussi la possibilit� de rejeter l'article si la pr�partion de ce dernier ne vous inspire pas, avec le menu <strong><fmt:message key="articleTemplate.edit.nav.cancel" /></strong> &gt; <strong><fmt:message key="articleTemplate.edit.nav.cancel.delete" /></strong>, mais il risque d'�tre propos� � nouveau (� vous ou � qqun d'autre).</p>
<p>Si l'article convient globalement, vous pouvez ensuite supprimer des portions de texte comme expliqu� ci-dessous.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-content-descr">&nbsp;</span>
    <a href="#HSEC-prep-article-content-descr"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Gestion de la description</a>
</h4>
<p>La description appara�t dans les listings d'articles ainsi que dans l'article lui m�me.</p>
<p>Si il n'y en a pas, le syst�me utilisera le d�but du contenu de l'article dans les listings.</p>
<p>Vous pouvez (et devriez) rejeter la description avec le bouton propos� si elle fait doublon avec le d�but de l'article ou si le texte n'est pas propice � faire une bonne description.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-content-erase-mode">&nbsp;</span>
    <a href="#HSEC-prep-article-content-erase-mode"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Mode suppression</a>
</h4>
<p>Supprimer des blocs de texte dans le corps de l'article a les utilit�s suivantes :</p>
<ul>
    <li>Enlever ce qui ne constitue pas du texte d'article, comme des liens "voir aussi", des signatures, des lignes de donn�es sur l'article d'origine (auteur, date..)</li>
    <li>Pour r�duire la longueur de l'article afin que ce soit moins long pour vous.</li>
</ul>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> La d�limitation des blocs de texte est bas�e sur la structure html de l'article, parfois tout le texte est dans un seul bloc ce qui emp�che d'utiliser cette fonctionalit�.</div>
<p>En supprimant du texte, le nombre de mots diminue, faites attention � ne pas �tre en dessous du minimum (actuellement : <strong>${myPage.minArticleWc}</strong>).</p>
<p>Cliquer sur le bouton <strong><fmt:message key="articleTemplate.edit.nav.eraseMode" /></strong> pour passer en mode suppression, puis cliquer sur les blocs de texte pour les supprimer ou annuler leur suppression. Cliquer sur <strong><fmt:message key="articleTemplate.edit.nav.eraseMode" /></strong> pour revenir en mode normal.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> La suppression de blocs de texte supprime les �ventuels emplacements cr��s dedans.</div>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article-placeholders">&nbsp;</span>
        <a href="#HSEC-prep-article-placeholders"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Cr�ation des emplacements</a>
    </h3>
</div>

<p>Vous devez cr�er un certain nombre d'emplacements avant de finaliser l'article, le nombre minimum est affich� dans la barre d'outils (vous pouvez bien s�r en faire plus que le minimum).</p>
<p>Actuellement il doit y avoir un emplacement minimum tous les <strong>${myPage.maxWordsByArticleTemplatePlaceholder}</strong> mots en moyenne et au minimum <strong>${myPage.minArticleTemplatePlaceholders}</strong> emplacements.</p>
<p>&nbsp;</p>
<p>Pour cr�er un emplacement, il faut d'abord s�lectionner le texte qui va �tre remplac�. Pour se faire cliquer sur un mot puis �tendre la s�lection en cliquant sur les mots � gauche ou � droite du mot.</p>
<p>Ensuite cliquer sur le type grammatical du texte s�lectionn� pour cr�er l'emplacement. On ne peut pas changer le type ou l'�tendue de la s�lection apr�s �a (il faut supprimer et recr�er l'emplacement).</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-popup">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-popup"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>D�finition de l'emplacement</a>
</h4>

<p>La d�finition d'un emplacement passe par le renseignement d'attributs (qui sont sp�cifiques au type d'emplacement). Chaque attribut a un ensemble de valeurs qu'on peut s�lectionner/d�selectionner en cliquant dessus. Certains attributs limitent ou pr�selectionnent d'autres.</p>
<p>Il y a 3 sections d'attributs&nbsp;:</p>
<ul>
    <li><strong><fmt:message key="articleTemplate.edit.placeholderModal.context.title" /></strong> : attributs concernant le texte autour de l'emplacement (potentiellement tout l'article).</li>
    <li><strong><fmt:message key="articleTemplate.edit.placeholderModal.definition.title" /></strong> : attributs concernant le texte d'origine.</li>
    <li><strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong> : attributs possibles pour le texte rempla�ant. On peut s�lectionner plusieurs valeurs par attribut ici.</li>
</ul>

<p>Le contexte d�finit les possibilit�s pour le texte de l'emplacement (� la fois le texte d'origine et le remplacement). Exemple : parce qu'il y a des accords de tel genre/nombre dans le contexte, il doit y avoir un nom de tel genre/nombre dans l'emplacement.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    Pour vous �vitez de chercher les r�f�rences au texte d'origine dans le contexte, vous pouvez d�finir certains attributs du contexte par la nature du texte d'origine (ex : d�finir le genre/nombre du contexte par le genre/nombre du nom lui m�me). Ce n'est pas g�nant, juste plus limitatif pour le remplacement.</div>

<p>Les attributs sur le texte d'origine et le remplacement qui ne sont pas directement d�finis par le contexte sont � d�finir dans <strong><fmt:message key="articleTemplate.edit.placeholderModal.definition.title" /></strong> et <strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong>.</p>
<p>Pour la d�finition du texte d'origine, il suffit de le regarder. Pour la d�finition du remplacement, il faut regarder le contexte ce qui peut demander du travail.</p>
<p>La d�finition du texte d'origine pr�-configure le remplacement avec au moins les m�mes valeurs (un texte de telle nature est au moins rempla�able par un texte de m�me nature), ce qui peut �tre satisfaisant.</p>
<p>Maintenant, la d�finition du texte d'origine est facultative, donc on peut aussi renseigner directement le remplacement. � vous de voir.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Important&nbsp;:</strong> 
    Si vous d�finissez le texte d'origine, attention � ne pas renseigner de fausse info dans la d�finition (une caract�ristique qu'on veut pour le remplacement mais qui ne correspond pas au texte d'origine).</div>



<!--<p>La fen�tre d'infos sur l'emplacement a pour but de d�finir par quoi le texte de l'emplacement peut �tre remplac�. 
    Pour cela, il est demand� de renseigner les infos dans les sections <strong><fmt:message key="articleTemplate.edit.placeholderModal.context.title" /></strong> et <strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong>.</p>
<p>Le texte rempla�ant (tout comme le texte d'origine) est d�fini par son contexte (le texte autour) ; 
    les choses importantes et formalisables (comme ce qui impacte les accords) sont demand�es dans la section <strong><fmt:message key="articleTemplate.edit.placeholderModal.context.title" /></strong>.
    Pour le reste (ce qui est difficilement formalisable ou r�sulte d'un choix personnel), il faut d�finir les caract�ristiques possibles du remplacement dans la section <strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong>.</p>


<p>Il est propos� de renseigner optionnellement la nature du texte d'origine (section <strong><fmt:message key="articleTemplate.edit.placeholderModal.definition.title" /></strong>). D�finir le texte d'origine pr�-configure le remplacement (un texte de telle nature est au moins rempla�able par un texte de m�me nature).
    Ce n'est pas n�cessaire de le faire mais vous pouvez donc configurer le remplacement via la d�finition du texte d'origine si �a semble plus facile. Note : si vous faites cela, attention � ne pas renseigner de fausse info dans la d�finition (une caract�ristique qu'on veut pour le remplacement mais qui ne correspond pas au texte d'origine).</p>-->


<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-reusable">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-reusable"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>R�utilisable</a>
</h4>
<p>Renseigner les infos du texte d'origine avait pour but initialement de constituer une base donn�es de portions de texte pour d'�ventuelles features de remplacements automatiques (note : ne pas consid�rer que ces �volutions arriveront).</p>
<p>Cependant il y a de nombreux cas o� le texte d'origine ne peut ou ne devrait pas �tre r�utilis� ailleurs ; on peut aussi vouloir cr�er un emplacement d'un certain type qui ne correspond pas au texte d'origine mais dont le remplacement fonctionnera avec le contexte (ex : un syntagme nominal en guise de COD � la place d'un compl�ment circonstanciel).</p>
<p>D�cocher la case <strong><fmt:message key="articleTemplate.edit.placeholderModal.origText.reusableFlagLabel" /></strong> pour cela.</p>


<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-from-another">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-from-another"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Emplacements bas�s sur un autre</a>
</h4>

<p>Vous pouvez cr�er un emplacement bas� sur un emplacement existant dans l'article : il sera remplac� par le m�me texte.</p>
<p>Cela est utile pour remplacer un groupe de mots qui apparait plusieurs fois dans l'article par la m�me chose.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> Seuls les emplacements "de base" (c'est � dire non bas� sur un autre) sont compt�s pour augmenter votre cr�dit de remplacements � effectuer.</div>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-conseils">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-conseils"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Conseils</a>
</h4>

<p>Lorsque vous remplacez le sujet principal de l'article, attention � ne pas laisser de texte qui y fait trop allusion, sans quoi l'effet est cass�.</p>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article-common-attr">&nbsp;</span>
        <a href="#HSEC-prep-article-common-attr"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Attributs communs</a>
    </h3>
</div>

<p>Certains attributs sont communs aux diff�rents types d'emplacement.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-common-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-n-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-cc-attr-cref">&nbsp;</span>
    <a href="#HSEC-prep-article-common-attr-cref"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>R�f�rents potentiels</a>
</h4>

<p>Les r�f�rents potentiels sont des �l�ments du contexte dont on signifie l'existence ce qui permet d'y faire r�f�rence dans le texte de l'emplacement au travers de pronoms ou d'accords.</p>
<p>Ex : <cite>Ma m�re entame <u>le paquet de chips qu'elle a achet�</u>.</cite> On aurait pas pu mettre la subordonn�e "qu'elle a achet�" si on ne savait pas qu'il existait une telle personne. Un 2�me r�f�rent potentiel ici est "moi" (donc 1�re personne du singulier) ce qui aurait permis d'avoir le texte <cite>Ma m�re entame <u>le paquet de chips que j'ai achet�</u>.</cite></p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Conseil&nbsp;:</strong> 
    Pas besoin de renseigner cet attribut dans le cas g�n�ral. Il faudra surtout le faire si le texte d'origine fait ce genre de r�f�rences (afin qu'il ne puisse pas �tre r�utilis� dans un contexte o� ces r�f�rents n'existent pas).</div>

<p>Cette fonction est surtout utile pour la 3�me personne. En cas de 3�me personne on doit indiquer genre/nombre/type.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    On peut consid�rer que les 1�res et 2�mes personnes sont toujours r�f�ren�ables, "pour la blague". Ex : <cite>il allait chez <u>ta m�re</u>.</cite> Cependant, on peut vouloir s�lectionner ces personnes pour assurer qu'elles sont r�f�ren�ables.</div>
<p>Cet attribut de contexte peut accepter plusieurs valeurs (sauf pour les Noms), mais il peut n'y avoir qu'un seul r�f�rent 3�me personne sans quoi on ne saurait pas � qui s'appliquent les attributs sp�cifiques � la 3�me personne (genre/type).</p>
<p>Les Noms n'accepent qu'un seul r�f�rent pour qu'en cas de d�terminant possessif on sache la personne de ce d�terminant.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-common-attr-defonly">&nbsp;</span>
    <a href="#HSEC-prep-article-common-attr-defonly"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Attributs sur le texte</a>
</h4>

<p>Les attributs permettant de <a href="#HSEC-replace-integration">r�int�grer un texte</a> sont demand�s pour le texte d'origine (sauf si on le marque comme non r�utilisable) ainsi que lors de la saisie du texte rempla�ant.</p>

<div class="page-header">
    <h2 class="help-title">
        <span class="help-anchor" id="HSEC-replace">&nbsp;</span>
        <a href="#HSEC-replace"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Remplacer</a>
    </h2>
</div>

<p>Apr�s avoir finalis� un article, vous pouvez effectuer des remplacements (� condition qu'il y ait des emplacements � remplacer disponibles).</p>
<p>Cliquer sur le menu <strong><fmt:message key="nav.replace" /></strong> pour effectuer le remplacement d'un emplacement. La pastille dans ce menu indique combien vous pouvez en faire.</p>
<p>En validant votre texte, on vous propose un autre remplacement � effectuer, ou on vous redirige sur l'article final si un article a �t� compl�t�.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    Il est possible qu'on demande certaines infos qui permettent de r�int�grer le texte dans l'article (voir plus bas).</div>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-replace-integration">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-n-attr-ha">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-sn-attr-ha">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-ha">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-cc-attr-ha">&nbsp;</span>
        <a href="#HSEC-replace-integration"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>R�int�gration du texte</a>
    </h3>
</div>

<p>R�int�grer un texte dans un contexte peut n�cessiter d'alt�rer ce texte ou le contexte. Il s'agit principalement de g�rer l'<strong>�lision</strong> (<cite>le</cite> devient <cite>l'</cite>) ou l'op�ration inverse (<cite>l'</cite> devient <cite>le</cite>).</p>
<p>L'�lision est normalement bien g�r�e dans les diff�rents cas. Pour les mots commen�ant par un "h" on demande � celui qui d�finit le texte si ce h est <em>aspir�</em> ou <em>muet</em>.</p>
<p>Les <strong>contractions</strong> de+le et assimil�s sont normalement g�r�s. Par contre la d�contraction de "du" ("de" d'appartenance + le) et assimil�s n'est pas possible avec le type <em>syntagme nominal</em>. Si vous avez ce genre de cas : <cite>la cravatte du pr�sident</cite>, vous ne pourrez cr�er qu'un emplacement de type <em>nom</em> pour <cite><u>pr�sident</u></cite> (introduit par d�terminant <em>d�fini</em>, et non <em>partitif</em>).</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-replace-integration-maj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-n-attr-kmj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-kmj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-kmj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-cc-attr-kmj">&nbsp;</span>
    <a href="#HSEC-replace-integration-maj"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Gestion des majuscules</a>
</h4>

<p>Le texte de l'emplacement peut comporter des majuscules mais il faut indiquer au syst�me quoi en faire</p>
<ul>
    <li>On garde tel quel (ex : noms propres)</li>
    <li>On enl�ve la majuscule du premier mot (utile dans la pr�partion d'article o� le texte d'origine peut contenir la majuscule de d�but de phrase)</li>
    <li>On enl�ve tout (le texte est en majuscules � tort)</li>
</ul>



<div class="page-header">
    <h2 class="help-title">
        <span class="help-anchor" id="HSEC-st">&nbsp;</span>
        <a href="#HSEC-st"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Types grammaticaux</a>
    </h2>
</div>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-n">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-n-attr-cgenre">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-n-attr-cnombre">&nbsp;</span>
        <a href="#HSEC-st-n"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Nom commun</a>
    </h3>
</div>

<p>Nom commun sans son d�terminant. Exemple : <u>carottes</u> dans la phrase <cite>Les <u>carottes</u> sont cuites</cite>.</p>
<p>Au lieu de demander le genre/nombre du nom, on demande de pr�ciser le genre/nombre des �l�ments qui y font r�f�rence dans le contexte (cela dit, on peut donner le genre/nombre du nom directement pour s'�viter du travail).</p> 
<p>Le d�terminant est l'�l�ment principal qui indique le genre/nombre (ici "les" indique que c'est pluriel). L'accord dans "cuites" indique en plus que c'est f�minin.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-n-attr-cdet">&nbsp;</span>
    <a href="#HSEC-st-n-attr-cdet"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>D�terminant</a>
</h4>
<p>On demande le type de d�terminant dans le contexte, car cela peut impacter le choix du texte rempla�ant (surtout pour les compl�ments)</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    Cet attribut peut accepter plusieurs valeurs puisque le nom peut se retrouver � plusieurs endroits avec la fonction "utiliser un emplacement existant".</div>
<ul>
    <li><strong>ind�fini</strong> : n'importe quel d�terminant ind�fini sauf partitif</li>
    <li><strong>article partitif</strong> : n�cessite que le nom soit (puisse �tre) <em>massif/non comptable</em> (ex : <cite>du <u>jus de raisin</u></cite>). Attention � ne pas confondre avec la contraction de "de" + article d�fini</li>
    <li><strong>article d�fini</strong> : l'article d�fini "le/la/les" sp�cifiquement</li>
    <li><strong>adjectif possessif</strong> : les d�terminants comme "mon/ton/son", le possesseur �tant d�termin� par le <em>r�f�rent potentiel</em></li>
</ul>
<p>L'article d�fini appelle pratiquement dans tous les cas � rajouter un <em>compl�ment du nom</em> plus ou moins <em>d�terminatif</em> (ex : <cite>le <u>chateau de ma m�re</u></cite>). Si les compl�ments sont interdits cela peut �tre un de ces cas&nbsp;:</p>    
<ul>
    <li>nom en compl�ment d'un autre nom</li>
    <li>nom d�j� utilis� dans le texte</li>
    <li>nom g�n�ral (ex : <cite>il aime le <u>sport</u></cite>)</li>
</ul>
<p>On �vitera de cr�er des emplacements <em>nom</em> introduits par les d�terminants d�finis qui manquent (principalement "d�monstratif"). C'est acceptable de le faire si on r�utilise un emplacement existant comme dans ce genre de cas : <cite>un <u>xxx</u>, blablala, ce <u>xxx</u> blabla.</cite> ; s�lectionner le d�terminant ind�fini + article d�fini dans ce cas.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-n-attr-ccompl">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-n-attr-compl">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-ccompl">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-compl">&nbsp;</span>
    <a href="#HSEC-st-n-attr-compl"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Compl�ments</a>
</h4>

<p>Le nom peut �tre accompagn� de compl�ments :</p>
<ul>
    <li><em>�pith�tes</em> (adjectifs qualificatifs)</li>
    <li><em>compl�ments du nom pr�positionnels</em> (ex : "de ...", "� ...")</li>
    <li><em>subordonn�es relatives</em> (ex : "qui ...", "que ...")</li>
</ul>
<p>Les compl�ments formant une <strong>locution</strong> avec le nom peuvent, dans une certaine mesure, ne faire qu'un avec le nom et ne pas �tre consid�r�s comme des compl�ments. Cela est laiss� � l'appreciation des utilisateurs. Ex : <cite>fer � cheval</cite> peut �tre consid�r� comme sans compl�ments.</p>

<p>&nbsp;</p>
<p>La personne qui d�finit l'emplacement peut d�finir si l'emplacement est suivi de compl�ments dans le contexte, cela est sens� dissuader d'en rajouter, mais on peut quand m�me le faire.</p>
<p>En plus de �a, la personne qui d�finit l'emplacement peut indiquer si le remplacement peut/doit ou ne doit pas en comporter. On peut vouloir qu'il n'y en ait pas pour que ce soit succinct ou on peut vouloir qu'il y en ait pour que le texte soit cons�quent.</p>

<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    On ne peut pas indiquer si dans le contexte il y a des �pith�tes en amont de l'emplacement (<cite>le petit <u>xxx</u></cite>), il faudra par d�faut consid�rer qu'il peut y en avoir dans le contexte.</div>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-n-attr-type">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-type">&nbsp;</span>
    <a href="#HSEC-st-n-attr-type"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Type</a>
</h4>

<p>On propose une classifiation s�mantique des noms communs au travers du <strong>type</strong> car les traits grammaticaux (genre/nombre) ne suffisent pas � d�finir par quoi on peut remplacer un nom.</p>
<ul>
    <li><strong>Anim�</strong>&nbsp;: d�signe g�n�ralement des personnes vivantes mais plus g�n�ralement des entit�s pouvant agir, comme des groupements de personnes ou des animaux dans une certaine mesure. Ex : <cite>le professeur</cite>, <cite>la CGT</cite>. Un moyen m�motechnique pour savoir est de faire une phrase du genre <cite>le xxx avec qui j'ai fait ceci</cite> ; si le nom n'est pas anim�, il faudra dire <em>avec lequel</em>.</li>
    <li><strong>Inanim�</strong>&nbsp;: objets concrets qui ne sont pas dans la cat�gorie Anim�s. Ex : <cite>un v�lo</cite>, <cite>un accoudoir</cite>.</li>
    <li><strong>Abstrait</strong>&nbsp;: choses immat�rielles, noms d'action et tout le reste. Ex : <cite>un documentaire animalier</cite>, <cite>une prouesse technique</cite>. Note : des choses non palpables ou qui ne font pas partie du monde r�el peuvent �tre consid�r� comme inanim�s plut�t qu'abstraits car assimilable � des objets, c'est � l'appr�ciation de l'utilisateur.</li>
</ul>
<p>Cette classification peut se retrouver ailleurs. Si il n'y a pas Abstraits, ils sont inclus dans les Inanim�s.</p>


<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-sn">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-sn-attr-cgenre">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-sn-attr-cnombre">&nbsp;</span>
        <a href="#HSEC-st-sn"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Syntagme nominal</a> <small>(a.k.a. groupe nominal)</small>
    </h3>
</div>

<p>Nom commun avec son d�terminant ou plus g�n�ralement tout ce qui peut constituer par exemple un <em>sujet</em> ou un <em>COD</em> dans une phrase.</p>
<p>Exemple : <u>une carotte</u> est un syntagme nominal qui constitue le COD dans la phrase <cite>J'�pluche <u>une carotte</u> au calme.</cite></p>

<p>On doit pr�ciser le genre/nombre indiqu� par le contexte, mais il y a des chances que rien ne l'indique vu que le d�terminant n'est plus dans le contexte.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> Attention aux indications du genre/nombre dans les autres phrases. Ex : <cite><u>Une carotte</u> roule sur la table. <u>Elle</u> est tomb�<u>e</u> par terre.</cite></div>
<p>Comme pour les noms on peut aussi mettre le genre/nombre du nom directement sans chercher dans le contexte, c'est juste potentiellement plus limitatif.</p>

<p>&nbsp;</p>
<p>M�me gestion des types que pour les Noms.</p>
<p>M�me gestion des compl�ments que pour les Noms.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-sn-attr-det">&nbsp;</span>
    <a href="#HSEC-st-sn-attr-det"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>D�terminant</a>
</h4>

<p>Un type de d�terminant est peut �tre pr�f�rable dans un contexte donn�, donc on peut le d�finir. Par d�faut, on peut d�finir celui du texte d'origine ce qui mettra la m�me chose pour le remplacement.</p>
<ul>
    <li><strong>Ind�fini</strong> : tous les d�terminants ind�finis sauf certains adjectifs sp�ciaux (aucun, nul). Pensez � la diversit� possible qu'on a avec les quantificateurs (ex : <cite>des montagnes de ...</cite>)</li>
    <li><strong>D�fini</strong> : l'article d�fini et les adjectifs possessifs uniquement. Comme pour les noms on �vitera les autres d�terminant d�finis (ex : d�monstratif). Pour les possessifs, on se basera sur les "r�f�rents potentiels" renseign�s.</li>
</ul>
<p>Un syntagme nominal peut aussi �tre constitu� d'un nom propre sans d�terminant, ces cas l� sont assimil�s aux noms avec d�terminant d�fini.</p>


<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-v">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-cc">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-forme">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-neg">&nbsp;</span>

        <a href="#HSEC-st-v"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Syntagme verbal</a> <small>(a.k.a. groupe verbal)</small>
    </h3>
</div>

<p>Il peut s'agir d'un verbe ou d'un syntagme verbal (c'est � dire le verbe et des satellites comme les compl�ments d'objet). Il peut constituer tout ou n'�tre qu'une partie du syntagme verbal du contexte.</p>
<p>Dans la phrase <cite>Je mange des chips au calme</cite>, on peut cr�er un emplacement pour juste le verbe (<cite>mange</cite>), inclure les CO (<cite>mange des chips</cite>) ou inclure jusqu'au compl�ment circonstanciel (<cite>mange des chips au calme</cite>).</p>
<p>On a le choix d'inclure ou de laisser dans le contexte ces �l�ments</p>
<ul>
    <li>le COD</li>
    <li>le pronom r�fl�chi en cas de verbe pronominal</li>
    <li>l'auxiliaire d'un temps compos� ou de la voix passive</li>
    <li>les marques de la n�gation</li>
    <li>les compl�ments circonstanciel</li>
</ul>
<p>Les COI ne sont pas g�r�s, il faudra les inclure dans le texte de l'emplacement : <cite>Il <u>t�l�phone � sa m�re</u></cite>. C'est une bonne id�e de faire de m�me pour les expressions / locutions, ex : <cite>Il <u>vomit son 4h</u></cite>.</p>
<p>On pr�f�re souvent cr�er un emplacement qu'� partir du verbe significatif et laisser tous les semi-auxiliaires : <cite>Il aimerait ne pas devoir <u>dormir dans la voiture</u></cite>.

<p>&nbsp;</p>
<p>Pour le verbe lui m�me, on doit indiquer pour le remplacement sous quelle forme on le veut (= � quel temps). Il peut s'agir de l'infinitif, d'un temps conjugu� ou du participe pass�.</p>
<p>S�lectionner une forme sp�ciale comme l'infinitif dans la d�finition du texte d'origine force � avoir un remplacement sous cette m�me forme. Pour un temps conjugu�, on est libre de choisir d'autres temps (� voir avec le contexte).</p>

<p>&nbsp;</p>
<p>Dans le remplacement on peut vouloir interdire ou demander de mettre des compl�ments circonstanciels, par exemple pour avoir un texte plut�t court ou plut�t cons�quent</p>
<p>Dans le remplacement on peut vouloir interdire les marques de n�gation, typiquement parce qu'il y en a d�j� dans le contexte ou parce que �a ne s'y pr�te pas ; ou inversement demander de les mettre (par exemple si le COD qui suit est introduit par un d�terminant n�gatif).</p>
<p>Pour ces attributs, d�finissez le remplacement � partir de la d�finition du texte d'origine pour ne pas avoir � r�flechir.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-csujet">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-csujetp">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-csujett">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-csujetg">&nbsp;</span>
    <a href="#HSEC-st-v-attr-csujet"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Sujet</a>
</h4>

<p>Au lieu d'indiquer comment conjuguer le verbe, on donne la nature du sujet (principalement la Personne).</p>
<p>M�me si des infos sur le sujet ne sont pas utiles pour la conjugaison (ex : � l'infinitif), cela est utile pour d'�ventuelles r�f�rences dans les compl�ments. Dans <cite>Elle veut <u>manger le paquet de chips qu'elle a achet�</u></cite>, on avait besoin de conna�tre personne et genre du sujet.</p>
<p>Le genre est facultatif mais peut �tre utile pour toutes les personnes, notamment lorsqu'il y a un auxiliaire �tre (<cite>je <u>suis tomb�e dans un trou</u></cite>) mais pas que (<cite>je <u>saute comme un con</u></cite>). � noter qu'il y a une diff�rence entre le genre "inconnu" et le genre ind�fini : dans le premier cas on sait qu'on ne connait pas le genre et on peut choisir de mettre un accord �pic�ne (<cite>les personnes <u>sont tomb�e�s</u></cite>), dans le 2�me cas peut �tre que le genre est connu et il serait bizarre de mettre un accord �pic�ne.</p>
<p>Pour un sujet � la 3�me personne, il est n�cessaire de conna�tre le Type (m�me classification que les Noms) : un sujet Inanim� limite beaucoup les choix de verbes (sauf � la voix passive).</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-ccod">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-ccodip">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-ccodig">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-ccodit">&nbsp;</span>
    <a href="#HSEC-st-v-attr-ccod"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>COD dans le contexte</a>
</h4>

<p>Pour l'attribut "COD", s�lectionner "Oui" si il y a un syntagme nominal apr�s l'emplacement (comme dans <cite>je <u>mange</u> des chips</cite>). Ce syntagme nominal est normalement le COD (et impliquerait donc d'avoir un verbe transitif sans CO dans le texte de l'emplacement).</p>
<p>Cependant dans une certaine mesure cette fonction peut �tre d�tourn�e et on peut uniquement prendre en compte le fait qu'il y a un syntagme nominal apr�s l'emplacement : <cite>je <u>profite de la vie tout en roulant sur</u> des chips</cite>. R�server cette pratique plut�t pour la cr�ation d'emplacement sur le texte d'origine et �viter de le faire dans la phase de remplacement.</p>
<p>&nbsp;</p>
<p>S�lectionner "Pronominal" si le COD est dans le contexte en amont de l'emplacement (ex : <cite>je la <u>mange</u></cite>). Il sera n�cessaire alors de pr�ciser la personne du pronom ainsi que le genre. Le genre est utile pour l'accord d'un participe pass� (<cite>je l'<u>ai mang�e</u></cite>) ou pour des r�f�rences dans les compl�ments (<cite>je t'<u>appelle m�me si tu es partie</u></cite>).</p>
<p>On peut utiliser cette fonction pour les COI si c'est le m�me pronom que les COD. Dans <cite>je te <u>parle</u></cite>, <cite>te</cite> est un COI, mais �a ne fonctionne pas � la 3�me personne (<cite>je lui parle</cite>)</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-cpr">&nbsp;</span>
    <a href="#HSEC-st-v-attr-cpr"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Pronom r�fl�chi</a>
</h4>

<p>On peut indiquer que le contexte contient un pronom r�fl�chi ("se"), ce qui n�cessite g�n�ralement d'avoir un verbe pronominal dans l'emplacement (<cite>il se <u>suicide</u></cite>).</p>
<p>Si il n'est pas dans le contexte, on peut potentiellement le mettre dans le texte de l'emplacement (<cite>il <u>se suicide</u></cite>).</p>
<p>Ne pas oublier que le pronom reflechi change avec la personne du sujet (me/te/nous/vous). Si le pronom ne correspond pas au sujet, c'est un COD/COI pronominal.</p>
<p>Dans un registre familier le pronom r�fl�chi peut aussi agir comme une sorte de compl�ment pour dire "soi m�me" et donc s'appliquer � un verbe non pronominal qui peut aussi avoir un COD <cite>je m'<u>enfile le paquet de chips en entier</u></cite>.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-cpp">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-cppa">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-cppae">&nbsp;</span>
    <a href="#HSEC-st-v-attr-cpp"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Temps compos�s et voix passive</a>
</h4>

<p>Pour les temps compos�s et la voix passive, l'auxiliaire peut se trouver dans l'emplacement (<cite>il <u>a mang�</u></cite>) ou dans le contexte (<cite>il a <u>mang�</u></cite>). Dans le 1er cas on choisit la forme correspondant au temps compos� (comme Pass� compos�), ou au temps qui convient pour la voix passive. Dans le 2�me cas on choisit la forme Participe pass� et on pr�cise l'auxiliaire qui est utilis� dans le contexte (avoir/�tre) et le cas d'utilisation (voix active ou passive).</p>
<p>Il est souvent pr�f�rable de laisser l'auxiliaire dans le contexte car il peut �tre s�par� du participe par des adverbes et des maques de n�gation (<cite>il n'a �videmment pas <u>mang�</u> les chips</cite>). �a a aussi comme avantage de g�rer n'importe quel temps compos� (tandis que la liste des temps compos�s est limit�e pour l'attribut Forme).</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> 
    Ne pas confondre temps compos� et voix passive : <cite>il <u>est parti sans rien dire</u></cite> est du pass� compos� ; <cite>il <u>est malmen� dans les sondages</u></cite> est du pr�sent � la voix passive (et peut donc �tre remplac� par un verbe au pr�sent � la voix active)</div>
<p>Les temps compos�s sont g�n�ralement avec l'auxiliaire avoir, mais quelques verbes utilisent l'auxiliaire �tre. Si il y a un pronom r�fl�chi, l'auxiliaire �tre est utilis� dans tous les cas.</p>
<p>La voix passive utilise l'auxiliaire �tre, ou <cite>avoir �t�</cite> pour les temps compos�s. Mettre en situation avec <cite>avoir �t�</cite> est un bon moyen pour s'assurer que c'est de la voix passive (<cite>il a �t� <u>mang�</u></cite>).</p>
<p>En cas de voix passive, on demande de pr�ciser si l'emplacement est suivi d'un compl�ment d'agent (normalement introduit par "par") car c'est souvent le cas et peut limiter le choix des verbes (<cite>il a �t� <u>maltrait�</u> par ses parents</cite>).</p>


<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-cc">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-cc-attr-type">&nbsp;</span>

        <a href="#HSEC-st-cc"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Compl�ment circonstanciel</a>
    </h3>
</div>

<p>Compl�ment circonstanciel de lieu, de temps, de mani�re ou autres.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> 
    Pour les CC de temps, �viter ce qui ne fonctionne qu'avec des verbes au futur ou pass�, ex : <cite>demain</cite>, <cite>hier</cite>, <cite>il y a 1 heure</cite>, <cite>dans 1 heure</cite></div>
<p>La seule info demand�e hormis le type est un r�f�rent potentiel pour lequel on mettra typiquement <strong>le sujet du verbe associ�</strong>. Ex : dans <cite>Je mange des chips <u>xxx</u></cite>, on mettra la 1�re personne du singulier comme r�f�rent potentiel, ce qui permettra d'avoir par exemple <cite><u>dans ma voiture</u></cite>.</p>


<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
