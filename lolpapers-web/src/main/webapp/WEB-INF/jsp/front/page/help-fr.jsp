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
        <a href="#HSEC-general"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Fonctionnement général</a>
    </h2>
</div>

<ul>
    <li>Vous devez être <a href="${myPage.userConnectUrl}">connecté</a> pour préparer un article ou effectuer le remplacement. Actuellement seule la connexion via Twitter est possible.</li>
    <li>Vous pouvez préparer jusqu'à <strong>${myPage.nbArticleTemplateByDay}</strong> articles par jour.</li>
    <li>Les articles de base proviennent d'une liste de flux RSS qui pourra évoluer avec le temps.</li>
    <li>Une fois que vous avez terminé la préparation d'un article, vous pouvez effectuer autant de remplacements qu'il y avait d'emplacements de base dans votre article.</li>
    <li>La préparation d'un article et le remplacement nécessitent de connaître les bases de la grammaire française (notions de base, accords en genre et en nombre, conjugaison des verbes) ; les choses plus techniques sont expliquées avec des bulles d'aide ainsi que dans cette page.</li>
    <li>Si en effectuant un remplacement vous complétez un article, vous serez redirigé sur l'article final.</li>
</ul>
    
<div class="page-header">
    <h2 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article">&nbsp;</span>
        <a href="#HSEC-prep-article"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Préparation d'article</a>
    </h2>
</div>

<p>Vous pouvez préparer un article en allant dans le menu <strong><fmt:message key="nav.create" /></strong> &gt; <strong><fmt:message key="nav.create.article" /></strong> &gt; <strong><fmt:message key="nav.create.article.new" /></strong>, 
    dans la limite autorisée par jour et dans la limite des articles de base disponibles par rapport aux sources d'articles (RSS).</p>
<p>Vous pouvez retrouver dans le menu <strong><fmt:message key="nav.create" /></strong> &gt; <strong><fmt:message key="nav.create.article" /></strong> les articles entamés. Pensez à <strong>rejeter</strong> les articles que vous ne comptez pas terminer.</p>
<p>La première fois que vous éditez un article, une aide de démarrage rapide s'affiche. Si elle n'est plus affichée, vous pourrez la retrouver dans le menu <strong><fmt:message key="articleTemplate.edit.nav.help.tuto" /></strong> &gt; <strong><fmt:message key="articleTemplate.edit.nav.help.tuto" /></strong>.</p>
<p>Comme le travail de préparation peut être long, vous avez la possibilité de sauvegarder les modifications avec le bouton <strong><fmt:message key="articleTemplate.edit.nav.save" /></strong>.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Important&nbsp;:</strong> Si vous quittez la page sans avoir sauvegardé, les modifications sont perdues.</div>
<p>Cliquez sur le bouton <strong><fmt:message key="articleTemplate.edit.nav.finish" /></strong> pour terminer l'article.</p>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article-content">&nbsp;</span>
        <a href="#HSEC-prep-article-content"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Préparation du contenu et rejet</a>
    </h3>
</div>

<p>Pour chaque article de base, le système essaie d'identifier le contenu principal de l'article mais peut échouer en partie ou complètement.</p>
<p>Dans l'édition de l'article vous avez la possibilité de rejeter l'article pour mauvais contenu avec le menu <strong><fmt:message key="articleTemplate.edit.nav.cancel" /></strong> &gt; <strong><fmt:message key="articleTemplate.edit.nav.cancel.rejectWrongContent" /></strong>, il ne sera alors plus proposé à personne.</p>
<p>Vous avez aussi la possibilité de rejeter l'article si la prépartion de ce dernier ne vous inspire pas, avec le menu <strong><fmt:message key="articleTemplate.edit.nav.cancel" /></strong> &gt; <strong><fmt:message key="articleTemplate.edit.nav.cancel.delete" /></strong>, mais il risque d'être proposé à nouveau (à vous ou à qqun d'autre).</p>
<p>Si l'article convient globalement, vous pouvez ensuite supprimer des portions de texte comme expliqué ci-dessous.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-content-descr">&nbsp;</span>
    <a href="#HSEC-prep-article-content-descr"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Gestion de la description</a>
</h4>
<p>La description apparaît dans les listings d'articles ainsi que dans l'article lui même.</p>
<p>Si il n'y en a pas, le système utilisera le début du contenu de l'article dans les listings.</p>
<p>Vous pouvez (et devriez) rejeter la description avec le bouton proposé si elle fait doublon avec le début de l'article ou si le texte n'est pas propice à faire une bonne description.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-content-erase-mode">&nbsp;</span>
    <a href="#HSEC-prep-article-content-erase-mode"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Mode suppression</a>
</h4>
<p>Supprimer des blocs de texte dans le corps de l'article a les utilités suivantes :</p>
<ul>
    <li>Enlever ce qui ne constitue pas du texte d'article, comme des liens "voir aussi", des signatures, des lignes de données sur l'article d'origine (auteur, date..)</li>
    <li>Pour réduire la longueur de l'article afin que ce soit moins long pour vous.</li>
</ul>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> La délimitation des blocs de texte est basée sur la structure html de l'article, parfois tout le texte est dans un seul bloc ce qui empêche d'utiliser cette fonctionalité.</div>
<p>En supprimant du texte, le nombre de mots diminue, faites attention à ne pas être en dessous du minimum (actuellement : <strong>${myPage.minArticleWc}</strong>).</p>
<p>Cliquer sur le bouton <strong><fmt:message key="articleTemplate.edit.nav.eraseMode" /></strong> pour passer en mode suppression, puis cliquer sur les blocs de texte pour les supprimer ou annuler leur suppression. Cliquer sur <strong><fmt:message key="articleTemplate.edit.nav.eraseMode" /></strong> pour revenir en mode normal.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> La suppression de blocs de texte supprime les éventuels emplacements créés dedans.</div>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article-placeholders">&nbsp;</span>
        <a href="#HSEC-prep-article-placeholders"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Création des emplacements</a>
    </h3>
</div>

<p>Vous devez créer un certain nombre d'emplacements avant de finaliser l'article, le nombre minimum est affiché dans la barre d'outils (vous pouvez bien sûr en faire plus que le minimum).</p>
<p>Actuellement il doit y avoir un emplacement minimum tous les <strong>${myPage.maxWordsByArticleTemplatePlaceholder}</strong> mots en moyenne et au minimum <strong>${myPage.minArticleTemplatePlaceholders}</strong> emplacements.</p>
<p>&nbsp;</p>
<p>Pour créer un emplacement, il faut d'abord sélectionner le texte qui va être remplacé. Pour se faire cliquer sur un mot puis étendre la sélection en cliquant sur les mots à gauche ou à droite du mot.</p>
<p>Ensuite cliquer sur le type grammatical du texte sélectionné pour créer l'emplacement. On ne peut pas changer le type ou l'étendue de la sélection après ça (il faut supprimer et recréer l'emplacement).</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-popup">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-popup"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Définition de l'emplacement</a>
</h4>

<p>La définition d'un emplacement passe par le renseignement d'attributs (qui sont spécifiques au type d'emplacement). Chaque attribut a un ensemble de valeurs qu'on peut sélectionner/déselectionner en cliquant dessus. Certains attributs limitent ou préselectionnent d'autres.</p>
<p>Il y a 3 sections d'attributs&nbsp;:</p>
<ul>
    <li><strong><fmt:message key="articleTemplate.edit.placeholderModal.context.title" /></strong> : attributs concernant le texte autour de l'emplacement (potentiellement tout l'article).</li>
    <li><strong><fmt:message key="articleTemplate.edit.placeholderModal.definition.title" /></strong> : attributs concernant le texte d'origine.</li>
    <li><strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong> : attributs possibles pour le texte remplaçant. On peut sélectionner plusieurs valeurs par attribut ici.</li>
</ul>

<p>Le contexte définit les possibilités pour le texte de l'emplacement (à la fois le texte d'origine et le remplacement). Exemple : parce qu'il y a des accords de tel genre/nombre dans le contexte, il doit y avoir un nom de tel genre/nombre dans l'emplacement.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    Pour vous évitez de chercher les références au texte d'origine dans le contexte, vous pouvez définir certains attributs du contexte par la nature du texte d'origine (ex : définir le genre/nombre du contexte par le genre/nombre du nom lui même). Ce n'est pas gênant, juste plus limitatif pour le remplacement.</div>

<p>Les attributs sur le texte d'origine et le remplacement qui ne sont pas directement définis par le contexte sont à définir dans <strong><fmt:message key="articleTemplate.edit.placeholderModal.definition.title" /></strong> et <strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong>.</p>
<p>Pour la définition du texte d'origine, il suffit de le regarder. Pour la définition du remplacement, il faut regarder le contexte ce qui peut demander du travail.</p>
<p>La définition du texte d'origine pré-configure le remplacement avec au moins les mêmes valeurs (un texte de telle nature est au moins remplaçable par un texte de même nature), ce qui peut être satisfaisant.</p>
<p>Maintenant, la définition du texte d'origine est facultative, donc on peut aussi renseigner directement le remplacement. À vous de voir.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Important&nbsp;:</strong> 
    Si vous définissez le texte d'origine, attention à ne pas renseigner de fausse info dans la définition (une caractéristique qu'on veut pour le remplacement mais qui ne correspond pas au texte d'origine).</div>



<!--<p>La fenêtre d'infos sur l'emplacement a pour but de définir par quoi le texte de l'emplacement peut être remplacé. 
    Pour cela, il est demandé de renseigner les infos dans les sections <strong><fmt:message key="articleTemplate.edit.placeholderModal.context.title" /></strong> et <strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong>.</p>
<p>Le texte remplaçant (tout comme le texte d'origine) est défini par son contexte (le texte autour) ; 
    les choses importantes et formalisables (comme ce qui impacte les accords) sont demandées dans la section <strong><fmt:message key="articleTemplate.edit.placeholderModal.context.title" /></strong>.
    Pour le reste (ce qui est difficilement formalisable ou résulte d'un choix personnel), il faut définir les caractéristiques possibles du remplacement dans la section <strong><fmt:message key="articleTemplate.edit.placeholderModal.replacement.title" /></strong>.</p>


<p>Il est proposé de renseigner optionnellement la nature du texte d'origine (section <strong><fmt:message key="articleTemplate.edit.placeholderModal.definition.title" /></strong>). Définir le texte d'origine pré-configure le remplacement (un texte de telle nature est au moins remplaçable par un texte de même nature).
    Ce n'est pas nécessaire de le faire mais vous pouvez donc configurer le remplacement via la définition du texte d'origine si ça semble plus facile. Note : si vous faites cela, attention à ne pas renseigner de fausse info dans la définition (une caractéristique qu'on veut pour le remplacement mais qui ne correspond pas au texte d'origine).</p>-->


<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-reusable">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-reusable"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Réutilisable</a>
</h4>
<p>Renseigner les infos du texte d'origine avait pour but initialement de constituer une base données de portions de texte pour d'éventuelles features de remplacements automatiques (note : ne pas considérer que ces évolutions arriveront).</p>
<p>Cependant il y a de nombreux cas où le texte d'origine ne peut ou ne devrait pas être réutilisé ailleurs ; on peut aussi vouloir créer un emplacement d'un certain type qui ne correspond pas au texte d'origine mais dont le remplacement fonctionnera avec le contexte (ex : un syntagme nominal en guise de COD à la place d'un complément circonstanciel).</p>
<p>Décocher la case <strong><fmt:message key="articleTemplate.edit.placeholderModal.origText.reusableFlagLabel" /></strong> pour cela.</p>


<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-from-another">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-from-another"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Emplacements basés sur un autre</a>
</h4>

<p>Vous pouvez créer un emplacement basé sur un emplacement existant dans l'article : il sera remplacé par le même texte.</p>
<p>Cela est utile pour remplacer un groupe de mots qui apparait plusieurs fois dans l'article par la même chose.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> Seuls les emplacements "de base" (c'est à dire non basé sur un autre) sont comptés pour augmenter votre crédit de remplacements à effectuer.</div>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-placeholders-conseils">&nbsp;</span>
    <a href="#HSEC-prep-article-placeholders-conseils"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Conseils</a>
</h4>

<p>Lorsque vous remplacez le sujet principal de l'article, attention à ne pas laisser de texte qui y fait trop allusion, sans quoi l'effet est cassé.</p>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-prep-article-common-attr">&nbsp;</span>
        <a href="#HSEC-prep-article-common-attr"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Attributs communs</a>
    </h3>
</div>

<p>Certains attributs sont communs aux différents types d'emplacement.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-common-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-n-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-cref">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-cc-attr-cref">&nbsp;</span>
    <a href="#HSEC-prep-article-common-attr-cref"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Référents potentiels</a>
</h4>

<p>Les référents potentiels sont des éléments du contexte dont on signifie l'existence ce qui permet d'y faire référence dans le texte de l'emplacement au travers de pronoms ou d'accords.</p>
<p>Ex : <cite>Ma mère entame <u>le paquet de chips qu'elle a acheté</u>.</cite> On aurait pas pu mettre la subordonnée "qu'elle a acheté" si on ne savait pas qu'il existait une telle personne. Un 2ème référent potentiel ici est "moi" (donc 1ère personne du singulier) ce qui aurait permis d'avoir le texte <cite>Ma mère entame <u>le paquet de chips que j'ai acheté</u>.</cite></p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Conseil&nbsp;:</strong> 
    Pas besoin de renseigner cet attribut dans le cas général. Il faudra surtout le faire si le texte d'origine fait ce genre de références (afin qu'il ne puisse pas être réutilisé dans un contexte où ces référents n'existent pas).</div>

<p>Cette fonction est surtout utile pour la 3ème personne. En cas de 3ème personne on doit indiquer genre/nombre/type.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    On peut considérer que les 1ères et 2èmes personnes sont toujours référençables, "pour la blague". Ex : <cite>il allait chez <u>ta mère</u>.</cite> Cependant, on peut vouloir sélectionner ces personnes pour assurer qu'elles sont référençables.</div>
<p>Cet attribut de contexte peut accepter plusieurs valeurs (sauf pour les Noms), mais il peut n'y avoir qu'un seul référent 3ème personne sans quoi on ne saurait pas à qui s'appliquent les attributs spécifiques à la 3ème personne (genre/type).</p>
<p>Les Noms n'accepent qu'un seul référent pour qu'en cas de déterminant possessif on sache la personne de ce déterminant.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-prep-article-common-attr-defonly">&nbsp;</span>
    <a href="#HSEC-prep-article-common-attr-defonly"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Attributs sur le texte</a>
</h4>

<p>Les attributs permettant de <a href="#HSEC-replace-integration">réintégrer un texte</a> sont demandés pour le texte d'origine (sauf si on le marque comme non réutilisable) ainsi que lors de la saisie du texte remplaçant.</p>

<div class="page-header">
    <h2 class="help-title">
        <span class="help-anchor" id="HSEC-replace">&nbsp;</span>
        <a href="#HSEC-replace"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Remplacer</a>
    </h2>
</div>

<p>Après avoir finalisé un article, vous pouvez effectuer des remplacements (à condition qu'il y ait des emplacements à remplacer disponibles).</p>
<p>Cliquer sur le menu <strong><fmt:message key="nav.replace" /></strong> pour effectuer le remplacement d'un emplacement. La pastille dans ce menu indique combien vous pouvez en faire.</p>
<p>En validant votre texte, on vous propose un autre remplacement à effectuer, ou on vous redirige sur l'article final si un article a été complété.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    Il est possible qu'on demande certaines infos qui permettent de réintégrer le texte dans l'article (voir plus bas).</div>

<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-replace-integration">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-n-attr-ha">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-sn-attr-ha">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-ha">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-cc-attr-ha">&nbsp;</span>
        <a href="#HSEC-replace-integration"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Réintégration du texte</a>
    </h3>
</div>

<p>Réintégrer un texte dans un contexte peut nécessiter d'altérer ce texte ou le contexte. Il s'agit principalement de gérer l'<strong>élision</strong> (<cite>le</cite> devient <cite>l'</cite>) ou l'opération inverse (<cite>l'</cite> devient <cite>le</cite>).</p>
<p>L'élision est normalement bien gérée dans les différents cas. Pour les mots commençant par un "h" on demande à celui qui définit le texte si ce h est <em>aspiré</em> ou <em>muet</em>.</p>
<p>Les <strong>contractions</strong> de+le et assimilés sont normalement gérés. Par contre la décontraction de "du" ("de" d'appartenance + le) et assimilés n'est pas possible avec le type <em>syntagme nominal</em>. Si vous avez ce genre de cas : <cite>la cravatte du président</cite>, vous ne pourrez créer qu'un emplacement de type <em>nom</em> pour <cite><u>président</u></cite> (introduit par déterminant <em>défini</em>, et non <em>partitif</em>).</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-replace-integration-maj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-n-attr-kmj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-kmj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-kmj">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-cc-attr-kmj">&nbsp;</span>
    <a href="#HSEC-replace-integration-maj"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Gestion des majuscules</a>
</h4>

<p>Le texte de l'emplacement peut comporter des majuscules mais il faut indiquer au système quoi en faire</p>
<ul>
    <li>On garde tel quel (ex : noms propres)</li>
    <li>On enlève la majuscule du premier mot (utile dans la prépartion d'article où le texte d'origine peut contenir la majuscule de début de phrase)</li>
    <li>On enlève tout (le texte est en majuscules à tort)</li>
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

<p>Nom commun sans son déterminant. Exemple : <u>carottes</u> dans la phrase <cite>Les <u>carottes</u> sont cuites</cite>.</p>
<p>Au lieu de demander le genre/nombre du nom, on demande de préciser le genre/nombre des éléments qui y font référence dans le contexte (cela dit, on peut donner le genre/nombre du nom directement pour s'éviter du travail).</p> 
<p>Le déterminant est l'élément principal qui indique le genre/nombre (ici "les" indique que c'est pluriel). L'accord dans "cuites" indique en plus que c'est féminin.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-n-attr-cdet">&nbsp;</span>
    <a href="#HSEC-st-n-attr-cdet"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Déterminant</a>
</h4>
<p>On demande le type de déterminant dans le contexte, car cela peut impacter le choix du texte remplaçant (surtout pour les compléments)</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    Cet attribut peut accepter plusieurs valeurs puisque le nom peut se retrouver à plusieurs endroits avec la fonction "utiliser un emplacement existant".</div>
<ul>
    <li><strong>indéfini</strong> : n'importe quel déterminant indéfini sauf partitif</li>
    <li><strong>article partitif</strong> : nécessite que le nom soit (puisse être) <em>massif/non comptable</em> (ex : <cite>du <u>jus de raisin</u></cite>). Attention à ne pas confondre avec la contraction de "de" + article défini</li>
    <li><strong>article défini</strong> : l'article défini "le/la/les" spécifiquement</li>
    <li><strong>adjectif possessif</strong> : les déterminants comme "mon/ton/son", le possesseur étant déterminé par le <em>référent potentiel</em></li>
</ul>
<p>L'article défini appelle pratiquement dans tous les cas à rajouter un <em>complément du nom</em> plus ou moins <em>déterminatif</em> (ex : <cite>le <u>chateau de ma mère</u></cite>). Si les compléments sont interdits cela peut être un de ces cas&nbsp;:</p>    
<ul>
    <li>nom en complément d'un autre nom</li>
    <li>nom déjà utilisé dans le texte</li>
    <li>nom général (ex : <cite>il aime le <u>sport</u></cite>)</li>
</ul>
<p>On évitera de créer des emplacements <em>nom</em> introduits par les déterminants définis qui manquent (principalement "démonstratif"). C'est acceptable de le faire si on réutilise un emplacement existant comme dans ce genre de cas : <cite>un <u>xxx</u>, blablala, ce <u>xxx</u> blabla.</cite> ; sélectionner le déterminant indéfini + article défini dans ce cas.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-n-attr-ccompl">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-n-attr-compl">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-ccompl">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-compl">&nbsp;</span>
    <a href="#HSEC-st-n-attr-compl"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Compléments</a>
</h4>

<p>Le nom peut être accompagné de compléments :</p>
<ul>
    <li><em>épithètes</em> (adjectifs qualificatifs)</li>
    <li><em>compléments du nom prépositionnels</em> (ex : "de ...", "à ...")</li>
    <li><em>subordonnées relatives</em> (ex : "qui ...", "que ...")</li>
</ul>
<p>Les compléments formant une <strong>locution</strong> avec le nom peuvent, dans une certaine mesure, ne faire qu'un avec le nom et ne pas être considérés comme des compléments. Cela est laissé à l'appreciation des utilisateurs. Ex : <cite>fer à cheval</cite> peut être considéré comme sans compléments.</p>

<p>&nbsp;</p>
<p>La personne qui définit l'emplacement peut définir si l'emplacement est suivi de compléments dans le contexte, cela est sensé dissuader d'en rajouter, mais on peut quand même le faire.</p>
<p>En plus de ça, la personne qui définit l'emplacement peut indiquer si le remplacement peut/doit ou ne doit pas en comporter. On peut vouloir qu'il n'y en ait pas pour que ce soit succinct ou on peut vouloir qu'il y en ait pour que le texte soit conséquent.</p>

<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> 
    On ne peut pas indiquer si dans le contexte il y a des épithètes en amont de l'emplacement (<cite>le petit <u>xxx</u></cite>), il faudra par défaut considérer qu'il peut y en avoir dans le contexte.</div>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-n-attr-type">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-sn-attr-type">&nbsp;</span>
    <a href="#HSEC-st-n-attr-type"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Type</a>
</h4>

<p>On propose une classifiation sémantique des noms communs au travers du <strong>type</strong> car les traits grammaticaux (genre/nombre) ne suffisent pas à définir par quoi on peut remplacer un nom.</p>
<ul>
    <li><strong>Animé</strong>&nbsp;: désigne généralement des personnes vivantes mais plus généralement des entités pouvant agir, comme des groupements de personnes ou des animaux dans une certaine mesure. Ex : <cite>le professeur</cite>, <cite>la CGT</cite>. Un moyen mémotechnique pour savoir est de faire une phrase du genre <cite>le xxx avec qui j'ai fait ceci</cite> ; si le nom n'est pas animé, il faudra dire <em>avec lequel</em>.</li>
    <li><strong>Inanimé</strong>&nbsp;: objets concrets qui ne sont pas dans la catégorie Animés. Ex : <cite>un vélo</cite>, <cite>un accoudoir</cite>.</li>
    <li><strong>Abstrait</strong>&nbsp;: choses immatérielles, noms d'action et tout le reste. Ex : <cite>un documentaire animalier</cite>, <cite>une prouesse technique</cite>. Note : des choses non palpables ou qui ne font pas partie du monde réel peuvent être considéré comme inanimés plutôt qu'abstraits car assimilable à des objets, c'est à l'appréciation de l'utilisateur.</li>
</ul>
<p>Cette classification peut se retrouver ailleurs. Si il n'y a pas Abstraits, ils sont inclus dans les Inanimés.</p>


<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-sn">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-sn-attr-cgenre">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-sn-attr-cnombre">&nbsp;</span>
        <a href="#HSEC-st-sn"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Syntagme nominal</a> <small>(a.k.a. groupe nominal)</small>
    </h3>
</div>

<p>Nom commun avec son déterminant ou plus généralement tout ce qui peut constituer par exemple un <em>sujet</em> ou un <em>COD</em> dans une phrase.</p>
<p>Exemple : <u>une carotte</u> est un syntagme nominal qui constitue le COD dans la phrase <cite>J'épluche <u>une carotte</u> au calme.</cite></p>

<p>On doit préciser le genre/nombre indiqué par le contexte, mais il y a des chances que rien ne l'indique vu que le déterminant n'est plus dans le contexte.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Note&nbsp;:</strong> Attention aux indications du genre/nombre dans les autres phrases. Ex : <cite><u>Une carotte</u> roule sur la table. <u>Elle</u> est tombé<u>e</u> par terre.</cite></div>
<p>Comme pour les noms on peut aussi mettre le genre/nombre du nom directement sans chercher dans le contexte, c'est juste potentiellement plus limitatif.</p>

<p>&nbsp;</p>
<p>Même gestion des types que pour les Noms.</p>
<p>Même gestion des compléments que pour les Noms.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-sn-attr-det">&nbsp;</span>
    <a href="#HSEC-st-sn-attr-det"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Déterminant</a>
</h4>

<p>Un type de déterminant est peut être préférable dans un contexte donné, donc on peut le définir. Par défaut, on peut définir celui du texte d'origine ce qui mettra la même chose pour le remplacement.</p>
<ul>
    <li><strong>Indéfini</strong> : tous les déterminants indéfinis sauf certains adjectifs spéciaux (aucun, nul). Pensez à la diversité possible qu'on a avec les quantificateurs (ex : <cite>des montagnes de ...</cite>)</li>
    <li><strong>Défini</strong> : l'article défini et les adjectifs possessifs uniquement. Comme pour les noms on évitera les autres déterminant définis (ex : démonstratif). Pour les possessifs, on se basera sur les "référents potentiels" renseignés.</li>
</ul>
<p>Un syntagme nominal peut aussi être constitué d'un nom propre sans déterminant, ces cas là sont assimilés aux noms avec déterminant défini.</p>


<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-v">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-cc">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-forme">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-v-attr-neg">&nbsp;</span>

        <a href="#HSEC-st-v"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Syntagme verbal</a> <small>(a.k.a. groupe verbal)</small>
    </h3>
</div>

<p>Il peut s'agir d'un verbe ou d'un syntagme verbal (c'est à dire le verbe et des satellites comme les compléments d'objet). Il peut constituer tout ou n'être qu'une partie du syntagme verbal du contexte.</p>
<p>Dans la phrase <cite>Je mange des chips au calme</cite>, on peut créer un emplacement pour juste le verbe (<cite>mange</cite>), inclure les CO (<cite>mange des chips</cite>) ou inclure jusqu'au complément circonstanciel (<cite>mange des chips au calme</cite>).</p>
<p>On a le choix d'inclure ou de laisser dans le contexte ces éléments</p>
<ul>
    <li>le COD</li>
    <li>le pronom réfléchi en cas de verbe pronominal</li>
    <li>l'auxiliaire d'un temps composé ou de la voix passive</li>
    <li>les marques de la négation</li>
    <li>les compléments circonstanciel</li>
</ul>
<p>Les COI ne sont pas gérés, il faudra les inclure dans le texte de l'emplacement : <cite>Il <u>téléphone à sa mère</u></cite>. C'est une bonne idée de faire de même pour les expressions / locutions, ex : <cite>Il <u>vomit son 4h</u></cite>.</p>
<p>On préfère souvent créer un emplacement qu'à partir du verbe significatif et laisser tous les semi-auxiliaires : <cite>Il aimerait ne pas devoir <u>dormir dans la voiture</u></cite>.

<p>&nbsp;</p>
<p>Pour le verbe lui même, on doit indiquer pour le remplacement sous quelle forme on le veut (= à quel temps). Il peut s'agir de l'infinitif, d'un temps conjugué ou du participe passé.</p>
<p>Sélectionner une forme spéciale comme l'infinitif dans la définition du texte d'origine force à avoir un remplacement sous cette même forme. Pour un temps conjugué, on est libre de choisir d'autres temps (à voir avec le contexte).</p>

<p>&nbsp;</p>
<p>Dans le remplacement on peut vouloir interdire ou demander de mettre des compléments circonstanciels, par exemple pour avoir un texte plutôt court ou plutôt conséquent</p>
<p>Dans le remplacement on peut vouloir interdire les marques de négation, typiquement parce qu'il y en a déjà dans le contexte ou parce que ça ne s'y prête pas ; ou inversement demander de les mettre (par exemple si le COD qui suit est introduit par un déterminant négatif).</p>
<p>Pour ces attributs, définissez le remplacement à partir de la définition du texte d'origine pour ne pas avoir à réflechir.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-csujet">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-csujetp">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-csujett">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-csujetg">&nbsp;</span>
    <a href="#HSEC-st-v-attr-csujet"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Sujet</a>
</h4>

<p>Au lieu d'indiquer comment conjuguer le verbe, on donne la nature du sujet (principalement la Personne).</p>
<p>Même si des infos sur le sujet ne sont pas utiles pour la conjugaison (ex : à l'infinitif), cela est utile pour d'éventuelles références dans les compléments. Dans <cite>Elle veut <u>manger le paquet de chips qu'elle a acheté</u></cite>, on avait besoin de connaître personne et genre du sujet.</p>
<p>Le genre est facultatif mais peut être utile pour toutes les personnes, notamment lorsqu'il y a un auxiliaire être (<cite>je <u>suis tombée dans un trou</u></cite>) mais pas que (<cite>je <u>saute comme un con</u></cite>). À noter qu'il y a une différence entre le genre "inconnu" et le genre indéfini : dans le premier cas on sait qu'on ne connait pas le genre et on peut choisir de mettre un accord épicène (<cite>les personnes <u>sont tombé·e·s</u></cite>), dans le 2ème cas peut être que le genre est connu et il serait bizarre de mettre un accord épicène.</p>
<p>Pour un sujet à la 3ème personne, il est nécessaire de connaître le Type (même classification que les Noms) : un sujet Inanimé limite beaucoup les choix de verbes (sauf à la voix passive).</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-ccod">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-ccodip">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-ccodig">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-ccodit">&nbsp;</span>
    <a href="#HSEC-st-v-attr-ccod"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>COD dans le contexte</a>
</h4>

<p>Pour l'attribut "COD", sélectionner "Oui" si il y a un syntagme nominal après l'emplacement (comme dans <cite>je <u>mange</u> des chips</cite>). Ce syntagme nominal est normalement le COD (et impliquerait donc d'avoir un verbe transitif sans CO dans le texte de l'emplacement).</p>
<p>Cependant dans une certaine mesure cette fonction peut être détournée et on peut uniquement prendre en compte le fait qu'il y a un syntagme nominal après l'emplacement : <cite>je <u>profite de la vie tout en roulant sur</u> des chips</cite>. Réserver cette pratique plutôt pour la création d'emplacement sur le texte d'origine et éviter de le faire dans la phase de remplacement.</p>
<p>&nbsp;</p>
<p>Sélectionner "Pronominal" si le COD est dans le contexte en amont de l'emplacement (ex : <cite>je la <u>mange</u></cite>). Il sera nécessaire alors de préciser la personne du pronom ainsi que le genre. Le genre est utile pour l'accord d'un participe passé (<cite>je l'<u>ai mangée</u></cite>) ou pour des références dans les compléments (<cite>je t'<u>appelle même si tu es partie</u></cite>).</p>
<p>On peut utiliser cette fonction pour les COI si c'est le même pronom que les COD. Dans <cite>je te <u>parle</u></cite>, <cite>te</cite> est un COI, mais ça ne fonctionne pas à la 3ème personne (<cite>je lui parle</cite>)</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-cpr">&nbsp;</span>
    <a href="#HSEC-st-v-attr-cpr"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Pronom réfléchi</a>
</h4>

<p>On peut indiquer que le contexte contient un pronom réfléchi ("se"), ce qui nécessite généralement d'avoir un verbe pronominal dans l'emplacement (<cite>il se <u>suicide</u></cite>).</p>
<p>Si il n'est pas dans le contexte, on peut potentiellement le mettre dans le texte de l'emplacement (<cite>il <u>se suicide</u></cite>).</p>
<p>Ne pas oublier que le pronom reflechi change avec la personne du sujet (me/te/nous/vous). Si le pronom ne correspond pas au sujet, c'est un COD/COI pronominal.</p>
<p>Dans un registre familier le pronom réfléchi peut aussi agir comme une sorte de complément pour dire "soi même" et donc s'appliquer à un verbe non pronominal qui peut aussi avoir un COD <cite>je m'<u>enfile le paquet de chips en entier</u></cite>.</p>

<h4 class="help-title">
    <span class="help-anchor" id="HSEC-st-v-attr-cpp">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-cppa">&nbsp;</span>
    <span class="help-anchor" id="HSEC-st-v-attr-cppae">&nbsp;</span>
    <a href="#HSEC-st-v-attr-cpp"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Temps composés et voix passive</a>
</h4>

<p>Pour les temps composés et la voix passive, l'auxiliaire peut se trouver dans l'emplacement (<cite>il <u>a mangé</u></cite>) ou dans le contexte (<cite>il a <u>mangé</u></cite>). Dans le 1er cas on choisit la forme correspondant au temps composé (comme Passé composé), ou au temps qui convient pour la voix passive. Dans le 2ème cas on choisit la forme Participe passé et on précise l'auxiliaire qui est utilisé dans le contexte (avoir/être) et le cas d'utilisation (voix active ou passive).</p>
<p>Il est souvent préférable de laisser l'auxiliaire dans le contexte car il peut être séparé du participe par des adverbes et des maques de négation (<cite>il n'a évidemment pas <u>mangé</u> les chips</cite>). Ça a aussi comme avantage de gérer n'importe quel temps composé (tandis que la liste des temps composés est limitée pour l'attribut Forme).</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> 
    Ne pas confondre temps composé et voix passive : <cite>il <u>est parti sans rien dire</u></cite> est du passé composé ; <cite>il <u>est malmené dans les sondages</u></cite> est du présent à la voix passive (et peut donc être remplacé par un verbe au présent à la voix active)</div>
<p>Les temps composés sont généralement avec l'auxiliaire avoir, mais quelques verbes utilisent l'auxiliaire être. Si il y a un pronom réfléchi, l'auxiliaire être est utilisé dans tous les cas.</p>
<p>La voix passive utilise l'auxiliaire être, ou <cite>avoir été</cite> pour les temps composés. Mettre en situation avec <cite>avoir été</cite> est un bon moyen pour s'assurer que c'est de la voix passive (<cite>il a été <u>mangé</u></cite>).</p>
<p>En cas de voix passive, on demande de préciser si l'emplacement est suivi d'un complément d'agent (normalement introduit par "par") car c'est souvent le cas et peut limiter le choix des verbes (<cite>il a été <u>maltraité</u> par ses parents</cite>).</p>


<div class="page-header">
    <h3 class="help-title">
        <span class="help-anchor" id="HSEC-st-cc">&nbsp;</span>
        <span class="help-anchor" id="HSEC-st-cc-attr-type">&nbsp;</span>

        <a href="#HSEC-st-cc"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Complément circonstanciel</a>
    </h3>
</div>

<p>Complément circonstanciel de lieu, de temps, de manière ou autres.</p>
<div class="help-conseil well well-sm"><strong class="help-conseil-head">Attention&nbsp;:</strong> 
    Pour les CC de temps, éviter ce qui ne fonctionne qu'avec des verbes au futur ou passé, ex : <cite>demain</cite>, <cite>hier</cite>, <cite>il y a 1 heure</cite>, <cite>dans 1 heure</cite></div>
<p>La seule info demandée hormis le type est un référent potentiel pour lequel on mettra typiquement <strong>le sujet du verbe associé</strong>. Ex : dans <cite>Je mange des chips <u>xxx</u></cite>, on mettra la 1ère personne du singulier comme référent potentiel, ce qui permettra d'avoir par exemple <cite><u>dans ma voiture</u></cite>.</p>


<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
