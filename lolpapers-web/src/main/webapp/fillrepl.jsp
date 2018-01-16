<%@page import="de.jambonna.lolpapers.core.model.lang.SyntagmeType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="languages" class="de.jambonna.lolpapers.core.model.lang.Languages" scope="page">
</jsp:useBean>
<c:set var="st" value="${languages.fr.getSyntagmeType('n')}" />
<jsp:useBean id="allowedFlags" class="java.util.HashMap" scope="page" />
<%
    String jsonLangFr = languages.getFr().toJson(false);
    allowedFlags.put("m", Boolean.TRUE);
    allowedFlags.put("p", Boolean.TRUE);
    allowedFlags.put("ci", Boolean.TRUE);
    allowedFlags.put("ca", Boolean.TRUE);
    allowedFlags.put("wc", Boolean.TRUE);
    allowedFlags.put("woc", Boolean.TRUE);
    allowedFlags.put("kmj", Boolean.TRUE);
    allowedFlags.put("kimj", Boolean.TRUE);
    allowedFlags.put("nomj", Boolean.TRUE);
    allowedFlags.put("ha", Boolean.TRUE);
    allowedFlags.put("hm", Boolean.TRUE);
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

        <div class="fill-replacement" data-st-code="n" data-input-repl-flags="m,p,ci,ca,wc,woc" data-text-max-lg="250">
            
            <form action="" method="post" class="save-form">
                <input type="hidden" name="id" value="" />
                
                <input type="hidden" name="flags" value="" />
                <input type="hidden" name="text" value="" />
                
                <div class="form-group user-text">
                    <input type="text" name="user_text" class="form-control" placeholder="Text input" />
                    <span class="help-block too-long-text-msg">A block of help text that breaks onto a new line and may extend beyond one line.</span>
                </div>
                
                <p><button class="btn btn-default submit-btn" type="submit">Envoyer</button></p>
            </form>

            
            <div class="placeholder-sdef">
                <c:forEach items="${st.attributes}" var="attrItem">
                <c:set var="attr" value="${attrItem.value}" />
                <div class="sdef-attr attr-${attr.code}">
                    <p><strong>${attr.code}</strong></p>
                    <div class="btn-group" role="group" aria-label="...">
                        <c:forEach items="${attr.flags}" var="flag">
                        <c:if test="${allowedFlags[flag.code]}">
                        <button type="button" class="btn btn-default sdef-flag" data-flag="${flag.code}">${flag.code}</button>
                        </c:if>
                        </c:forEach>
                    </div>
                </div>
                </c:forEach>
            </div>
            <div class="js-lang-data" style="display: none;">
                
{
  "locale": "fr_fr",
  "syntagmeTypes": {
    "n": {
      "code": "n",
      "attributes": {
        "genre": {
          "code": "genre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "f"
            },
            {
              "code": "m"
            }
          ]
        },
        "nombre": {
          "code": "nombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "s"
            },
            {
              "code": "p"
            }
          ]
        },
        "type": {
          "code": "type",
          "definitionOnly": false,
          "flags": [
            {
              "code": "ci"
            },
            {
              "code": "cnc"
            },
            {
              "code": "ca"
            },
            {
              "code": "abs"
            }
          ]
        },
        "compl": {
          "code": "compl",
          "definitionOnly": false,
          "flags": [
            {
              "code": "woc"
            },
            {
              "code": "wc"
            }
          ]
        },
        "kmj": {
          "code": "kmj",
          "definitionOnly": true,
          "flags": [
            {
              "code": "kmj"
            },
            {
              "code": "kimj"
            },
            {
              "code": "nomj"
            }
          ]
        },
        "ha": {
          "code": "ha",
          "definitionOnly": true,
          "flags": [
            {
              "code": "ha"
            },
            {
              "code": "hm"
            }
          ]
        }
      },
      "updates": [
        {
          "flagsToLimitTo": [
            "woc"
          ],
          "attributeFlagsToSetTo": [
            "woc"
          ],
          "replAttributeFlagsToSetTo": [
            "woc",
            "wc"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "f",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "f"
          ],
          "replAttributeFlagsToSetTo": [
            "f"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "m",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "m"
          ],
          "replAttributeFlagsToSetTo": [
            "m"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "s",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "s"
          ],
          "replAttributeFlagsToSetTo": [
            "s"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "p",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "p"
          ],
          "replAttributeFlagsToSetTo": [
            "p"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]+[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
              "flags": 0
            }
          },
          "flagsToLimitTo": [
            "wc"
          ],
          "attributeFlagsToSetTo": [
            "wc"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cnc",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cnc"
          ],
          "replAttributeFlagsToSetTo": [
            "cnc"
          ]
        },
        {
          "condition": {
            "type": "onetrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "ci",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "ca",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "abs",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "replFlagsToLimitTo": [
            "ci",
            "ca",
            "abs"
          ]
        },
        {
          "condition": {
            "type": "onetrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "ci",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "ca",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "replAttributeFlagsToSetTo": [
            "ci",
            "ca"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "abs",
            "flagSet": true,
            "flagInRepl": false
          },
          "replAttributeFlagsToSetTo": [
            "ci",
            "ca",
            "abs"
          ]
        },
        {
          "attributesToDisable": [
            "kmj",
            "ha"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "\\p{Lu}",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "kmj"
          ],
          "attributeFlagsToSetTo": [
            "nomj"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]*[hH]",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "ha"
          ]
        }
      ],
      "allowByPattern": {
        "pattern": "[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
        "flags": 0
      }
    },
    "sn": {
      "code": "sn",
      "attributes": {
        "genre": {
          "code": "genre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "f"
            },
            {
              "code": "m"
            }
          ]
        },
        "nombre": {
          "code": "nombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "s"
            },
            {
              "code": "p"
            }
          ]
        },
        "type": {
          "code": "type",
          "definitionOnly": false,
          "flags": [
            {
              "code": "ci"
            },
            {
              "code": "cnc"
            },
            {
              "code": "ca"
            },
            {
              "code": "abs"
            }
          ]
        },
        "compl": {
          "code": "compl",
          "definitionOnly": false,
          "flags": [
            {
              "code": "woc"
            },
            {
              "code": "wc"
            }
          ]
        },
        "kmj": {
          "code": "kmj",
          "definitionOnly": true,
          "flags": [
            {
              "code": "kmj"
            },
            {
              "code": "kimj"
            },
            {
              "code": "nomj"
            }
          ]
        },
        "ha": {
          "code": "ha",
          "definitionOnly": true,
          "flags": [
            {
              "code": "ha"
            },
            {
              "code": "hm"
            }
          ]
        }
      },
      "updates": [
        {
          "attributeFlagsToSetTo": [
            "woc"
          ],
          "replAttributeFlagsToSetTo": [
            "f",
            "m",
            "s",
            "p",
            "ci",
            "cnc",
            "ca",
            "abs",
            "woc",
            "wc"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]+[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]+[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
              "flags": 0
            }
          },
          "attributeFlagsToSetTo": [
            "wc"
          ]
        },
        {
          "attributesToDisable": [
            "kmj",
            "ha"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "\\p{Lu}",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "kmj"
          ],
          "attributeFlagsToSetTo": [
            "nomj"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]*[hH]",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "ha"
          ]
        }
      ],
      "allowByPattern": {
        "pattern": "[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]+[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
        "flags": 0
      }
    },
    "v": {
      "code": "v",
      "attributes": {
        "type": {
          "code": "type",
          "definitionOnly": false,
          "flags": [
            {
              "code": "tr"
            },
            {
              "code": "itr"
            },
            {
              "code": "pr"
            }
          ]
        },
        "forme": {
          "code": "forme",
          "definitionOnly": false,
          "flags": [
            {
              "code": "pres"
            },
            {
              "code": "infi"
            },
            {
              "code": "ppas"
            },
            {
              "code": "impa"
            },
            {
              "code": "pass"
            },
            {
              "code": "futur"
            },
            {
              "code": "ppre"
            },
            {
              "code": "impe"
            }
          ]
        },
        "cnombre": {
          "code": "cnombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "cs"
            },
            {
              "code": "cp"
            }
          ]
        },
        "cpersonne": {
          "code": "cpersonne",
          "definitionOnly": false,
          "flags": [
            {
              "code": "cp1"
            },
            {
              "code": "cp2"
            },
            {
              "code": "cp3"
            }
          ]
        },
        "cgenre": {
          "code": "cgenre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "cm"
            },
            {
              "code": "cf"
            }
          ]
        },
        "cc": {
          "code": "cc",
          "definitionOnly": false,
          "flags": [
            {
              "code": "nocc"
            },
            {
              "code": "cc"
            }
          ]
        },
        "snombre": {
          "code": "snombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "sn0"
            },
            {
              "code": "ss"
            },
            {
              "code": "sp"
            }
          ]
        },
        "sgenre": {
          "code": "sgenre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "sg0"
            },
            {
              "code": "sm"
            },
            {
              "code": "sf"
            }
          ]
        },
        "kmj": {
          "code": "kmj",
          "definitionOnly": true,
          "flags": [
            {
              "code": "kmj"
            },
            {
              "code": "kimj"
            },
            {
              "code": "nomj"
            }
          ]
        },
        "ha": {
          "code": "ha",
          "definitionOnly": true,
          "flags": [
            {
              "code": "ha"
            },
            {
              "code": "hm"
            }
          ]
        }
      },
      "updates": [
        {
          "attributesToDisable": [
            "cgenre",
            "cnombre",
            "cpersonne",
            "sgenre",
            "snombre"
          ],
          "attributeFlagsToSetTo": [
            "nocc"
          ],
          "replAttributesToDisable": [
            "cgenre",
            "cnombre",
            "cpersonne",
            "sgenre",
            "snombre"
          ],
          "replAttributeFlagsToSetTo": [
            "cc",
            "nocc"
          ]
        },
        {
          "condition": {
            "type": "onetrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "pres",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "impa",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "pass",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "futur",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "impe",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "attributesToEnable": [
            "cnombre",
            "cpersonne"
          ],
          "replAttributesToEnable": [
            "cnombre",
            "cpersonne"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "impe",
            "flagSet": true,
            "flagInRepl": false
          },
          "flagsToLimitTo": [
            "cp1",
            "cp2"
          ],
          "replFlagsToLimitTo": [
            "cp1",
            "cp2"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "ppas",
            "flagSet": true,
            "flagInRepl": false
          },
          "attributesToEnable": [
            "cnombre",
            "cgenre"
          ],
          "replAttributesToEnable": [
            "cnombre",
            "cgenre"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]+[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
              "flags": 0
            }
          },
          "attributeFlagsToSetTo": [
            "cc"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cc",
            "flagSet": true,
            "flagInRepl": false
          },
          "attributesToEnable": [
            "snombre",
            "sgenre"
          ],
          "attributeFlagsToSetTo": [
            "sn0",
            "sg0"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cc",
            "flagSet": true,
            "flagInRepl": true
          },
          "replAttributesToEnable": [
            "snombre",
            "sgenre"
          ],
          "replAttributeFlagsToSetTo": [
            "sn0",
            "sg0"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "tr",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "tr"
          ],
          "replAttributeFlagsToSetTo": [
            "tr"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "itr",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "itr"
          ],
          "replAttributeFlagsToSetTo": [
            "itr"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "pr",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "pr"
          ],
          "replAttributeFlagsToSetTo": [
            "pr"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "pres",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "pres"
          ],
          "replAttributeFlagsToSetTo": [
            "pres"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "infi",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "infi"
          ],
          "replAttributeFlagsToSetTo": [
            "infi"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "ppas",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "ppas"
          ],
          "replAttributeFlagsToSetTo": [
            "ppas"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "impa",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "impa"
          ],
          "replAttributeFlagsToSetTo": [
            "impa"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "pass",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "pass"
          ],
          "replAttributeFlagsToSetTo": [
            "pass"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "futur",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "futur"
          ],
          "replAttributeFlagsToSetTo": [
            "futur"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "ppre",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "ppre"
          ],
          "replAttributeFlagsToSetTo": [
            "ppre"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "impe",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "impe"
          ],
          "replAttributeFlagsToSetTo": [
            "impe"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cs",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cs"
          ],
          "replAttributeFlagsToSetTo": [
            "cs"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp"
          ],
          "replAttributeFlagsToSetTo": [
            "cp"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp1",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp1"
          ],
          "replAttributeFlagsToSetTo": [
            "cp1"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp2",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp2"
          ],
          "replAttributeFlagsToSetTo": [
            "cp2"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp3",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp3"
          ],
          "replAttributeFlagsToSetTo": [
            "cp3"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cm",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cm"
          ],
          "replAttributeFlagsToSetTo": [
            "cm"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cf",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cf"
          ],
          "replAttributeFlagsToSetTo": [
            "cf"
          ]
        },
        {
          "attributesToDisable": [
            "kmj",
            "ha"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "\\p{Lu}",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "kmj"
          ],
          "attributeFlagsToSetTo": [
            "nomj"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]*[hH]",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "ha"
          ]
        },
        {
          "condition": {
            "type": "alltrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "cc",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "ppas",
                "flagSet": false,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "cs",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "flagsToLimitTo": [
            "sn0",
            "ss"
          ]
        },
        {
          "condition": {
            "type": "alltrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "cc",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "ppas",
                "flagSet": false,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "cp",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "flagsToLimitTo": [
            "sn0",
            "sp"
          ]
        },
        {
          "condition": {
            "type": "alltrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "cc",
                "flagSet": true,
                "flagInRepl": true
              },
              {
                "type": "flag",
                "flag": "ppas",
                "flagSet": false,
                "flagInRepl": true
              },
              {
                "type": "flag",
                "flag": "cs",
                "flagSet": true,
                "flagInRepl": true
              }
            ]
          },
          "replFlagsToLimitTo": [
            "sn0",
            "ss"
          ]
        },
        {
          "condition": {
            "type": "alltrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "cc",
                "flagSet": true,
                "flagInRepl": true
              },
              {
                "type": "flag",
                "flag": "ppas",
                "flagSet": false,
                "flagInRepl": true
              },
              {
                "type": "flag",
                "flag": "cp",
                "flagSet": true,
                "flagInRepl": true
              }
            ]
          },
          "replFlagsToLimitTo": [
            "sn0",
            "sp"
          ]
        }
      ],
      "allowByPattern": {
        "pattern": "[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
        "flags": 0
      }
    },
    "sv": {
      "code": "sv",
      "attributes": {
        "forme": {
          "code": "forme",
          "definitionOnly": false,
          "flags": [
            {
              "code": "pres"
            },
            {
              "code": "infi"
            },
            {
              "code": "ppas"
            },
            {
              "code": "impa"
            },
            {
              "code": "pass"
            },
            {
              "code": "futur"
            },
            {
              "code": "ppre"
            },
            {
              "code": "impe"
            }
          ]
        },
        "cnombre": {
          "code": "cnombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "cs"
            },
            {
              "code": "cp"
            }
          ]
        },
        "cpersonne": {
          "code": "cpersonne",
          "definitionOnly": false,
          "flags": [
            {
              "code": "cp1"
            },
            {
              "code": "cp2"
            },
            {
              "code": "cp3"
            }
          ]
        },
        "cgenre": {
          "code": "cgenre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "cm"
            },
            {
              "code": "cf"
            }
          ]
        },
        "snombre": {
          "code": "snombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "sn0"
            },
            {
              "code": "ss"
            },
            {
              "code": "sp"
            }
          ]
        },
        "sgenre": {
          "code": "sgenre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "sg0"
            },
            {
              "code": "sm"
            },
            {
              "code": "sf"
            }
          ]
        },
        "cc": {
          "code": "cc",
          "definitionOnly": false,
          "flags": [
            {
              "code": "nocc"
            },
            {
              "code": "cc"
            }
          ]
        },
        "kmj": {
          "code": "kmj",
          "definitionOnly": true,
          "flags": [
            {
              "code": "kmj"
            },
            {
              "code": "kimj"
            },
            {
              "code": "nomj"
            }
          ]
        },
        "ha": {
          "code": "ha",
          "definitionOnly": true,
          "flags": [
            {
              "code": "ha"
            },
            {
              "code": "hm"
            }
          ]
        }
      },
      "updates": [
        {
          "attributesToDisable": [
            "cgenre",
            "cnombre",
            "cpersonne"
          ],
          "attributeFlagsToSetTo": [
            "sg0",
            "sn0",
            "nocc"
          ],
          "replAttributesToDisable": [
            "cgenre",
            "cnombre",
            "cpersonne"
          ],
          "replAttributeFlagsToSetTo": [
            "sg0",
            "sn0",
            "cc",
            "nocc"
          ]
        },
        {
          "condition": {
            "type": "onetrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "pres",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "impa",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "pass",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "futur",
                "flagSet": true,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "impe",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "attributesToEnable": [
            "cnombre",
            "cpersonne"
          ],
          "replAttributesToEnable": [
            "cnombre",
            "cpersonne"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "impe",
            "flagSet": true,
            "flagInRepl": false
          },
          "flagsToLimitTo": [
            "cp1",
            "cp2"
          ],
          "replFlagsToLimitTo": [
            "cp1",
            "cp2"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "ppas",
            "flagSet": true,
            "flagInRepl": false
          },
          "attributesToEnable": [
            "cnombre",
            "cgenre"
          ],
          "replAttributesToEnable": [
            "cnombre",
            "cgenre"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "pres",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "pres"
          ],
          "replAttributeFlagsToSetTo": [
            "pres"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "infi",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "infi"
          ],
          "replAttributeFlagsToSetTo": [
            "infi"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "ppas",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "ppas"
          ],
          "replAttributeFlagsToSetTo": [
            "ppas"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "impa",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "impa"
          ],
          "replAttributeFlagsToSetTo": [
            "impa"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "pass",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "pass"
          ],
          "replAttributeFlagsToSetTo": [
            "pass"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "futur",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "futur"
          ],
          "replAttributeFlagsToSetTo": [
            "futur"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "ppre",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "ppre"
          ],
          "replAttributeFlagsToSetTo": [
            "ppre"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "impe",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "impe"
          ],
          "replAttributeFlagsToSetTo": [
            "impe"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cs",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cs"
          ],
          "replAttributeFlagsToSetTo": [
            "cs"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp"
          ],
          "replAttributeFlagsToSetTo": [
            "cp"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp1",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp1"
          ],
          "replAttributeFlagsToSetTo": [
            "cp1"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp2",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp2"
          ],
          "replAttributeFlagsToSetTo": [
            "cp2"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cp3",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cp3"
          ],
          "replAttributeFlagsToSetTo": [
            "cp3"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cm",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cm"
          ],
          "replAttributeFlagsToSetTo": [
            "cm"
          ]
        },
        {
          "condition": {
            "type": "flag",
            "flag": "cf",
            "flagSet": true,
            "flagInRepl": false
          },
          "replFlagsToLimitTo": [
            "cf"
          ],
          "replAttributeFlagsToSetTo": [
            "cf"
          ]
        },
        {
          "attributesToDisable": [
            "kmj",
            "ha"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "\\p{Lu}",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "kmj"
          ],
          "attributeFlagsToSetTo": [
            "nomj"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]*[hH]",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "ha"
          ]
        },
        {
          "condition": {
            "type": "alltrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "ppas",
                "flagSet": false,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "cs",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "flagsToLimitTo": [
            "sn0",
            "ss"
          ],
          "replFlagsToLimitTo": [
            "sn0",
            "ss"
          ]
        },
        {
          "condition": {
            "type": "alltrue",
            "subConditions": [
              {
                "type": "flag",
                "flag": "ppas",
                "flagSet": false,
                "flagInRepl": false
              },
              {
                "type": "flag",
                "flag": "cp",
                "flagSet": true,
                "flagInRepl": false
              }
            ]
          },
          "flagsToLimitTo": [
            "sn0",
            "sp"
          ],
          "replFlagsToLimitTo": [
            "sn0",
            "sp"
          ]
        }
      ],
      "allowByPattern": {
        "pattern": "[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]+[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
        "flags": 0
      }
    },
    "cc": {
      "code": "cc",
      "attributes": {
        "type": {
          "code": "type",
          "definitionOnly": false,
          "flags": [
            {
              "code": "tlieu"
            },
            {
              "code": "ttps"
            },
            {
              "code": "tother"
            }
          ]
        },
        "sgenre": {
          "code": "sgenre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "sg0"
            },
            {
              "code": "sm"
            },
            {
              "code": "sf"
            }
          ]
        },
        "snombre": {
          "code": "snombre",
          "definitionOnly": false,
          "flags": [
            {
              "code": "sn0"
            },
            {
              "code": "ss"
            },
            {
              "code": "sp"
            }
          ]
        },
        "kmj": {
          "code": "kmj",
          "definitionOnly": true,
          "flags": [
            {
              "code": "kmj"
            },
            {
              "code": "kimj"
            },
            {
              "code": "nomj"
            }
          ]
        },
        "ha": {
          "code": "ha",
          "definitionOnly": true,
          "flags": [
            {
              "code": "ha"
            },
            {
              "code": "hm"
            }
          ]
        }
      },
      "updates": [
        {
          "attributeFlagsToSetTo": [
            "sg0",
            "sn0"
          ],
          "replAttributeFlagsToSetTo": [
            "tlieu",
            "ttps",
            "tother",
            "sg0",
            "sn0"
          ]
        },
        {
          "attributesToDisable": [
            "kmj",
            "ha"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "\\p{Lu}",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "kmj"
          ],
          "attributeFlagsToSetTo": [
            "nomj"
          ]
        },
        {
          "condition": {
            "type": "text",
            "pattern": {
              "pattern": "^[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]*[hH]",
              "flags": 0
            }
          },
          "attributesToEnable": [
            "ha"
          ]
        }
      ],
      "allowByPattern": {
        "pattern": "[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
        "flags": 0
      }
    }
  },
  "extractWordsPattern": {
    "pattern": "[\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d][\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d-]*",
    "flags": 0
  },
  "wordFollowedBySentenceEndMark": {
    "pattern": "(?:\\.(?![\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d])|[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d\\.]+\\.|[^\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d]*[\\?\\!]).*",
    "flags": 0
  }
}
                
            </div>
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
