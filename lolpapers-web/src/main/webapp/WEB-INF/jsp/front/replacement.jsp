<%@ include file="/WEB-INF/jspf/front/page-top-declarations.jspf" %>

<fmt:message key="fillReplacement.pageTitle" var="pageTitle" />

<jsp:useBean id="myPage" class="de.jambonna.lolpapers.web.model.page.FillReplacementPage" scope="page">
    <jsp:setProperty name="myPage" property="servletRequest" value="${pageContext.request}" />
    <jsp:setProperty name="myPage" property="curPageTitle" value="${pageTitle}" />
</jsp:useBean>

<c:set var="baseHtmlId" value="${myPage.uniqueHtmlId}" />


<%@ include file="/WEB-INF/jspf/front/page-html-website-start.jspf" %>

<c:choose>
<c:when test="${empty myPage.placeholder}">

<div class="well">
    <c:choose>
    <c:when test="${myPage.user.canDoReplacement()}">
    <p><fmt:message key="fillReplacement.noPlaceholderMsg" /></p>
    </c:when>
    <c:otherwise>
    <p>
        <fmt:message key="fillReplacement.cantDoReplacementMsg">
            <fmt:param value="${fn:escapeXml(myPage.newArticleTemplateUrl)}" />
        </fmt:message>
    </p>
    </c:otherwise>
    </c:choose>
    <p><a href="${fn:escapeXml(myPage.websiteHomeUrl)}"><fmt:message key="fillReplacement.backToWebsite" /></a></p>
</div>

</c:when>
<c:otherwise>

<form action="${fn:escapeXml(myPage.formActionUrl)}" method="post" class="fill-replacement-form language-${myPage.language.code}"
        data-st-code="${fn:escapeXml(myPage.syntagmeType.code)}"
        data-orig-ctx-flags="${fn:escapeXml(myPage.strOrigContextFlags)}"
        data-orig-repl-flags="${fn:escapeXml(myPage.strOrigReplFlags)}"
        data-text-max-lg="${fn:escapeXml(myPage.replacementTextMaxLg)}"
        data-sdef-tooltips-disabled="${myPage.user.sdefTooltipsDisabled ? 'on' : 'off'}">

    <input type="hidden" name="id" value="${fn:escapeXml(myPage.placeholder.templatePlaceholderId)}" />

    <input type="hidden" name="flags" value="" />
    <input type="hidden" name="text" value="" />

    <div class="fill-text-part">
        <p class="fill-text-msg">
            <fmt:message key="fillReplacement.fill.msg">
                <fmt:param value="${fn:escapeXml(myPage.syntagmeType.code)}" />
                <fmt:param value="${myPage.languageText.getTypeInfo('fillReplacement.fill', myPage.syntagmeType, 'name', '')}" />
            </fmt:message>
        </p>
        <div class="row fill-text-details">
            <div class="col-md-4 col-md-offset-1">
                <p class="details-title"><fmt:message key="fillReplacement.fill.details.def.title" /></p>
                ${myPage.replHTMLInfo}
            </div>
            <div class="col-md-6">
                <p class="details-title"><fmt:message key="fillReplacement.fill.details.ctx.title" /></p>
                ${myPage.contextHTMLInfo}
            </div>
        </div>

        <div class="row">
            <div class="col-md-10">
                <div class="form-group user-text">
                    <div class="input-group">
                        <div class="input-group-addon ctx-prefix"></div>
                        <fmt:message key="fillReplacement.fill.inputPlaceholder" var="placeholderAttr" />
                        <input type="text" name="user_text" value="" class="form-control input-lg" placeholder="${fn:escapeXml(placeholderAttr)}" />
                        <div class="input-group-addon ctx-suffix"></div>
                    </div>
                    <span class="help-block too-long-text-msg"><fmt:message key="fillReplacement.fill.tooLongMsg" /></span>
                </div>
            </div>
            <div class="col-md-2">
                <button class="btn btn-success btn-lg btn-block submit-btn" type="submit"><fmt:message key="fillReplacement.submit" /></button>
            </div>
        </div>
    </div>

                <%-- 
    <div class="tpl-fill-text-popover-content-with-ex">{text}<br /><span class="ex"><fmt:message key="fillReplacement.fill.popover.ex" />{ex}</span></div>
    <div class="tpl-fill-text-popover-content-ex">{text}<br /><span class="ex"><fmt:message key="fillReplacement.fill.popover.ex" />{ex}</span></div>
    
    <div class="js-fill-text-help-data" style="display: none;">${fn:escapeXml(myPage.getHelpTextJSON('fillReplacement.fill', 'repl'))}</div>
    --%>

    <div class="row">
        <div class="col-md-4 ctx-samples">
            <p class="ctx-samples-title"><fmt:message key="fillReplacement.ctxSamples.title" /></p>
            <ul></ul>
        </div>
        <div class="col-md-8 placeholder-sdef" style="display: none;">
            <c:set var="baseTrKey" value="fillReplacement.sdef" />
            <c:set var="st" value="${myPage.syntagmeType}" />
            <p class="sdef-msg"><fmt:message key="${baseTrKey}.msg" /></p>

            <div class="row">
                <c:forEach items="${myPage.possiblyAskedAttributes}" var="attr">
                <div class="sdef-attr attr-${attr.code} col-md-6">
                    <c:set var="attrLabel" value="${myPage.languageText.getAttrInfo(baseTrKey, st, attr, 'name', 'repl')}" />
                    <c:set var="attrInfosId" value="${baseHtmlId}-edit-placeholder-sdef-${st.code}-${sdType}-attr-${attr.code}" />
                    <p class="attr-title">${attrLabel}</p>

                    <div class="list-group">
                        <c:forEach items="${attr.flags}" var="flag">
                        <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', 'repl')}" />
                        <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', 'repl')}" />
                        <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, 'repl')}" />
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
                                <fmt:message key="${baseTrKey}.flagPopoverEx" />
                                <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
                                <c:if test="${!vs.first}">
                                <fmt:message key="${baseTrKey}.flagPopoverExSep" />
                                </c:if>
                                <cite>${curFlagEx}</cite>
                                </c:forEach>
                                </c:if>
                            </p>
                            </c:if>
                        </a>
                        </c:forEach>
                    </div>

                    <%--
                    <fmt:message key="${baseTrKey}.flagBtnsGroupAriaLabel" var="ariaLabel" />
                    <div class="btn-group" role="group" aria-label="${fn:escapeXml(ariaLabel)}">
                        <c:forEach items="${attr.flags}" var="flag">
                        <c:if test="${!empty myPage.possibleFlags[flag.code]}">
                        <c:set var="flagLabel" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'name', 'repl')}" />
                        <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, st, flag, 'help', 'repl')}" />
                        <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, st, flag, 'repl')}" />
                        <c:set var="flagPopoverContent" value="${flagHelp}" />
                        <c:if test="${!empty flagEx}">
                        <c:if test="${!empty flagPopoverContent}">
                        <c:set var="flagPopoverContent" value="${flagPopoverContent}<br />" />
                        </c:if>
                        <fmt:message key="${baseTrKey}.flagPopoverEx" var="flagPopoverExText" />
                        <fmt:message key="${baseTrKey}.flagPopoverExSep" var="flagPopoverExSepText" />
                        <c:set var="flagPopoverContent" value="${flagPopoverContent}${flagPopoverExText}" />
                        <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
                        <c:set var="flagPopoverContent" value="${flagPopoverContent}${!vs.first ? flagPopoverExSepText : ''} <u>${curFlagEx}</u>" />
                        </c:forEach>
                        </c:if>
                        <button type="button" class="btn btn-default sdef-flag" data-flag="${flag.code}"
                                data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" data-content="${fn:escapeXml(flagPopoverContent)}">
                            ${flagLabel}
                        </button>
                        </c:if>
                        </c:forEach>
                    </div>
                    --%>
                </div>

                </c:forEach>

            </div>
        </div>
    </div>
    
    <c:set var="baseTrKey" value="fillReplacement.fill" />
    <div class="fill-text-st-help-popover" style="display: none;">
        ${myPage.languageText.getTypeInfo(baseTrKey, myPage.syntagmeType, 'help', '')}
        <c:set var="helpEx" value="${myPage.languageText.getTypeEx(baseTrKey, myPage.syntagmeType, '')}" />
        <br /><fmt:message key="${baseTrKey}.helpPopoverEx" />
        <c:forEach var="curEx" items="${helpEx}" varStatus="vs">
        <cite>${curEx}</cite><c:if test="${!vs.last}"><fmt:message key="${baseTrKey}.helpPopoverExSep" /></c:if>
        </c:forEach>
    </div>
    <c:forEach var="attrItem" items="${myPage.syntagmeType.attributes}">
    <c:set var="attr" value="${attrItem.value}" />
    
    <c:set var="attrHelp" value="${myPage.languageText.getAttrInfo(baseTrKey, myPage.syntagmeType, attr, 'help', 'repl')}" />
    <c:if test="${!empty attrHelp}">
    <div class="fill-text-attr-${attr.code}-help-popover" style="display: none;">
        ${attrHelp}
    </div>
    </c:if>

    <c:forEach items="${attr.flags}" var="flag">
    <c:set var="flagHelp" value="${myPage.languageText.getFlagInfo(baseTrKey, myPage.syntagmeType, flag, 'help', 'repl')}" />
    <c:set var="flagEx" value="${myPage.languageText.getFlagEx(baseTrKey, myPage.syntagmeType, flag, 'repl')}" />
    <c:if test="${!empty flagEx || !empty flagHelp}">
    <div class="fill-text-flag-${flag.code}-help-popover" style="display: none;">
        ${flagHelp}
        <c:if test="${!empty flagEx}">
        <c:if test="${!empty flagHelp}">
        <br />
        </c:if>
        <fmt:message key="${baseTrKey}.helpPopoverEx" />
        <c:forEach var="curFlagEx" items="${flagEx}" varStatus="vs">
        <cite>${curFlagEx}</cite><c:if test="${!vs.last}"><fmt:message key="${baseTrKey}.helpPopoverExSep" /></c:if>
        </c:forEach>
        </c:if>
    </div>
    </c:if>
    </c:forEach>
    </c:forEach>

    <!--<p class="btns-part"><button class="btn btn-success btn-lg submit-btn" type="submit"><fmt:message key="fillReplacement.submit" /></button></p>-->

    <div class="js-lang-data" style="display: none;">${fn:escapeXml(myPage.languageJSON)}</div>
</form>
    
</c:otherwise>
</c:choose>

    
<%@ include file="/WEB-INF/jspf/front/page-html-website-end.jspf" %>
