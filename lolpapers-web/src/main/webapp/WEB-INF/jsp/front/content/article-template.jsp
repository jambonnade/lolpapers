<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<fmt:message key="articleTemplate.edit.pageTitle" var="pageTitle" />

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.content.ArticleTemplatePage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="${pageTitle}" />
</jsp:useBean>

<c:set var="baseHtmlId" value="${myPage.uniqueHtmlId}" />

<%@ include file="/WEB-INF/jspf/front/page-html-start.jspf" %>

<div class="template-edit language-${myPage.language.code}" id="${baseHtmlId}-main-container"
        data-id="${fn:escapeXml(myPage.articleTemplate.contentTemplateId)}"
        data-save-url="${fn:escapeXml(myPage.formActionUrl)}"
        data-placeholders="${fn:escapeXml(myPage.placeholdersJSON)}"
        data-removed-blocks="${fn:escapeXml(myPage.articleTemplate.articleContentRemovedBlocks)}"
        data-descr-rejected="${fn:escapeXml(myPage.articleTemplate.articleDescriptionRejected)}"
        data-placeholder-text-max-lg="${fn:escapeXml(myPage.placeholderTextMaxLg)}"
        data-min-words="${fn:escapeXml(myPage.minWordsCount)}"
        data-min-placeholders="${fn:escapeXml(myPage.minPlaceholderCount)}"
        data-max-words-by-placeholder="${fn:escapeXml(myPage.maxWordsByPlaceholder)}"
        data-sdef-reusable-attr="${fn:escapeXml(myPage.SDefReusableAttrCode)}"
        data-sdef-reusable-flag-on="${fn:escapeXml(myPage.SDefReusableFlagCode)}"
        data-sdef-reusable-flag-off="${fn:escapeXml(myPage.SDefNotReusableFlagCode)}"
        data-sdef-tooltips-disabled="${myPage.user.sdefTooltipsDisabled ? 'on' : 'off'}">

<div class="js-lang-data" style="display: none;">${fn:escapeXml(myPage.languageJSON)}</div>

<form action="${myPage.formActionUrl}" method="post" class="submit-form">
    <input type="hidden" name="id" value="${fn:escapeXml(myPage.articleTemplate.contentTemplateId)}" />
    <input type="hidden" name="action" value="delete" />
    <input type="hidden" name="reject" value="1" />
</form>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#${baseHtmlId}-navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only"><fmt:message key="nav.mobileToggleNav" /></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${fn:escapeXml(myPage.websiteHomeUrl)}" title="${fn:escapeXml(website.name)}">
                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            </a>
        </div>
        <div id="${baseHtmlId}-navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="erase-mode-menu"><a href="#"><fmt:message key="articleTemplate.edit.nav.eraseMode" />
                        <span class="glyphicon glyphicon-erase" aria-hidden="true"></span></a></li>
                <li><p class="navbar-text"><fmt:message key="articleTemplate.edit.nav.words" /></p></li>
                <li><p class="navbar-text"><fmt:message key="articleTemplate.edit.nav.placeholders" /></p></li>
                <li class="disabled save-menu"><a href="#"><fmt:message key="articleTemplate.edit.nav.save" /></a></li>
                <li class="disabled finish-menu"><a href="#"><fmt:message key="articleTemplate.edit.nav.finish" /></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="articleTemplate.edit.nav.cancel" /> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="delete-menu"><a href="#"><fmt:message key="articleTemplate.edit.nav.cancel.delete" /></a></li>
                        <li class="reject-menu"><a href="#"><fmt:message key="articleTemplate.edit.nav.cancel.rejectWrongContent" /></a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="articleTemplate.edit.nav.help" /> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="tuto-menu"><a href="#"><fmt:message key="articleTemplate.edit.nav.help.tuto" /></a></li>
                        <li><a href="${fn:escapeXml(myPage.helpUrl)}" target="_blank"><fmt:message key="articleTemplate.edit.nav.help.help" /></a></li>
                    </ul>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container main" role="main">
    <%@ include file="/WEB-INF/jspf/front/page-messages.jspf" %>


    <jsp:include page="article-template/tuto-${myPage.language.code}.jsp">
        <jsp:param name="needsTuto" value="${myPage.tutoNeeded ? '1' : ''}" />
        <jsp:param name="baseUrl" value="${myPage.baseUrl}" />
        <jsp:param name="helpUrl" value="${myPage.helpUrl}" />
    </jsp:include>

    
    <c:set var="artImageUrl" value="${myPage.getArticleImageUrl(1200, 300)}" />
    <c:if test="${!empty artImageUrl}">
    <fmt:message var="eltAttr" key="articleTemplate.edit.image.alt" />
    <p><img class="img-responsive center-block" src="${fn:escapeXml(artImageUrl)}" alt="${fn:escapeXml(eltAttr)}" /></p>
    </c:if>
    
    <div class="panel panel-default title-part">
        <div class="panel-heading">
            <h2 class="panel-title"><fmt:message key="articleTemplate.edit.titlePart.title" /></h2>
        </div>
        <div class="panel-body template-edit-part">
            <div>${myPage.articleTemplate.articleTitle}</div>
        </div>
    </div>

    <c:if test="${!empty myPage.articleTemplate.articleDescription}">
    <div class="panel panel-default descr-part">
        <div class="panel-heading">
            <h2 class="panel-title">
                <fmt:message key="articleTemplate.edit.descrPart.title" /> 
                <small style="display: none;" class="disable-descr">
                    <fmt:message var="eltAttr" key="articleTemplate.edit.descrPart.rejectTitle" />
                    <a href="#" title="${fn:escapeXml(eltAttr)}"><fmt:message key="articleTemplate.edit.descrPart.reject" /></a>
                </small>
                <small style="display: none;" class="enable-descr">
                    <a href="#"><fmt:message key="articleTemplate.edit.descrPart.reactivate" /></a>
                </small>
            </h2>
        </div>
        <div class="panel-body template-edit-part">
            <div>${myPage.articleTemplate.articleDescription}</div>
        </div>
    </div>
    </c:if>
        
    <div class="panel panel-default content-part">
        <div class="panel-heading">
            <h2 class="panel-title"><fmt:message key="articleTemplate.edit.contentPart.title" /></h2>
        </div>
        <div class="panel-body template-edit-part">
            <div>${myPage.articleTemplate.articleContent}</div>
        </div>
    </div>

    <p class="footer-spacer">&nbsp;</p>
            <%--
    <div class="template-edit-st-choice">
        <p>text: <span class="sel-text"></span> <a href="#" class="cancel">(x)</a></p>
        <c:forEach items="${myPage.language.syntagmeTypes}" var="stItem">
            <c:set var="st" value="${stItem.value}" />
            <div class="st-choice disabled" data-st="${st.code}"><a href="#">${st.code}</a> ex: zboub</div>
        </c:forEach>
        <p class="use-placeholder">use placeholder <input type="text" /> <button>use placeholder</button></p>
    </div>
--%>
            <%--
    <div class="template-edit-placeholder" style="display:none;">
        <p>text: <span class="orig-text"></span> <a href="#" class="close">(x)</a> <a href="#" class="remove">(suppr)</a></p>
        <p class="use-placeholder">use placeholder: <span></span></p>

        <c:forEach items="${myPage.language.syntagmeTypes}" var="stItem">
            <c:set var="st" value="${stItem.value}" />
            <div class="row placeholder-sdef st-${st.code}" data-st="${st.code}" style="display: none;">
                <c:forTokens items="definition,replacement" var="sdType" delims=",">
                <div class="col-md-6 sdef ${sdType}">
                    <c:forEach items="${st.attributes}" var="attrItem">
                    <c:set var="attr" value="${attrItem.value}" />
                    <c:if test="${sdType == 'definition' || !attr.definitionOnly}">
                    <div class="sdef-attr attr-${attr.code}">
                        <p><strong>${attr.code}</strong></p>
                        <div class="btn-group" role="group" aria-label="...">
                            <c:forEach items="${attr.flags}" var="flag">
                                <button type="button" class="btn btn-default sdef-flag" data-flag="${flag.code}">${flag.code}</button>
                            </c:forEach>
                        </div>
                    </div>
                    </c:if>
                    </c:forEach>
                </div>
                </c:forTokens>
            </div>
        </c:forEach>
    </div>
            --%>
        
    <c:set var="baseTrKey" value="articleTemplate.edit.placeholderModal" />
    <div class="template-edit-placeholder modal" tabindex="-1" role="dialog" aria-labelledby="${baseHtmlId}-placeholder-modal-title">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <fmt:message key="${baseTrKey}.closeBtn" var="eltAttr" />
                    <button type="button" class="close" data-dismiss="modal" aria-label="${fn:escapeXml(eltAttr)}"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="${baseHtmlId}-placeholder-modal-title">
                        <fmt:message key="${baseTrKey}.title" />
                        <small><fmt:message key="${baseTrKey}.title.usingOtherPlaceholder" /></small>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger alert-validation alert-attr-not-set" role="alert" style="display: none;">
                        <fmt:message key="${baseTrKey}.alert.attrNotSet" />
                    </div>
                    
<%--
                    <div class="row">
                        <div class="col-md-6">
                            <h4><fmt:message key="${baseTrKey}.origText.title" /></h4>
                            <blockquote class="orig-text"></blockquote>

                            <div class="context-samples">
                                <h4><fmt:message key="${baseTrKey}.samples.title" /></h4>
                                <ul></ul>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <h4><fmt:message key="${baseTrKey}.article.title" /></h4>
                            <div class="article-view well well-sm" style="height: 80px; overflow: auto;">
                                <div class="content" style="position: relative;">
                                    <h4 class="title-container"></h4>
                                    <div class="descr-container"></div>
                                    <div class="content-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>--%>


                    <div class="row context-sections">

                        <div class="col-md-6">
                            <h4 class="section-title">
                                <fmt:message key="${baseTrKey}.context.title" />

                                <fmt:message key="${baseTrKey}.context.help" var="sectionHelp" />
                                <a href="${myPage.helpUrl}#HSEC-prep-article-placeholders-popup" target="_blank" class="help-link no-follow"
                                        data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true"
                                        data-content="${fn:escapeXml(sectionHelp)}"  data-popover-allow-touch="1">
                                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                </a>
                            </h4>

                            <c:forEach items="${myPage.language.syntagmeTypes}" var="stItem">
                                <%--<c:if test="${false}">--%>
                            <c:set var="st" value="${stItem.value}" />
                            <jsp:setProperty name="myPage" property="curST" value="${st}" />
                            
                            <div class="placeholder-sdef st-${st.code}" data-st="${st.code}" style="display: none;">
                                <div class=" sdef context">
                                    <c:forEach items="${myPage.curSTCtxAttributes}" var="attr" varStatus="attrLoopStatus">
                                    <!-- attr : ${attr.code} -->

                                    <%-- End of attr group 
                                    <c:if test="${!empty lastOpenedAttrGrp && attr.groupCode != lastOpenedAttrGrp}">
                                        </div>
                                    </div>
                                    <c:set var="lastOpenedAttrGrp" value="" />
                                    </c:if>
                                    --%>

                                    <%-- Filter context attributes --%>
                                    <%--<c:if test="${attr.context}">--%>

                                    <%-- New attr group 
                                    <c:if test="${!empty attr.groupCode && lastOpenedAttrGrp != attr.groupCode}">
                                    <c:set var="lastOpenedAttrGrp" value="${attr.groupCode}" />
                                    <div class="col-md-12 sdef-agrp agrp-${lastOpenedAttrGrp}">
                                        <div class="row">
                                            <c:set var="agrpTitle" value="${myPage.languageText.getAttrGrpInfo(baseTrKey, st, lastOpenedAttrGrp, 'name', '')}" />
                                            <c:if test="${!empty agrpTitle}">
                                            <p class="agrp-title">${fn:escapeXml(agrpTitle)}</p>
                                            </c:if>
                                    </c:if>
                                    --%>

                                    <div class="sdef-attr attr-${attr.code}">
                                        <c:set var="attrLabel" value="${myPage.languageText.getAttrInfo(baseTrKey, st, attr, 'name', textSpecialCase)}" />
                                        <c:set var="attrHelp" value="${myPage.languageText.getAttrInfo(baseTrKey, st, attr, 'help', textSpecialCase)}" />
                                        <c:set var="attrInfosId" value="${baseHtmlId}-edit-placeholder-sdef-${st.code}-${sdType}-attr-${attr.code}" />
                                        <p class="attr-title">
                                            ${attrLabel}

                                            <c:if test="${myPage.isCurSTDefAttributeRequired(attr)}"><span class="required">*</span></c:if>

                                            <%--<fmt:message key="${baseTrKey}.attr.infosBtnTitle" var="eltTitle" />
                                            title="${fn:escapeXml(eltTitle)}" HSEC-st-v-attr-cpr--%>
                                            <a href="${myPage.helpUrl}#HSEC-st-${st.code}-attr-${attr.code}" class="attr-help-btn" target="_blank"
                                                    data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" data-content="${fn:escapeXml(attrHelp)}">
                                                <fmt:message key="${baseTrKey}.attr.infosBtn" />&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                            </a>

                                            <%--
                                            <fmt:message key="${baseTrKey}.attr.infosBtnTitle" var="eltTitle" />
                                            <a href="#${attrInfosId}" data-toggle="collapse" title="${fn:escapeXml(eltTitle)}"
                                                    aria-expanded="false" aria-controls="${attrInfosId}">
                                                <small><fmt:message key="${baseTrKey}.attr.infosBtn" />&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></small>
                                            </a>
                                            --%>
                                        </p>
                                        <fmt:message key="${baseTrKey}.attr.flagsGroupLabel" var="eltAttr" />
                                        <div class="btn-group" role="group" aria-label="${fn:escapeXml(eltAttr)}">
                                            <c:forEach items="${attr.flags}" var="flag">
                                            <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', textSpecialCase)}" />
                                            <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', textSpecialCase)}" />
                                            <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, textSpecialCase)}" />
                                            <c:set var="flagPopoverContent" value="${flagHelp}" />
                                            <c:if test="${!empty flagEx}">
                                            <c:if test="${!empty flagPopoverContent}">
                                            <c:set var="flagPopoverContent" value="${flagPopoverContent}<br />" />
                                            </c:if>
                                            <c:set var="flagPopoverContent" value="${flagPopoverContent}ex&nbsp;:" />
                                            <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
                                            <c:set var="flagPopoverContent" value="${flagPopoverContent}${!vs.first ? ',' : ''} <cite>${curFlagEx}</cite>" />
                                            </c:forEach>
                                            </c:if>
                                            <button type="button" class="btn btn-default sdef-flag" data-flag="${flag.code}"
                                                    data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" data-content="${fn:escapeXml(flagPopoverContent)}">
                                                ${flagLabel}
                                            </button>
                                            </c:forEach>
                                        </div>
                                        <div class="detailed-attr" style="display: none;">
                                            <c:if test="${!empty attrHelp}">
                                            <div class="attr-help well well-sm">${attrHelp}</div>
                                            </c:if>

                                            <div class="list-group">
                                                <c:forEach items="${attr.flags}" var="flag">
                                                <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', textSpecialCase)}" />
                                                <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', textSpecialCase)}" />
                                                <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, textSpecialCase)}" />
                                                <a href="#" class="list-group-item sdef-flag" data-flag="${flag.code}">
                                                    <p class="list-group-item-heading">
                                                        ${flagLabel}
                                                        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                                    </p>
                                                    <c:if test="${!empty flagHelp || !empty flagEx}">
                                                    <p class="list-group-item-text flag-help">
                                                        ${flagHelp}
                                                        <c:if test="${!empty flagEx}">
                                                        <c:if test="${!empty flagHelp}">
                                                        <br />
                                                        </c:if>
                                                        <fmt:message key="${baseTrKey}.flag.exStart" />
                                                        <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
                                                        <c:if test="${!vs.first}">
                                                        <fmt:message key="${baseTrKey}.flag.exSep" />
                                                        </c:if>
                                                        <cite>${curFlagEx}</cite>
                                                        </c:forEach>
                                                        </c:if>
                                                    </p>
                                                    </c:if>
                                                </a>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- /Filter context attr --%>
                                    <%--</c:if>--%>

                                    <%-- End of attr group 
                                    <c:if test="${attrLoopStatus.last && !empty lastOpenedAttrGrp}">
                                        </div>
                                    </div>
                                    <c:set var="lastOpenedAttrGrp" value="" />
                                    </c:if>
                                    --%>
                                    </c:forEach>
                                </div>
                            </div>
                            <%--</c:if>--%>
                            </c:forEach>

                        </div>
                            
                        <div class="col-md-6">
                            <h4 class="section-title"><fmt:message key="${baseTrKey}.article.title" /></h4>
                            <div class="article-view well well-sm">
                                <div class="content" style="position: relative;">
                                    <h4 class="title-container"></h4>
                                    <div class="descr-container"></div>
                                    <div class="content-container"></div>
                                </div>
                            </div>
                            
                            <div class="context-samples">
                                <h4 class="section-title">
                                    <fmt:message key="${baseTrKey}.samples.title" />

                                    <fmt:message key="${baseTrKey}.samples.help" var="sectionHelp" />
                                    <a href="${myPage.helpUrl}#HSEC-prep-article-placeholders-popup" target="_blank" class="help-link no-follow"
                                            data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" 
                                            data-content="${fn:escapeXml(sectionHelp)}" data-popover-allow-touch="1">
                                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                    </a>
                                </h4>
                                <p class="invalid-msg"><fmt:message key="${baseTrKey}.samples.invalidMsg" /></p>
                                <ul></ul>
                            </div>
                                
                            <h4 class="section-title">
                                <fmt:message key="${baseTrKey}.origText.title" />
                                
                                <fmt:message key="${baseTrKey}.origText.reusableFlagHelp" var="popoverHelp" />
                                <label class="checkbox-inline pull-right reusable-def-flag"
                                            data-toggle="popover" data-trigger="hover" data-placement="top"
                                            data-content="${fn:escapeXml(popoverHelp)}" data-popover-allow-touch="1">
                                    <input type="checkbox" value="1">
                                    <fmt:message key="${baseTrKey}.origText.reusableFlagLabel" />
                                </label>
                            </h4>
                            <blockquote class="orig-text"></blockquote>

                        </div>

                    </div>
                        

                    <%--<h4><fmt:message key="${baseTrKey}.context.title" /></h4>--%>

                    <c:forEach items="${myPage.language.syntagmeTypes}" var="stItem">
                    <c:set var="st" value="${stItem.value}" />
                    <jsp:setProperty name="myPage" property="curST" value="${st}" />
                    
                    <div class="placeholder-sdef st-${st.code}" data-st="${st.code}" style="display: none;">                        
                        <div class="row">
                            <c:forTokens items="definition,replacement" var="sdType" delims=",">
                            <c:set var="textSpecialCase" value="${sdType == 'definition' ? '' : 'repl'}" />
                            <div class="col-md-6 sdef ${sdType}">
                                <h4 class="section-title">
                                    <fmt:message key="${baseTrKey}.${sdType}.title" />
                                    
                                    <fmt:message key="${baseTrKey}.${sdType}.help" var="sectionHelp" />
                                    <a href="${myPage.helpUrl}#HSEC-prep-article-placeholders-popup" target="_blank" class="no-follow help-link"
                                            data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true"
                                            data-content="${fn:escapeXml(sectionHelp)}" data-popover-allow-touch="1">
                                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                    </a>
                                </h4>

                                <c:forEach items="${sdType == 'definition' ? myPage.curSTDefAttributes : myPage.curSTReplAttributes}" var="attr">
                                <div class="sdef-attr attr-${attr.code}">
                                    <c:set var="attrLabel" value="${myPage.languageText.getAttrInfo(baseTrKey, st, attr, 'name', textSpecialCase)}" />
                                    <c:set var="attrHelp" value="${myPage.languageText.getAttrInfo(baseTrKey, st, attr, 'help', textSpecialCase)}" />
                                    <c:set var="attrInfosId" value="${baseHtmlId}-edit-placeholder-sdef-${st.code}-${sdType}-attr-${attr.code}" />
                                    <p class="attr-title">
                                        ${attrLabel}

                                        <c:if test="${(sdType == 'definition' && myPage.isCurSTDefAttributeRequired(attr)) || (sdType == 'replacement' && myPage.isCurSTReplAttributeRequired(attr))}"><span class="required">*</span></c:if>
                                        
                                        <a href="${myPage.helpUrl}#HSEC-st-${st.code}-attr-${attr.code}" class="attr-help-btn" target="_blank"
                                                data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" data-content="${fn:escapeXml(attrHelp)}">
                                            <fmt:message key="${baseTrKey}.attr.infosBtn" />&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                        </a>

                                        <%--
                                        <fmt:message key="${baseTrKey}.attr.infosBtnTitle" var="eltTitle" />
                                        <a href="#${attrInfosId}" data-toggle="collapse" title="${fn:escapeXml(eltTitle)}"
                                                aria-expanded="false" aria-controls="${attrInfosId}">
                                            <small><fmt:message key="${baseTrKey}.attr.infosBtn" />&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></small>
                                        </a>
                                        --%>
                                    </p>
                                    <%--
                                    <div class="attr-info well well-sm collapse" id="${attrInfosId}">
                                        <p class="pull-right">
                                            <a href="${myPage.helpUrl}#st-${st.code}-attr-${attr.code}" target="_blank">
                                                <small><fmt:message key="${baseTrKey}.attr.detailedHelp" />&nbsp;<span class="glyphicon glyphicon-share" aria-hidden="true"></span></small>
                                            </a>
                                        </p>
                                        <c:if test="${!empty attrHelp}">
                                        <p class="attr-help">${attrHelp}</p>
                                        </c:if>
                                        <dl class="dl-horizontal">
                                            <c:forEach items="${attr.flags}" var="flag">
                                            <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', textSpecialCase)}" />
                                            <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', textSpecialCase)}" />
                                            <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, textSpecialCase)}" />
                                            <c:if test="${!empty flagHelp || !empty flagEx}">
                                            <dt>${flagLabel}</dt>
                                            <dd>
                                                <c:if test="${!empty flagHelp}">
                                                <p>${flagHelp}</p>
                                                </c:if>
                                                <c:if test="${!empty flagEx}">
                                                <p>
                                                    <fmt:message key="${baseTrKey}.attr.flag.ex" /> 
                                                    <c:forEach var="curFlagEx" items="${flagEx}">
                                                    <span class="label label-default">${curFlagEx}</span>
                                                    </c:forEach>
                                                </p>
                                                </c:if>
                                            </dd>
                                            </c:if>
                                            </c:forEach>
                                        </dl>
                                    </div>
                                    --%>
                                    <fmt:message key="${baseTrKey}.attr.flagsGroupLabel" var="eltAttr" />
                                    <div class="btn-group" role="group" aria-label="${fn:escapeXml(eltAttr)}">
                                        <c:forEach items="${attr.flags}" var="flag">
                                        <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', textSpecialCase)}" />
                                        <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', textSpecialCase)}" />
                                        <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, textSpecialCase)}" />
                                        <c:set var="flagPopoverContent" value="${flagHelp}" />
                                        <c:if test="${!empty flagEx}">
                                        <c:if test="${!empty flagPopoverContent}">
                                        <c:set var="flagPopoverContent" value="${flagPopoverContent}<br />" />
                                        </c:if>
                                        <c:set var="flagPopoverContent" value="${flagPopoverContent}ex&nbsp;:" />
                                        <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
                                        <c:set var="flagPopoverContent" value="${flagPopoverContent}${!vs.first ? ',' : ''} <cite>${curFlagEx}</cite>" />
                                        </c:forEach>
                                        </c:if>
                                        <button type="button" class="btn btn-default sdef-flag" data-flag="${flag.code}"
                                                data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" data-content="${fn:escapeXml(flagPopoverContent)}">
                                            ${flagLabel}
                                        </button>
                                        </c:forEach>
                                    </div>
                                    <div class="detailed-attr" style="display: none;">
                                        <c:if test="${!empty attrHelp}">
                                        <div class="attr-help well well-sm">${attrHelp}</div>
                                        </c:if>

                                        <div class="list-group">
                                            <c:forEach items="${attr.flags}" var="flag">
                                            <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', textSpecialCase)}" />
                                            <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', textSpecialCase)}" />
                                            <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, textSpecialCase)}" />
                                            <a href="#" class="list-group-item sdef-flag" data-flag="${flag.code}">
                                                <p class="list-group-item-heading">
                                                    ${flagLabel}
                                                    <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                                </p>
                                                <c:if test="${!empty flagHelp || !empty flagEx}">
                                                <p class="list-group-item-text flag-help">
                                                    ${flagHelp}
                                                    <c:if test="${!empty flagEx}">
                                                    <c:if test="${!empty flagHelp}">
                                                    <br />
                                                    </c:if>
                                                    <fmt:message key="${baseTrKey}.flag.exStart" />
                                                    <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
                                                    <c:if test="${!vs.first}">
                                                    <fmt:message key="${baseTrKey}.flag.exSep" />
                                                    </c:if>
                                                        <cite>${curFlagEx}</cite>
                                                    </c:forEach>
                                                    </c:if>
                                                </p>
                                                </c:if>
                                            </a>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                            </c:forTokens>
                        </div>
                    </div>
                    </c:forEach>

                </div>
                <div class="modal-footer">
                    <span class="required-note">*&nbsp;<fmt:message key="${baseTrKey}.requiredNote" /></span>
                    <button type="button" class="btn btn-danger remove-btn"><fmt:message key="${baseTrKey}.removeBtn" /></button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="${baseTrKey}.closeBtn" /></button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div> <!-- container main -->

<div class="tpl-selection-popover-container" style="display: none;">
    <div class="popover selection-popover" role="tooltip">
        <div class="arrow"></div>
        <!--<h3 class="popover-title"></h3>-->
        <div class="popover-content"></div>
    </div>
</div>

<div class="tpl-selection-popover" style="display: none;">
<!--    <p class="pull-right">
                
        <a href="#" class="cancel-btn">&times;</a>
    </p>-->
    <p class="select-type-msg">
        <fmt:message key="articleTemplate.edit.selection.create.msg" />
        
        <fmt:message key="articleTemplate.edit.selection.create.infosBtnTitle" var="eltTitle" />
        <a href="#" class="st-help-toggle" title="${fn:escapeXml(eltTitle)}">
            <fmt:message key="articleTemplate.edit.selection.create.infosBtn" />
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
        </a>
    </p>

    <div class="list-group">
        <c:forEach items="${myPage.language.syntagmeTypes}" var="stItem">
        <c:set var="st" value="${stItem.value}" />
        <c:set var="typeLabel" value="${myPage.languageText.getTypeInfo('', st, 'name', '')}" />
        <c:set var="typeHelp" value="${myPage.languageText.getTypeInfo('', st, 'help', '')}" />
        <c:set var="typeQuickEx" value="${myPage.languageText.getTypeInfo('', st, 'quickEx', '')}" />
        <a href="#" class="st-choice list-group-item" data-st="${st.code}"
                data-toggle="popover" data-html="true" data-content="${fn:escapeXml(typeHelp)}" data-placement="top" data-trigger="hover">
            <h4 class="list-group-item-heading" >
                ${typeLabel} <small class="pull-right"><fmt:message key="articleTemplate.edit.selection.create.typeEx" />${typeQuickEx}</small>
            </h4>
            <p class="list-group-item-text collapse">${typeHelp}</p>
        </a>
        </c:forEach>
    </div>
    <div class="use-placeholder">
        <div class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                <fmt:message key="articleTemplate.edit.selection.usePl.msg" />
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
            </ul>
        </div>
    </div>
    <div class="bottom-btns text-right">                
        <button type="button" class="btn btn-default cancel-btn"><fmt:message key="articleTemplate.edit.selection.cancelBtn" /></button>
    </div>
</div>

<ul class="tpl-selection-popover-use-placeholder-item" style="display: none;">
    <li><a href="#" data-ref="{ref}"><strong>&#x23;{num}</strong> <span class="pl-text"></span></a></li>
</ul>

<div class="tpl-placeholder" style="display: none;">
    <span data-placeholder-ref="{ref}" class="placeholder colored-st st-{st}">
        <strong class="placeholder-num">&#x23;<span class="num">{num}</span></strong>
        <span class="placeholder-use-num">
            <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>&nbsp;&#x23;<span class="num">{useNum}</span>
        </span>
        <span class="placeholder-type">{st}</span>
        <span class="placeholder-flags"></span>
    </span>
</div>

<div class="tpl-alert-genric" style="display: none;"    >
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
        <fmt:message key="articleTemplate.edit.alert.closeTitle" var="eltAttr" />
        <button type="button" class="close" data-dismiss="alert" aria-label="${fn:escapeXml(eltAttr)}"><span aria-hidden="true">×</span></button>
        <fmt:message key="articleTemplate.edit.alert.generic" />
    </div>
</div>
<div class="tpl-alert-user-error" style="display: none;">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
        <fmt:message key="articleTemplate.edit.alert.closeTitle" var="eltAttr" />
        <button type="button" class="close" data-dismiss="alert" aria-label="${fn:escapeXml(eltAttr)}"><span aria-hidden="true">×</span></button>
        <fmt:message key="articleTemplate.edit.alert.genericUserError" />
    </div>
</div>
    
</div> <!-- template edit -->

<div class="main-overlay">
    <div class="loader">
        <p><fmt:message key="articleTemplate.edit.savingOverlayMessage" /></p>
        <div class="spinner">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/front/page-html-end.jspf" %>
