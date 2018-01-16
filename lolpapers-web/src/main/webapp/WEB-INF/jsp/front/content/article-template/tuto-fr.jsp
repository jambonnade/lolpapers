<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="article-template-tuto well" style="${empty param.needsTuto ? 'display: none;' : ''}">
    <p class="tuto-head"><em class="tuto-title">Guide de démarrage rapide</em> 
        <span class="tuto-head-actions">
            <a href="${fn:escapeXml(param.helpUrl)}" target="_blank">aide détaillée <span class="glyphicon glyphicon-share" aria-hidden="true"></span></a>
            <a href="#" class="tuto-close">[fermer]</a>
        </span>
    </p>
    <div class="row">
        <div class="col-md-4 tuto-col">
            <p><span class="tuto-item-num">1.</span> Éliminer éventuellement des paragraphes pour réduire la taille de l'article avec le <strong>mode suppression</strong>&nbsp;:</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-01.png" /></p>
            <p>Désactiver le <strong>mode suppression</strong> pour créer des <strong>emplacements</strong></p>
        </div>
        <div class="col-md-4 tuto-col">
            <p><span class="tuto-item-num">2.</span> Pour créer un <strong>emplacement</strong>, cliquer sur un mot pour commencer une sélection de mots à remplacer puis cliquer éventuellement sur des mots autour pour élargir la sélection</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-03.png" /></p>
            <p>Sélectionner le type correspondant à la sélection et renseigner les infos requises</p>
        </div>
        <div class="col-md-4 tuto-col">
            <p><span class="tuto-item-num">3.</span> Recommencer au moins jusqu'à atteindre le nombre d'emplacements minimum (proportionnel à la quantité de texte)&nbsp;:</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-04.png" /></p>
            <p>Cliquer sur Terminer&nbsp;:</p>
            <p class="tuto-img"><img src="${fn:escapeXml(param.baseUrl)}assets/tuto-fr-05.png" /></p>
        </div>
    </div>
</div>
