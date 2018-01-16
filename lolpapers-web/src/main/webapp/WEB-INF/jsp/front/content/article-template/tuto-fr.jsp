<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="article-template-tuto well" style="${empty param.needsTuto ? 'display: none;' : ''}">
    <p class="tuto-head"><em class="tuto-title">Guide de d�marrage rapide</em> 
        <span class="tuto-head-actions">
            <a href="${fn:escapeXml(param.helpUrl)}" target="_blank">aide d�taill�e <span class="glyphicon glyphicon-share" aria-hidden="true"></span></a>
            <a href="#" class="tuto-close">[fermer]</a>
        </span>
    </p>
    <div class="row">
        <div class="col-md-4 tuto-col">
            <p><span class="tuto-item-num">1.</span> �liminer �ventuellement des paragraphes pour r�duire la taille de l'article avec le <strong>mode suppression</strong>&nbsp;:</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-01.png" /></p>
            <p>D�sactiver le <strong>mode suppression</strong> pour cr�er des <strong>emplacements</strong></p>
        </div>
        <div class="col-md-4 tuto-col">
            <p><span class="tuto-item-num">2.</span> Pour cr�er un <strong>emplacement</strong>, cliquer sur un mot pour commencer une s�lection de mots � remplacer puis cliquer �ventuellement sur des mots autour pour �largir la s�lection</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-03.png" /></p>
            <p>S�lectionner le type correspondant � la s�lection et renseigner les infos requises</p>
        </div>
        <div class="col-md-4 tuto-col">
            <p><span class="tuto-item-num">3.</span> Recommencer au moins jusqu'� atteindre le nombre d'emplacements minimum (proportionnel � la quantit� de texte)&nbsp;:</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-04.png" /></p>
            <p>Cliquer sur Terminer&nbsp;:</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-05.png" /></p>
        </div>
    </div>
</div>
