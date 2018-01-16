<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="languages" class="de.jambonna.lolpapers.core.model.lang.Languages" scope="page">
</jsp:useBean>
<%
    String jsonLangFr = languages.getFr().toJson(false);
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Theme Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap-3.3.7.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme-3.3.7.min.css" rel="stylesheet">
    <link href="css/lolpapers.css" rel="stylesheet">
    
  </head>

  <body>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Bootstrap theme</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>Theme example</h1>
        <p>This is a template showcasing the optional theme stylesheet included in Bootstrap. Use it as a starting point to create something more unique by building on or modifying it.</p>
      </div>

        <div class="template-edit" style="position: relative">

            <p><button class="btn btn-default save-template-btn">Save</button> <button class="btn btn-default finish-template-btn">Finish</button></p>
            
            <div class="template-edit-part">
                <div>
                  <p><a data-wid="1" data-wsid="1">Je</a> <a data-wid="2" data-wsid="1">veux</a> <a data-wid="3" data-wsid="1">des</a> <a data-wid="4" data-wsid="1">aaa</a> <a data-wid="5" data-wsid="1">et</a> <a data-wid="6" data-wsid="1">un</a> <a data-wid="7" data-wsid="1">bbb</a>, <a data-wid="8" data-wsid="1">cccc</a> <a data-wid="9" data-wsid="1">ddd</a>, <a data-wid="10" data-wsid="1">tout</a> <a data-wid="11" data-wsid="1">simplement</a>. <a data-wid="12" data-wsid="2">eeeee</a> <a data-wid="13" data-wsid="2">c</a>'<a data-wid="14" data-wsid="2">est</a> <a data-wid="15" data-wsid="2">ma</a> <a data-wid="16" data-wsid="2">grande</a> <a data-wid="17" data-wsid="2">passion</a>. <a data-wid="18" data-wsid="3">Que</a> <a data-wid="19" data-wsid="3">ffff</a> <a data-wid="20" data-wsid="3">soit</a> <a data-wid="21" data-wsid="3">fait</a> <a data-wid="22" data-wsid="3">avec</a> <a data-wid="23" data-wsid="3">respect</a> <a data-wid="24" data-wsid="3">de</a> <a data-wid="25" data-wsid="3">ggg</a> <a data-wid="26" data-wsid="3">hhh</a></p>
                  <p><strong><a data-wid="27" data-wsid="4">Ma</a> <a data-wid="28" data-wsid="4">iii</a></strong> <a data-wid="29" data-wsid="4">est</a> <em><a data-wid="30" data-wsid="4">TA</a></em> <a data-wid="31" data-wsid="4">jjj</a>, <a data-wid="32" data-wsid="4">je</a> <strong><a data-wid="33" data-wsid="4">kkk</a> <em><a data-wid="34" data-wsid="4">lll</a></em></strong> <a data-wid="35" data-wsid="4">mmm</a> <em><a data-wid="36" data-wsid="4">comme</a> <a data-wid="37" data-wsid="4">ooo</a></em> <a data-wid="38" data-wsid="4">ppp</a>.</p>
                  <p><strong><em><a data-wid="39" data-wsid="6">Le</a></em></strong> <em><a data-wid="40" data-wsid="6">qqq</a> <a data-wid="41" data-wsid="6">de</a></em> <strong><a data-wid="42" data-wsid="6">rrr</a></strong></p>
                </div>
            </div>

            
            <div class="template-edit-st-choice">
                <p>text: <span class="sel-text"></span> <a href="#" class="cancel">(x)</a></p>
                <c:forEach items="${languages.fr.syntagmeTypes}" var="stItem">
                <c:set var="st" value="${stItem.value}" />
                <div class="st-choice disabled" data-st="${st.code}"><a href="#">${st.code}</a> ex: zboub</div>
                </c:forEach>
            </div>
            
            <div class="template-edit-placeholder">
                <p>text: <span class="orig-text"></span> <a href="#" class="close">(x)</a> <a href="#" class="remove">(suppr)</a></p>

                <c:forEach items="${languages.fr.syntagmeTypes}" var="stItem">
                <c:set var="st" value="${stItem.value}" />
                <div class="placeholder-sdef st-${st.code}" data-st="${st.code}" style="display: none;">
                    <c:forTokens items="definition,replacement" var="sdType" delims=",">
                    <div class="sdef ${sdType}">
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
            <form action="" method="post" class="save-form">
                <input type="hidden" name="id" value="34" />
                <input type="hidden" name="placeholders" value='[{"reference":"r1", "fromWordId":7, "nbWords": 2, "stCode": "sn"}]' />

            </form>
            <div class="js-lang-data" style="display: none;"><%= jsonLangFr %></div>
        </div>
      
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/bootstrap-3.3.7.min.js"></script>
    <script src="js/xregexp-all.min.js"></script>
    <script src="js/lolpapers.js"></script>
  </body>
</html>
