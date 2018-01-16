/* Main js file
 */

(function ($) {
    // JS UTILS
    
    function getProp(obj, prop, def) {
        var result = def;
        var val;
        if (obj) {
            val = obj[prop];
            if (typeof val !== 'undefined') {
                result = val;
            }
        }
        return result;
    }
    function getProps(obj) {
        var props = [ ];
        if (obj) {
            for (var prop in obj) {
                if (obj.hasOwnProperty(prop)) {
                    props.push(prop);
                }
            }
        }
        return props;
    }
    function getPropValues(obj) {
        var values = [ ];
        if (obj) {
            for (var prop in obj) {
                if (obj.hasOwnProperty(prop)) {
                    values.push(obj[prop]);
                }
            }
        }
        return values;
    }
    function copyProps(src, dest) {
        var props = getProps(src);
        if (!dest) {
            dest = { };
        }
        var k;
        var prop;
        for (k = 0; k < props.length; k++) {
            prop = props[k];
            dest[prop] = src[prop];
        }
        return dest;
    }
    function makeInherit(subClassCtor, baseCtor) {
        var emptyCtor = function () {};
        var prototypeObj;
        emptyCtor.prototype = baseCtor.prototype;
        prototypeObj = new emptyCtor();
        prototypeObj.constructor = subClassCtor;
        subClassCtor.prototype = prototypeObj;
    }
    
    function inArray(value, arr) {
        return $.inArray(value, arr) !== -1;
    }
    function trim(str) {
        return $.trim(str);
    }
    function removeDiacriticalMarks(str) {
        // Heuristic ; not as good as server side and not language dependant
        // but sufficient for current needs
        var accents =    'ÀÁÂÃÄÅàáâãäåßÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
        var accentsOut = 'AAAAAAaaaaaaBOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz';
        var i, x;
        str = str.split('');
        for (i = 0; i < str.length; i++) {
            if ((x = $.inArray(str[i], accents)) !== -1) {
                str[i] = accentsOut[x];
            }
        }
        return str.join('');
    }

    function randomString() {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
        }
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
                s4() + '-' + s4() + s4() + s4();
    }
    
    function randomInt(min, max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }

    
    function log() {
        if (typeof window.console !== 'undefined') {
            console.log(arguments.length === 1 ? arguments[0] : arguments);
        }
    }
    
    function hasTouch() {
        return 'ontouchstart' in document.documentElement
                || navigator.maxTouchPoints > 0
                || navigator.msMaxTouchPoints > 0;
    }
    
    function focusPage(elt, opts) {
        opts = copyProps(opts, {
            'duration': 200,
            'offsetY': 0
        });
                
        $('html, body').animate({
            'scrollTop': elt.offset().top - opts.offsetY
        }, opts.duration);
    }
    

    
    // COMMON WEBSITE JS
    
    function showOverlay(show) {
        $('.main-overlay').toggleClass('opened', !!show);
        $('.container.main').toggleClass('overlay-opened', !!show);
    }
    
    function registerLinksNoFollow(context) {
        $('a.no-follow', context).on('click', function (e) {
            e.preventDefault();
        });
    }
    
    function initWebsiteNav() {
        $('.navbar .disconnect-menu a').click(function (e) {
            e.preventDefault();
            $('<form method="post" />')
                    .attr('action', $(this).attr('href'))
                    .appendTo(document.body)
                    .submit();
        });
    }

    var flagHasTouch = false;
    
    function updateHasTouch() {
        flagHasTouch = hasTouch();
        $(document.body).toggleClass('has-touch', flagHasTouch);
        $(document.body).toggleClass('not-touch', !flagHasTouch);
    }


    // SYNTAGME DEFINITION

    var FLAG_STATE_OFF = 0;
    var FLAG_STATE_ON = 1;
    var FLAG_STATE_DISABLED = -1;
    
    var FLAG_COMMON_PHASE_REPL = 'repl';
    
    function SyntagmeDefinitionAbstract(st) {
        if (!st) {
            throw new "No syntagme type";
        }
        this._st = st;
        
        this._attributes = { };
        this._attributeCodes = [ ];
        this._attributesByFlag = { };

        
        this._flags = { };
        this._chosenFlags = { };
        this._chosenAttributes = { };
        
        this._initAttributes();
        this._initAttributesByFlag();
    }
    
    var p = SyntagmeDefinitionAbstract.prototype;
    
    p.getSyntagmeType = function () {
        return this._st;
    };
    
    p._initAttributes = function () {
        this._attributes = this.getSyntagmeType().attributes;
        this._attributeCodes = getProps(this._attributes);
    };
    p._initAttributesByFlag = function () {
        var k;
        var attr;
        var f;
        var flags;
        var flag;
        
        this._attributesByFlag = { };
        for (k = 0; k < this._attributeCodes.length; k++) {
            attr = this._attributes[this._attributeCodes[k]];
            flags = this.getAttributeFlags(attr);
            for (f = 0; f < flags.length; f++) {
                flag = flags[f];
                this._attributesByFlag[flag.code] = attr;
            }
        }
    };
    p.getAttribute = function (attrCode) {
        return this._attributes[attrCode];
    };
    p.getAttributeByFlag = function (flagCode) {
        return this._attributesByFlag[flagCode];
    };
    p.getAttributeCodes = function () {
        return this._attributeCodes;
    };
    
    p._isAttributeMulti = function (attr) {
        return attr.multi;
    };
    p._isAttributeRequired = function (attr) {
        return attr.required;
    };
    
    p.getAttributeFlags = function (attr) {
        return attr.flags;
    };
    
    p.select = function (flagCode, flagOn) {
        var attr;
        
        if (this.getFlagState(flagCode) !== FLAG_STATE_DISABLED) {
            attr = this.getAttributeByFlag(flagCode);
            if (attr) {
                this._setFlag(flagCode, flagOn);
                this._updateAttributeChosenFlags(attr);
                this.update();
            }
        }
    };
    
    p._updateAttributeChosenFlags = function (attr) {
        var k;
        var flag;
        var flags;
        
        flags = this.getAttributeFlags(attr);
        for (k = 0; k < flags.length; k++) {
            flag = flags[k];
            this.setChosenFlag(flag.code, this.getFlagState(flag.code) === FLAG_STATE_ON);
        }
    };
    
    p.setChosenFlag = function (flagCode, flagOn) {
        var attr;
        var k;
        var flag;
        var flags;
        
        attr = this.getAttributeByFlag(flagCode);
        if (attr) {
            this._chosenAttributes[attr.code] = true;
            
            if (flagOn) {
                this._chosenFlags[flagCode] = true;
                
                if (!this._isAttributeMulti(attr)) {
                    flags = this.getAttributeFlags(attr);
                    for (k = 0; k < flags.length; k++) {
                        flag = flags[k];
                        if (flag.code !== flagCode) {
                            delete this._chosenFlags[flag.code];
                        }
                    }
                }
            } else {
                delete this._chosenFlags[flagCode];
            }
        }
    };
    
    p.resetChosenFlags = function (attributeCodes, flagCodes) {
        var k;
        var attrCode;
        
        this._chosenAttributes = { };
        this._chosenFlags = { };
        
        if (attributeCodes) {
            for (k = 0; k < attributeCodes.length; k++) {
                attrCode = attributeCodes[k];
                if (this.getAttribute(attrCode)) {
                    this._chosenAttributes[attrCode] = true;
                }
            }
        }
        if (flagCodes) {
            for (k = 0; k < flagCodes.length; k++) {
                this.setChosenFlag(flagCodes[k], true);
            }
        }
    };
    
    p.getChosenFlags = function () {
        return getProps(this._chosenFlags);
    };
    p.getChosenAttributes = function () {
        return getProps(this._chosenAttributes);
    };
    
    
    p.update = function () {
        
    };
    
    p.getFlagState = function (flagCode) {
        return getProp(this._flags, flagCode, FLAG_STATE_OFF);
    };
    
    p._setFlagState = function (flagCode, state) {
        var prevState;
        
        if (!this.getAttributeByFlag(flagCode)) {
            return false;
        }
        if (state !== FLAG_STATE_DISABLED && state !== FLAG_STATE_ON && state !== FLAG_STATE_OFF) {
            return false;
        }
        prevState = this._flags[flagCode];
        this._flags[flagCode] = state;
        return state !== prevState;
    };
    
    p._setFlag = function (flagCode, flagOn) {
        var result;
        var attr;
        var k;
        var flag;
        var flags;

        result = false;
        if (this.getFlagState(flagCode) !== FLAG_STATE_DISABLED) {
            result = this._setFlagState(flagCode, flagOn ? FLAG_STATE_ON : FLAG_STATE_OFF);
        }
        if (flagOn && result) {
            attr = this.getAttributeByFlag(flagCode);
            if (attr && !this._isAttributeMulti(attr)) {
                flags = this.getAttributeFlags(attr);
                for (k = 0; k < flags.length; k++) {
                    flag = flags[k];
                    if (flag.code !== flagCode) {
                        this._setFlag(flag.code, false);
                    }
                }
            }
        }

        return result;
    };
    
    p._setFlagEnabled = function (flagCode, flagEnabled) {
        if (flagEnabled) {
            if (this.getFlagState(flagCode) === FLAG_STATE_DISABLED) {
                this._setFlagState(flagCode, FLAG_STATE_OFF);
            }
        } else {
            this._setFlagState(flagCode, FLAG_STATE_DISABLED);
        }
    };
    
    p.isFlagOn = function (flagCode) {
        return this.getFlagState(flagCode) === FLAG_STATE_ON;        
    };
    
    p.isFlagEnabled = function (flagCode) {
        return this.getFlagState(flagCode) !== FLAG_STATE_DISABLED;
    };
    
    p.getFlagsOn = function () {
        var allFlags;
        var k;
        var f;
        var result = [ ];
        
        allFlags = getProps(this._flags);
        for (k = 0; k < allFlags.length; k++) {
            f = allFlags[k];
            if (this._flags[f] === FLAG_STATE_ON) {
                result.push(f);
            }
        }
        return result;
    };
    
    p._clearFlags = function () {
        this._flags = { };
    };
    
    p._updateFlagsFromChoices = function () {
        var k;
        var attr;
        var f;
        var flag;
        var attrCode;
        var flags;
        
        
        for (k = 0; k < this._attributeCodes.length; k++) {
            attrCode = this._attributeCodes[k];
            if (this._chosenAttributes[attrCode]) {
                attr = this.getAttribute(attrCode);
                flags = this.getAttributeFlags(attr);
                for (f = 0; f < flags.length; f++) {
                    flag = flags[f];
                    this._setFlag(flag.code, !!this._chosenFlags[flag.code]);
                }
            }
        }
    };
    
    p._setAllAttributeFlagsEnabled = function (attrCode, flagEnabled) {
        var attr = this.getAttribute(attrCode);
        var k;
        var flagCode;
        var flags;
        
        if (attr) {
            flags = this.getAttributeFlags(attr);
            for (k = 0; k < flags.length; k++) {
                flagCode = flags[k].code;
                this._setFlagEnabled(flagCode, flagEnabled);
            }
        }
    };
    
    p._limitAttributeFlagsTo = function (flagCodes) {
        var k;
        var attr;
        var f;
        var flagCode;
        var attrToLimit;
        var flags;

        for (k = 0; k < this._attributeCodes.length; k++) {
            attr = this.getAttribute(this._attributeCodes[k]);
            flags = this.getAttributeFlags(attr);
            attrToLimit = false;
            for (f = 0; !attrToLimit && f < flags.length; f++) {
                flagCode = flags[f].code;
                attrToLimit = inArray(flagCode, flagCodes);
            }
            if (attrToLimit) {
                for (f = 0; f < flags.length; f++) {
                    flagCode = flags[f].code;
                    this._setFlagEnabled(flagCode, inArray(flagCode, flagCodes));
                }
            }
        }
    };

    p._setAttributeFlagsTo = function (flagCodes) {
        var k;
        var attr;
        var f;
        var flagCode;
        var attrToSet;
        var flags;

        for (k = 0; k < this._attributeCodes.length; k++) {
            attr = this.getAttribute(this._attributeCodes[k]);
            flags = this.getAttributeFlags(attr);
            attrToSet = false;
            for (f = 0; !attrToSet && f < flags.length; f++) {
                flagCode = flags[f].code;
                attrToSet = inArray(flagCode, flagCodes);
            }
            if (attrToSet) {
                for (f = 0; f < flags.length; f++) {
                    flagCode = flags[f].code;
                    this._setFlag(flagCode, inArray(flagCode, flagCodes));
                }
            }
        }
    };
    
    p.isValid = function () {
        return this.isEveryNeededAttrFilled();
    };
    
    p.isEveryNeededAttrFilled = function () {
        var k;
        var attr;
        var f;
        var flagCode;
        var found;
        var oneAvailable;
        var state;
        var flags;

        for (k = 0; k < this._attributeCodes.length; k++) {
            attr = this.getAttribute(this._attributeCodes[k]);
            if (this._isAttributeRequired(attr)) {
                flags = this.getAttributeFlags(attr);

                found = false;
                oneAvailable = false;
                for (f = 0; !found && f < flags.length; f++) {
                    flagCode = flags[f].code;
                    state = this.getFlagState(flagCode);
                    if (state !== FLAG_STATE_DISABLED) {
                        oneAvailable = true;
                    }
                    if (state === FLAG_STATE_ON) {
                        found = true;
                    }
                }
                if (oneAvailable && !found) {
                    return false;
                }
            }
        }
        return true;
    };


    function SyntagmeReplacementDefinition(def) {
        this._def = def;
        
        SyntagmeDefinitionAbstract.call(this, def.getSyntagmeType());
    }
    makeInherit(SyntagmeReplacementDefinition, SyntagmeDefinitionAbstract);
    p = SyntagmeReplacementDefinition.prototype;

    p._initAttributes = function () {
        var allAttrs = this.getSyntagmeType().attributes;
        var allAttrCodes = getProps(allAttrs);
        var k;
        var attr;
        
        this._attributeCodes = [ ];
        this._attributes = { };
        for (k = 0; k < allAttrCodes.length; k++) {
            attr = allAttrs[allAttrCodes[k]];
            if (!attr.context && !attr.definitionOnly) {
                this._attributes[attr.code] = attr;
                this._attributeCodes.push(attr.code);
            }
        }
    };
    
    p._isAttributeMulti = function (attr) {
        return true;
    };
    p._isAttributeRequired = function (attr) {
        return true;
    };

    p.getDefinition = function () {
        return this._def;
    };
    
    p.update = function () {
        this.getDefinition().update();
    };


    function SyntagmeDefinition(st) {
        this._text = '';
        this._replacement = null;
        this._origDefinition = null;
        
        this._precedingWords = null;
        this._flagsToSelect = { };
        this._ctxVars = { };
        this._ctxSamples = [ ];
        this._ctxMultiVarSep = "/";
        
        SyntagmeDefinitionAbstract.call(this, st);
    }
    makeInherit(SyntagmeDefinition, SyntagmeDefinitionAbstract);
    p = SyntagmeDefinition.prototype;

    p.getReplacement = function () {
        if (!this._replacement) {
            this._replacement = new SyntagmeReplacementDefinition(this);
        }
        return this._replacement;
    };
    
    p.getOrigDefinition = function () {
        return this._origDefinition;
    };
    p.setOrigDefinition = function (sd) {
        this._origDefinition = sd;
    };
    
    p.setText = function (text) {
        this._text = this.normalizedText(text);
        this.update();
    };
    p.getText = function () {
        return this._text;
    };
    
    p.normalizedText = function (text) {
        if (typeof text !== 'string') {
            return '';
        }
        return trim(text.replace(/\s+/g, ' '));
    };

    p.setPrecedingWords = function (words) {
        this._precedingWords = words;
    };
        
    p.update = function () {
        var updates = this.getSyntagmeType().updates;
        var update;
        var k;
        var doUpdate;
        var sdr = this.getReplacement();
        var a;
        var n;
        var ctxVarData;
        var v;


        this._clearFlags();
        sdr._clearFlags();
        this._updateFlagsFromChoices();
        sdr._updateFlagsFromChoices();
        
        this._flagsToSelect = { };
        this._ctxVars = { };
        this._ctxSamples = [ ];
        
        for (k = 0; k < updates.length; k++) {
            update = updates[k];
            doUpdate = true;
            
            if (update.condition) {
                doUpdate = this._evaluateCondition(update.condition);
            }
            
            if (doUpdate) {

                if (update.attributesToDisable) {
                    for (a = 0; a < update.attributesToDisable.length; a++) {
                        this._setAllAttributeFlagsEnabled(update.attributesToDisable[a], false);
                    }
                }
                if (update.attributesToEnable) {
                    for (a = 0; a < update.attributesToEnable.length; a++) {
                        this._setAllAttributeFlagsEnabled(update.attributesToEnable[a], true);
                    }
                }
                if (update.flagsToLimitTo) {
                    this._limitAttributeFlagsTo(update.flagsToLimitTo);
                }
                if (update.flagsToDisable) {
                    for (a = 0; a < update.flagsToDisable.length; a++) {
                        this._setFlagEnabled(update.flagsToDisable[a], false);
                    }
                }
                if (update.attributeFlagsToSetTo) {
                    this._setAttributeFlagsTo(update.attributeFlagsToSetTo);
                }
                
                if (update.replAttributesToDisable) {
                    for (a = 0; a < update.replAttributesToDisable.length; a++) {
                        sdr._setAllAttributeFlagsEnabled(update.replAttributesToDisable[a], false);
                    }
                }
                if (update.replAttributesToEnable) {
                    for (a = 0; a < update.replAttributesToEnable.length; a++) {
                        sdr._setAllAttributeFlagsEnabled(update.replAttributesToEnable[a], true);
                    }
                }
                if (update.replFlagsToLimitTo) {
                    sdr._limitAttributeFlagsTo(update.replFlagsToLimitTo);
                }
                if (update.replAttributeFlagsToSetTo) {
                    sdr._setAttributeFlagsTo(update.replAttributeFlagsToSetTo);
                }
                
                
                if (update.flagsToSelect) {
                    this._flagsToSelect = { };
                    for (n = 0; n < update.flagsToSelect.length; n++) {
                        this._flagsToSelect[update.flagsToSelect[n]] = true;
                    }
                }
                
                if (update.ctxVar) {
                    ctxVarData = this._ctxVars[update.ctxVar];
                    if (!ctxVarData) {
                        this._ctxVars[update.ctxVar] = ctxVarData = {
                            'type': null,
                            'values': [ ]
                        };
                    }
                    if (update.ctxVarValueType === 'DEFAULT' || ctxVarData.type === 'DEFAULT') {
                        ctxVarData.values = [ ];
                    }
                    ctxVarData.type = update.ctxVarValueType;
                    if (update.ctxVarValues) {
                        for (n = 0; n < update.ctxVarValues.length; n++) {
                            v = update.ctxVarValues[n];
                            if (!inArray(v, ctxVarData.values)) {
                                ctxVarData.values.push(v);
                            }
                        }
                    }
                }
                if (update.ctxSamplesToAdd) {
                    this._ctxSamples.push(update.ctxSamplesToAdd);
                }

                
                this._updateFlagsFromChoices();
                sdr._updateFlagsFromChoices();
            }
        }
    };
    
    p._evaluateCondition = function (condition) {
        var result;
        var theSd;
        var k;
        var subConditions;
        var xreFlags;
        var textToTest;
        var idxOffset;
        var word;
        var pword;

        result = false;
        switch (condition.type) {
            case 'flagd':
            case 'flagr':
            case 'flagod':
            case 'flagor':
                theSd = null;
                switch (condition.type) {
                    case 'flagd':
                        theSd = this;
                        break;
                    case 'flagr':
                        theSd = this.getReplacement();
                        break;
                    case 'flagod':
                        theSd = this.getOrigDefinition();
                        break;
                    case 'flagor':
                        theSd = this.getOrigDefinition();
                        if (theSd) {
                            theSd = theSd.getReplacement();
                        }
                        break;
                }
                if (theSd) {
                    result = theSd.isFlagOn(condition.flag);
                    if (!condition.flagSet) {
                        result = !result;
                    }
                }
                break;
            case 'text':
                if (!condition.xre) {
                    xreFlags = '';
                    if (condition.caseInsensitive) {
                        xreFlags = 'i';
                    }
                    condition.xre = XRegExp(condition.pattern.pattern, xreFlags);
                }
                textToTest = this.getText();
                if (condition.withoutDiacritics) {
                    textToTest = removeDiacriticalMarks(textToTest);
                }
                result = condition.xre.test(textToTest);
                break;
            case 'pwords':
                if (this._precedingWords && condition.words) {
                    idxOffset = this._precedingWords.length - condition.words.length;
                    if (idxOffset >= 0) {
                        result = true;
                        for (k = 0; result && k < condition.words.length; k++) {
                            word = condition.words[k];
                            pword = this._precedingWords[k + idxOffset];
                            if (pword.toLowerCase() !== word) {
                                result = false;
                            }
                        }
                    }
                }
                break;
            case 'onetrue':
                subConditions = condition.subConditions;
                for (k = 0; !result && k < subConditions.length; k++) {
                    result = this._evaluateCondition(subConditions[k]);
                }
                break;
            case 'alltrue':
                result = true;
                subConditions = condition.subConditions;
                for (k = 0; result && k < subConditions.length; k++) {
                    result = this._evaluateCondition(subConditions[k]);
                }
                break;
        }
        return result;
    };
    
    p.getFlagsToSelect = function () {
        return getProps(this._flagsToSelect);
    };
    
    p.getCtxMultiVarSep = function () {
        return this._ctxMultiVarSep;
    };
    p.setCtxMultiVarSep = function (sep) {
        this._ctxMultiVarSep = sep;
    };
    
    p.getCtxVarStrValue = function (ctxVar) {
        var ctxVarData;
        
        ctxVarData = this._ctxVars[ctxVar];
        if (ctxVarData && ctxVarData.values.length > 0) {
            switch (ctxVarData.type) {
                case 'MULTI':
                    return ctxVarData.values.join(this.getCtxMultiVarSep());
                case 'RAND':
                    return ctxVarData.values[randomInt(0, ctxVarData.values.length)];
            }
            return ctxVarData.values[0];
        }
        return null;
    };
    
    p.getCtxFinalValue = function (ctxVar) {
        return this._resolveCtxSampleVars(this.getCtxVarStrValue(ctxVar), '', 0);
    };
    
    p.getCtxPrefix = function () {
        return this.getCtxFinalValue('PREFIX');
    };
    p.getCtxSuffix = function () {
        return this.getCtxFinalValue('SUFFIX');
    };
    
    p.getFinalCtxSamples = function (text) {
        var result;
        var k;
        var samples;
        var sample;
        
        result = [ ];
        for (k = 0; k < this._ctxSamples.length; k++) {
            samples = this._ctxSamples[k];
            if (samples.length > 0) {
                if (samples.length > 1) {
                    sample = samples[randomInt(0, samples.length)];
                } else {
                    sample = samples[0];
                }
                
                sample = this._resolveCtxSampleVars(sample, text, 0);
                if (sample) {
                    result.push(sample);
                }
            }
        }
        return result;
    };
    
    p._resolveCtxSampleVars = function (sample, text, recursNum) {
        var re;
        var reResult;
        var ctxVar;
        var ctxVarValue;
        var result;
        var resolveAgain;
        
        result = sample;
        resolveAgain = false;
        re = /\{(\w+)\}/g;
        while ((reResult = re.exec(sample)) !== null) {
            ctxVar = reResult[1];
            ctxVarValue = null;
            switch (ctxVar) {
                case 'TEXT':
                    ctxVarValue = text;
                    break;
                default:
                    ctxVarValue = this.getCtxVarStrValue(ctxVar);
                    break;
            }
            if (ctxVarValue === null) {
                return null;
            }
            result = result.replace(reResult[0], ctxVarValue);
            resolveAgain = true;
        }

        if (resolveAgain && recursNum <= 10) {
            result = this._resolveCtxSampleVars(result, text, recursNum + 1);
        }
        return result;
    };

    
    p.isAvailable = function () {
        var result = true;
        var st = this.getSyntagmeType();
        
        if (st.allowByPattern) {
            result = false;
            if (!st.allowByPattern.rex) {
                st.allowByPattern.rex = XRegExp(st.allowByPattern.pattern);
            }
            result = st.allowByPattern.rex.test(this.getText());
        }
        return result;
    };
    
    p.isValid = function () {
        if (this.isAvailable()) {
            return this.isEveryNeededAttrFilled()
                    && this.getReplacement().isEveryNeededAttrFilled();
        }
        return false;
    };
    
    
//    function ReplacementSyntagmeDefinition(st, possibleFlags) {
//        this._possibleFlags = possibleFlags;
//        
//        SyntagmeDefinition.call(this, st);
//        
//        this._presetChoices();
//    }
//    makeInherit(ReplacementSyntagmeDefinition, SyntagmeDefinition);
//    p = ReplacementSyntagmeDefinition.prototype;
//
//    p.getAttributeFlags = function (attr) {
//        var flags = [ ];
//        var k;
//        var flag;
//        
//        for (k = 0; k < attr.flags.length; k++) {
//            flag = attr.flags[k];
//            // Note : "definition only" flags are all available
//            if (attr.definitionOnly || inArray(flag.code, this._possibleFlags)) {
//                flags.push(flag);
//            }
//        }
//        return flags;
//    };
//    
//    p._presetChoices = function () {
//        var k;
//        var attr;
//        var f;
//        var flags;
//        
//        var chosenFlags = [ ];
//        
//        // Auto-selects one-choice attributes
//        for (k = 0; k < this._attributeCodes.length; k++) {
//            attr = this.getAttribute(this._attributeCodes[k]);
//            flags = this.getAttributeFlags(attr);
//            if (flags.length === 1) {
//                chosenFlags.push(flags[0].code);
//            }
//        }
//        
//        this.resetChosenFlags(null, chosenFlags);
//    };
    
    
    
    
    

    // TEMPLATE EDIT
    

    function initTemplateEdit(mainContainer) {
        
        var pageContainer = $('.container.main', mainContainer);
        var containers = [ ];
        var placeholders = [ ];
        var placeholderNum = 0;
        var removedBlockIds = [ ];
        var descrRejected = false;
        
        var eraseMode = false;
        var removedBlocksChanged = false;
        var placeholdersChanged = false;
        var onSave = false;
        var curTooltipsEnabled = null;
        var userDataToUpdate = null;
        
        var placeholderMaxTextLg = null;
        var minWordsConf = null;
        var minPlaceholdersConf = null;
        var maxWordsByPlaceholdersConf = null;
        
        var sdefReusableAttrCode = null;
        var sdefReusableFlagOn = null;
        var sdefReusableFlagOff = null;
        
        var wordEltSelector = 'a[data-wid]';
        var wordCount = 0;
        var origTotalWordCount = 0;
        var origDescrWordCount = 0;
        var containerEltSelector = '.template-edit-part';
        var containersElts = $(containerEltSelector, mainContainer);
        var descrContainer = $('.descr-part ' + containerEltSelector, mainContainer);
        
        var textBlockSelector = '[data-blkid]';
        var textBlockEltsCache = getTextBlockElts();
        
        var langData = null;
        var syntagmeTypes = [ ];

        var submitForm = $('.submit-form', mainContainer);
        var templateId = $('input[name=id]', submitForm).val();
        var saveUrl = submitForm.attr('action');

        
        var curSelection = null;
        var selectionPopoverContainerHtml = $('.tpl-selection-popover-container', mainContainer).remove().html();
        var selectionPopoverHtml = $('.tpl-selection-popover', mainContainer).remove().html();
        var selectionPopoverUsePlItemHtml = $('.tpl-selection-popover-use-placeholder-item', mainContainer).remove().html();
        var selectionPopoverSelector = '.selection-popover';
        var selectionPopoverBaseElt = null;
//        var stChoicePopupElt = $('.template-edit-st-choice', mainContainer);
        
        var curPlaceholder = null;
        var placeholderEltSelector = '.placeholder';
        var placeholderTpl = $('.tpl-placeholder', mainContainer).remove().html();
        var placeholderPopupElt = $('.template-edit-placeholder', mainContainer);
        var placeholderPopupTextViewContainer = $('.article-view', placeholderPopupElt);
        var placeholderPopupTextContainer = $('.content', placeholderPopupTextViewContainer);
        var placeholderPopupContentUpdateNeeded = true;
        var placeholderPopupReusableDefFlagElt = $('.reusable-def-flag', placeholderPopupElt);
        var placeholderPopupState = null;
        var placeholderSDefContainers = $('.placeholder-sdef', placeholderPopupElt);
        var sDefFlagSelector = '.sdef-flag';
        var placeholderSamplesText = "(...)";
        
        var navBarElt = $('.navbar', mainContainer);
        var wordsCountElt = $('.words-count', navBarElt);
        var wordsCountMinElt = $('.words-count-min', navBarElt);
        var placeholdersCountElt = $('.placeholders-count', navBarElt);
        var placeholdersCountMinElt = $('.placeholders-count-min', navBarElt);
        var saveMenuElt = $('.save-menu', navBarElt);
        var finishMenuElt = $('.finish-menu', navBarElt);
        var deleteMenuElt = $('.delete-menu', navBarElt);
        var rejectMenuElt = $('.reject-menu', navBarElt);
        var eraseModeMenuElt = $('.erase-mode-menu', navBarElt);
        var disableDescrElt = $('.disable-descr', mainContainer);
        var enableDescrElt = $('.enable-descr', mainContainer);
        var tutoContainer = $('.article-template-tuto', mainContainer);
        var tutoMenuElt = $('.tuto-menu', navBarElt);
        
        var genericErrorHtml = $('.tpl-alert-genric', mainContainer).remove().html();
        var userErrorHtml = $('.tpl-alert-user-error', mainContainer).remove().html();
        
        
        function getSyntagmeType(code) {
            return langData.syntagmeTypes[code];
        }

        function getEltWordId(elt) {
            var val = $(elt).data('wid');
            if (val) {
                val = parseInt(val);
                if (!isNaN(val)) {
                    return val;
                }
            }
            return null;
        }
        function getEltWordSentenceId(elt) {
            var val = $(elt).data('wsid');
            if (val) {
                val = parseInt(val);
                if (!isNaN(val)) {
                    return val;
                }
            }
            return null;
        }

        function getWordElt(wordId, context) {
            if (!context) {
                context = containersElts;
            }
            return $(wordEltSelector + '[data-wid=' + wordId + ']', context).first();
        }
        function getWordElts(context) {
            if (!context) {
                context = containersElts;
            }
            return $(wordEltSelector, context);
        }
                
        function getPlaceholderElts(ref, context) {
            if (!context) {
                context = containersElts.add(placeholderPopupTextContainer);
            }
            return $(placeholderEltSelector + '[data-placeholder-ref="' + ref + '"]', context);
        }
        
        function getNewPlaceholder() {
            return {
                'id': null,
                'reference': null,
                'num': null,
                'fromWordId': null,
                'nbWords': 0,
                'text': null,
                'prevWords': null,
                'sd': null,
                'stCode': null,
                'sdFlags': [ ],
                'sdAttributes': [ ],
                'srFlags': [ ],
                'srAttributes': [ ],
                'usePlaceholder': null,
                'ctxSamplesSd': null,
                'changed': false,
                'removed': false
            };
        }
                
        function getPlaceholderSDef(p) {
            if (!p.sd) {
                p.sd = new SyntagmeDefinition(getSyntagmeType(p.stCode));
                p.sd.resetChosenFlags(p.sdAttributes, p.sdFlags);
                p.sd.getReplacement().resetChosenFlags(p.srAttributes, p.srFlags);
                p.sd.setText(p.text);
            }
            return p.sd;
        }
        
        function getActivePlaceholders() {
            var result;
            var k;
            var p;
            
            result = [ ];
            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (!p.removed) {
                    result.push(p);
                }
            }
            return result;
        }
        
        function getActualPlaceholder(p) {
            if (p.usePlaceholder) {
                return getPlaceholder(p.usePlaceholder);
            }
            return p;
        }
        
        function getPlaceholder(ref) {
            var k;
            var p;
            
            if (ref) {
                for (k = 0; k < placeholders.length; k++) {
                    p = placeholders[k];
                    if (!p.removed && p.reference === ref) {
                        return p;
                    }
                }
            }
            return null;
        }
        function getPlaceholderByNum(num) {
            var k;
            var p;
            
            if (num && !isNaN(num)) {
                for (k = 0; k < placeholders.length; k++) {
                    p = placeholders[k];
                    if (!p.removed && p.num === num) {
                        return p;
                    }
                }
            }
            return null;
        }
        
        function updateDOMContainersPlaceholders() {
            var k;
            for (k = 0; k < containers.length; k++) {
                updateDOMContainerPlaceholders(containers[k]);
            }
            
            updatePlaceholdersUI();
            
            textBlockEltsCache = getTextBlockElts();
        };

        
        function updateDOMContainerPlaceholders(container) {
            var newContent = $(container.html);
            var ctx = {
                'placeholder': null,
                'wordText': null,
                'curSentenceId': null,
                'prevWordsElts': [ ]
            };
            replaceWords(ctx, newContent.get(0));

            container.container.empty();
            container.container.append(newContent);
        };
    
        function getPlaceholderByStartWordId(startWordId) {
            var k;
            var p;

            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (!p.removed && p.fromWordId > 0 && p.fromWordId === startWordId) {
                    return p;
                }
            }
            return null;
        };

        function replaceWords(ctx, node) {
            var child;
            var nextChild;
            var wordId;
            var sentenceId;
            var placeholder;
            var placeholderElt;

            for (child = node.firstChild; child; child = nextChild) {
                nextChild = child.nextSibling;

                switch (child.nodeType) {
                    // Text
                    case 3:
                        if (ctx.wordText !== null) {
                            ctx.wordText += child.nodeValue;
                        }
                        if (ctx.placeholder) {
                            ctx.placeholder.text += child.nodeValue;
                            node.removeChild(child);
                        }
                        break;
                    // Element
                    case 1:
                        wordId = getEltWordId(child);
                        if (wordId) {
                            sentenceId = getEltWordSentenceId(child);
                            // Sentence change : clear prev words
                            if (sentenceId !== ctx.curSentenceId) {
                                ctx.prevWords = [ ];
                            }
                            ctx.curSentenceId = sentenceId;
                        }
                        
                        if (wordId && !ctx.placeholder) {
                            placeholder = getPlaceholderByStartWordId(wordId);
                            if (placeholder) {
                                placeholder.text = '';
                                // Asked for prev words : give them
                                if (placeholder.prevWords) {
                                    placeholder.prevWords = ctx.prevWords;
                                }
                                ctx.placeholder = placeholder;
                                ctx.prevWords = [ ];

                                placeholderElt = createPlaceholderElt(placeholder);
                                placeholderElt.insertBefore(child);
                            }
                        }
                        
                        if (wordId) {
                            ctx.wordText = '';
                        }

                        replaceWords(ctx, child);
                        
                        if (wordId) {
                            ctx.prevWords.push(ctx.wordText);
                        }

                        if (ctx.placeholder && wordId) {
                            // Word to remove
                            node.removeChild(child);

                            // End of placeholder / word removal
                            if (ctx.placeholder.lastWordId <= wordId) {
                                ctx.placeholder = null;
                            }
                        }
                        break;
                }
            }
        }
        
        function createPlaceholderElt(placeholder) {
            var html;
            var elt;
            var referencedPl;
            var actualPl;
            
            actualPl = placeholder;
            referencedPl = null;
            if (placeholder.usePlaceholder) {
                referencedPl = getPlaceholder(placeholder.usePlaceholder);
                actualPl = referencedPl;
            }
            
            html = placeholderTpl.replace(/\{num\}/g, placeholder.num)
                    .replace(/\{useNum\}/g, actualPl.num)
                    .replace(/\{ref\}/g, placeholder.reference)
                    .replace(/\{st\}/g, actualPl.stCode);
            elt = $(html);
            return elt;
        }
        
        function updatePlaceholderUI(placeholder, placeholderElts) {
            var referencedPl;
            var actualPl;
            var useNumElt;
            var plFlags;
            var plAttrFlags;
            var sd;
            var k;
            var attrCodes;
            var attr;
            var flags;
            var f;
            var flag;
            var flagCode;
            var isEveryFlagOn;
            var flagsElt;
            
            if (!placeholderElts) {
                placeholderElts = getPlaceholderElts(placeholder.reference);
            }

            actualPl = placeholder;
            referencedPl = null;
            useNumElt = placeholderElts.find('.placeholder-use-num');
            if (placeholder.usePlaceholder) {
                referencedPl = getPlaceholder(placeholder.usePlaceholder);
                actualPl = referencedPl;
                useNumElt.show();
            } else {
                useNumElt.hide();
            }
            
            placeholderElts.attr('title', placeholder.text);
                        
            plFlags = [ ];
            sd = getPlaceholderSDef(actualPl);
            attrCodes = sd.getAttributeCodes();
            for (k = 0; k < attrCodes.length; k++) {
                attr = sd.getAttribute(attrCodes[k]);
                if (attr.context) {
                    plAttrFlags = [ ]; 
                    flags = sd.getAttributeFlags(attr);
                    for (f = 0; f < flags.length; f++) {
                        flag = flags[f];
                        flagCode = flag.code;
                        if (flag.quickCode && sd.isFlagOn(flagCode)) {
                            plAttrFlags.push(flag.quickCode);
                        }
                    }
                    if (plAttrFlags.length > 0) {
                        plFlags.push(plAttrFlags.join('/'));
                    }
                }
            }
            
            sd = sd.getReplacement();
            attrCodes = sd.getAttributeCodes();
            for (k = 0; k < attrCodes.length; k++) {
                attr = sd.getAttribute(attrCodes[k]);

                plAttrFlags = [ ]; 
                flags = sd.getAttributeFlags(attr);
                isEveryFlagOn = true;
                for (f = 0; f < flags.length; f++) {
                    
                    flag = flags[f];
                    flagCode = flag.code;
                    if (sd.isFlagOn(flagCode)) {
                        if (flag.quickCode) {
                            plAttrFlags.push(flag.quickCode);
                        }
                    } else {
                        isEveryFlagOn = false;
                    }
                }
                if (!isEveryFlagOn && plAttrFlags.length > 0) {
                    plFlags.push(plAttrFlags.join('/'));
                }
            }


            
            flagsElt = placeholderElts.find('.placeholder-flags');
            if (plFlags.length > 0) {
                flagsElt.html(plFlags.join('&nbsp;')).show();
            } else {
                flagsElt.html('').hide();
            }
        }
        
//        function updateCurrentlySelectedPlaceholderUI() {
//            $(placeholderEltSelector).removeClass('current');
//            if (curPlaceholder) {
//                getPlaceholderElts(curPlaceholder.reference).addClass('current');
//            }
//        }
        
        function updatePlaceholdersUI() {
            var k;
            var p;
            
            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (!p.removed) {
                    updatePlaceholderUI(p);
                }
            }
//            updateCurrentlySelectedPlaceholderUI();
        }
        
        
        function getWordRangeText(fromWordId, toWordId) {
            var k;
            var ctx;
            var wordElt;
            
            ctx = {
                'rootElt': null,
                'lastWordId': toWordId,
                'text': '',
                'done': false
            };
            
            wordElt = getWordElt(fromWordId);
            if (wordElt) {
                ctx.rootElt = wordElt.parents(containerEltSelector);
            }
//            for (k = 0; !wordElt && k < containers.length; k++) {
//                ctx.rootElt = $(containers[k].html);
//                wordElt = getWordElt(fromWordId, ctx.rootElt).get(0);
//            }
            
            if (wordElt.length === 1 && ctx.rootElt.length === 1 && toWordId >= fromWordId) {
                browseDomUntilWord(ctx, wordElt.get(0));
                return ctx.text;
            }
            return null;
        }
        
        function browseDomUntilWord(ctx, node) {
            var wordId;
            var next;
            var gotoNext = true;

            switch (node.nodeType) {
                // Element
                case 1:
                    wordId = getEltWordId(node);
                    if (wordId) {
                        ctx.text += $(node).text();
                        if (wordId >= ctx.lastWordId) {
                            gotoNext = false;
                        }
                    } else {
                        if (node.firstChild) {
                            gotoNext = false;
                            browseDomUntilWord(ctx, node.firstChild);
                        }
                    }
                    break;
                // Text
                case 3:
                    ctx.text += node.nodeValue;
                    break;
            }

            if (gotoNext) {
                next = node.nextSibling;
                while (!next) {
                    node = node.parentNode;
                    if (!node || ctx.rootElt.is(node)) {
                        break;
                    }
                    next = node.nextSibling;
                }
                if (next) {
                    browseDomUntilWord(ctx, next);
                }
            }
            
        }
        
        function hasPlaceholderBetween(wordId1, wordId2) {
            var k;
            var p;
            
            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (!p.removed && wordId1 <= p.lastWordId && wordId2 >= p.fromWordId) {
                    return true;
                }
            }
        }
        
        function isWordEltInRemovedBlock(wordElt) {
            if (wordElt.parents('.removed').length > 0) {
                return true;
            }
            if (descrRejected && descrContainer.has(wordElt).length > 0) {
                return true;
            }
            return false;
        }
        
        function wordClick(wordElt) {
            var wordId;
            var sId;
            var newFromId;
            var newToId;

            if (eraseMode) {
                return;
            }
            if (isWordEltInRemovedBlock(wordElt)) {
                return;
            }

            wordId = getEltWordId(wordElt);
            sId = wordElt.data('wsid');

            if (curSelection) {
                if (sId === curSelection.sid) {

                    newFromId = curSelection.from;
                    newToId = curSelection.to;
                    
                    if (wordId >= curSelection.from && wordId <= curSelection.to) {
                        if (wordId === curSelection.from || wordId === curSelection.to) {
                            newFromId = newToId = wordId;
                        } else {
                            newToId = wordId;
                        }
                    } else if (wordId < curSelection.from) {
                        newFromId = wordId;
                    } else if (wordId > curSelection.to) {
                        newToId = wordId;
                    }
                    
                    updateCurSelection(sId, newFromId, newToId);
                }
            } else {
                updateCurSelection(sId, wordId, wordId);
            }
        }
        
        function updateCurSelection(sId, fromId, toId) {
            var k;
            var sd;
            
            if ((!curSelection || fromId !== curSelection.from || toId !== curSelection.to)
                    && !hasPlaceholderBetween(fromId, toId)) {
                if (!curSelection) {
                    curSelection = {
                        'sid': sId,
                        'from': null,
                        'to': null,
                        'sdList': [ ],
                        'baseElt': null
                    };
                    for (k = 0; k < syntagmeTypes.length; k++) {
                        sd = new SyntagmeDefinition(syntagmeTypes[k]);
                        curSelection.sdList.push(sd);
                    }
                }

                curSelection.from = fromId;
                curSelection.to = toId;
                curSelection.text = getWordRangeText(fromId, toId);
                
                for (k = 0; k < curSelection.sdList.length; k++) {
                    sd = curSelection.sdList[k];
                    sd.setText(curSelection.text);
                }
                
                updateCurSelectionUI();
            }
        }
        
        function cancelSelection() {
            curSelection = null;
            updateCurSelectionUI();
        }
        
        function getSelectionPopoverElt() {
            return $('.selection-popover', pageContainer);
        }
        
        function updateCurSelectionUI() {
            var k;
            var choiceBlocks;
            var sd;
            var word;
            var wordElts;
            var popoverContainerElt;
            var p;
            var usePlaceholderContainer;
            var usePlDropdownItemsContainer;
            var itemHtml;
            var itemElt;

            
            $(wordEltSelector, containersElts).removeClass('selected');

            if (curSelection) {
//                var html;
//                var lastWordElt;
                
                for (word = curSelection.from; word <= curSelection.to; word++) {
                    getWordElt(word).addClass('selected');
                }

                if (!selectionPopoverBaseElt) {
                    selectionPopoverBaseElt = 
                            getWordElt(curSelection.to, containersElts);
                    
                    selectionPopoverBaseElt.popover({
                        'template': selectionPopoverContainerHtml,
                        'content': selectionPopoverHtml,
                        'html': true,
                        'placement': 'bottom',
                        'container': pageContainer
                    }).popover('show');
                    
                    popoverContainerElt = getSelectionPopoverElt();

                    usePlaceholderContainer = popoverContainerElt.find('.use-placeholder');
                    usePlDropdownItemsContainer = usePlaceholderContainer.find('.dropdown-menu');
                    for (k = 0; k < placeholders.length; k++) {
                        p = placeholders[k];
                        if (!p.removed && !p.usePlaceholder && 
                                p.stCode && getSyntagmeType(p.stCode).referencesAllowed) {
                            itemHtml = selectionPopoverUsePlItemHtml
                                    .replace(/\{ref\}/g, p.reference)
                                    .replace(/\{num\}/g, p.num);
                            itemElt = $(itemHtml);
                            itemElt.find('.pl-text').text(p.text);

                            usePlDropdownItemsContainer.append(itemElt);
                        }
                    }
                    
                    if (usePlDropdownItemsContainer.children().length === 0) {
                        usePlaceholderContainer.remove();
                    }
                    
                    if (curTooltipsEnabled) {
                        $('[data-toggle="popover"]', popoverContainerElt).popover({
                            'container': popoverContainerElt
                        });
                    }
                    
//                    focusPage(popoverContainerElt, {
//                        'offsetY': popoverContainerElt.data('focusPageOffsetY')
//                    });
                    
                } else {
                    popoverContainerElt = getSelectionPopoverElt();
                }
                
//                html = selectionPopoverHtml.replace(/\{text\}/g, curSelection.text);
                
                
                
                
                $('.sel-text', popoverContainerElt).text(curSelection.text);
                
                choiceBlocks = $('.st-choice', popoverContainerElt).removeClass('disabled');
                
                for (k = 0; k < curSelection.sdList.length; k++) {
                    sd = curSelection.sdList[k];
                    if (!sd.isAvailable()) {
                        choiceBlocks.filter(
                                '[data-st=' + sd.getSyntagmeType().code + ']')
                            .addClass('disabled');
                    }
                }
                
                
                
//                wordElts = $();
//                for (word = curSelection.from; word <= curSelection.to; word++) {
//                    wordElts = wordElts.add(getWordElt(word, containersElts));
//                }
//                positionPopupUnder(stChoicePopupElt, wordElts);
//                stChoicePopupElt.addClass('opened');
                
            } else {
                if (selectionPopoverBaseElt) {
                    selectionPopoverBaseElt.popover('destroy');
                    selectionPopoverBaseElt = null;
                }
            }
        }
        
//        function positionPopupUnder(popupElt, relatedElts) {
//            var k;
//            var elt;
//            var position;
//            var right;
//            var bottom;
//            var minLeft;
//            var maxLeft;
//            var maxTop;
//            var popupWidth;
//            var mainWidth;
//            var middleLeft;
//            var popupLeft;
//
//            
//            if (relatedElts.length > 0) {
//                minLeft = null;
//                maxLeft = null;
//                maxTop = null;
//                for (k = 0; k < relatedElts.length; k++) {
//                    elt = relatedElts.eq(k);
//                    position = elt.position();
//                    right = position.left + elt.width();
//                    bottom = position.top + elt.height();
//                    if (minLeft === null || position.left < minLeft) {
//                        minLeft = position.left;
//                    }
//                    if (maxLeft === null || right > maxLeft) {
//                        maxLeft = right;
//                    }
//                    if (maxTop === null || bottom > maxTop) {
//                        maxTop = bottom;
//                    }
//                }
//
//                middleLeft = (minLeft + maxLeft) / 2;
//                popupWidth = popupElt.width();
//                mainWidth = mainContainer.width();
//                popupLeft = middleLeft - popupWidth / 2;
//                // Avoids overflowing right
//                if (popupLeft + popupWidth > mainWidth) {
//                    popupLeft = mainWidth - popupWidth;
//                }
//                // Avoids overflowing left (most important)
//                if (popupLeft < 0) {
//                    popupLeft = 0;
//                }
//                popupElt.css('left', Math.round(popupLeft) + 'px');
//                popupElt.css('top', maxTop + 'px');
//            }
//        }
        
        function getCurSelectionSDef(stCode) {
            var sd;
            var k;
            
            if (curSelection) {
                for (k = 0; k < curSelection.sdList.length; k++) {
                    sd = curSelection.sdList[k];
                    if (sd.getSyntagmeType().code === stCode) {
                        return sd;
                    }
                }
            }
            return null;
        }
        
        function makePlaceholder(stCode) {
            var sd;
            var p;
            var flagsToSelect;

            if (curSelection) {
                sd = getCurSelectionSDef(stCode);
                if (sd) {
                    p = getNewPlaceholder();
                    p.reference = randomString();
                    p.num = ++placeholderNum;
                    p.changed = true;
                    p.fromWordId = curSelection.from;
                    p.nbWords = curSelection.to - curSelection.from + 1;
                    p.lastWordId = curSelection.to;
                    p.stCode = sd.getSyntagmeType().code;
                    p.sd = sd;
                    p.text = sd.getText();
                    p.prevWords = [ ]; // Asks for prev words
                    placeholders.push(p);
                    
                    placeholdersChanged = true;
                    
                    curPlaceholder = p; 
                    
                    cancelSelection();
                    
                    updateDOMContainersPlaceholders();
                    updateDOMRemovedBlocks();
                    
                    // Auto-select flags from context text at placeholder creation only
                    sd.setPrecedingWords(p.prevWords);
                    sd.update();
                    flagsToSelect = sd.getFlagsToSelect();
                    sd.setPrecedingWords(null);
                    if (flagsToSelect.length > 0) {
                        sd.resetChosenFlags(null, flagsToSelect);
                        sd.update();
                    }
                                        
                    updateCurPlaceholderUI();
                    updateGlobalUI();
                }
            }
        }
        function makePlaceholderFromAnother(baseRef) {
            var baseP;
            var p;

            if (curSelection) {
                baseP = getPlaceholder(baseRef);
                if (baseP && baseP.stCode) {
                    p = getNewPlaceholder();
                    p.reference = randomString();
                    p.num = ++placeholderNum;
                    p.changed = true;
                    p.fromWordId = curSelection.from;
                    p.nbWords = curSelection.to - curSelection.from + 1;
                    p.lastWordId = curSelection.to;
                    p.usePlaceholder = baseP.reference;
                    p.text = curSelection.text;
                    placeholders.push(p);
                    
                    placeholdersChanged = true;
                    
                    curPlaceholder = null;
                    cancelSelection();
                    
                    updateDOMContainersPlaceholders();
                    updateDOMRemovedBlocks();
                    updateCurPlaceholderUI();
                    updateGlobalUI();
                }
            }
        }
        
        function removeCurPlaceholder() {
            if (curPlaceholder) {
                removePlaceholder(curPlaceholder.reference);
                curPlaceholder = null;
                placeholdersChanged = true;
                updateCurPlaceholderUI();
                updateDOMContainersPlaceholders();
                updateGlobalUI();
            }
        }
        
        function removePlaceholder(ref) {
            var k;
            var p;
            var changes;
            
            changes = false;
            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (ref && !p.removed && 
                        (p.reference === ref || p.usePlaceholder === ref)) {
                    p.removed = true;
                    changes = true;
                    placeholdersChanged = true;
                }
            }
            return changes;
        }
        
        function editPlaceholder(reference) {
            if (eraseMode) {
                return;
            }
            
            curPlaceholder = getPlaceholder(reference);
//            updateCurrentlySelectedPlaceholderUI();
            updateCurPlaceholderUI();
        }
        
        function getCurPlaceholderSDefContainer() {
            var actualP;
            
            if (curPlaceholder) {
                actualP = getActualPlaceholder(curPlaceholder);
                return placeholderSDefContainers.filter('[data-st=' + actualP.stCode + ']');
            }
            return $();
        }
        
        function updatePlaceholderPopupArticleContent() {
            var inputContainer;
            var outputContainer;
            
            if (placeholderPopupContentUpdateNeeded) {
                inputContainer = $('.title-part ' + containerEltSelector);
                outputContainer = placeholderPopupTextContainer.find('.title-container').empty();
                outputContainer.html(inputContainer.children().html());
                
                outputContainer = placeholderPopupTextContainer.find('.descr-container').empty();
                if (!descrRejected) {
                    outputContainer.html(descrContainer.children().html());
                }
                
                inputContainer = $('.content-part ' + containerEltSelector);
                outputContainer = placeholderPopupTextContainer.find('.content-container').empty();
                outputContainer.html(inputContainer.children().html());
                getRemovedTextBlockElts(outputContainer).remove();

                placeholderPopupContentUpdateNeeded = false;
            }
        }
        
        function focusPlaceholderPopupArticleContent() {
            var placeholderElt;
            var containerHeight;
            var scrollTop;
            var eltTop;
            
            $(placeholderEltSelector, placeholderPopupTextContainer).removeClass('current');
            placeholderElt = $();
            if (curPlaceholder) {
                placeholderElt = getPlaceholderElts(curPlaceholder.reference, 
                        placeholderPopupTextContainer);
                placeholderElt.addClass('current');
            }

            if (placeholderElt.length === 1 
                    && placeholderPopupState === 'opened') {
                containerHeight = placeholderPopupTextViewContainer.height();
                eltTop = placeholderElt.position().top;
                if (containerHeight > 0 && typeof eltTop === 'number' && !isNaN(eltTop)) {
                    scrollTop = eltTop - containerHeight * 0.3;
//                    log(containerHeight, eltTop, scrollTop);
                    placeholderPopupTextViewContainer.animate({
                        'scrollTop': scrollTop
                    }, 200);
                }
            }
        }
        
        function updateCurPlaceholderUI() {
//            var placeholderElt;
            var usePlaceholderElt;
            var usePlaceholderP;

            if (curPlaceholder) {                
                placeholderPopupElt.find('.orig-text').text(curPlaceholder.text);
                
                placeholderPopupElt.find('.modal-title .pl-num').text(curPlaceholder.num);
                usePlaceholderElt = placeholderPopupElt.find('.modal-title small').hide();
                if (curPlaceholder.usePlaceholder) {
                    usePlaceholderP = getPlaceholder(curPlaceholder.usePlaceholder);
                    usePlaceholderElt.find('.pl-use-num')
                            .text(usePlaceholderP ? usePlaceholderP.num : '');
                    usePlaceholderElt.show();
                }
                placeholderSDefContainers.hide();
                getCurPlaceholderSDefContainer().show();
                updateSDefUI();
                
                updateCtxSamplesUI();
                
                
//                placeholderElt = getPlaceholderElt(curPlaceholder.reference);
//                positionPopupUnder(placeholderPopupElt, placeholderElt);
                
                updatePlaceholderPopupArticleContent();
                focusPlaceholderPopupArticleContent();
                
                placeholderPopupElt.modal('show');
            } else {
                placeholderPopupElt.modal('hide');
            }
        }
        
        function sDefFlagClick(flagBtnElt) {
            var sDefContainer;
            var baseSd;
            var actualSd;
            var activeInUI;
            var flagCode;


            if (!curPlaceholder) {
                return;
            }
            sDefContainer = getCurPlaceholderSDefContainer();
            if (sDefContainer.has(flagBtnElt).length === 0) {
                return;
            }

            baseSd = getPlaceholderSDef(getActualPlaceholder(curPlaceholder));
            actualSd = baseSd;
            if (flagBtnElt.parents('.sdef.replacement').length === 1) {
                actualSd = baseSd.getReplacement();
            }
            
            flagCode = flagBtnElt.data('flag');
            if (actualSd.isFlagEnabled(flagCode)) {
                activeInUI = flagBtnElt.hasClass('active');
                actualSd.select(flagCode, !activeInUI);
                
                sDefFlagChange();
            }
        }
        
        function sDefFlagChange() {
            placeholdersChanged = true;
            updatePlaceholdersUI();

            updateSDefUI();
            updateCtxSamplesUI();
            clearPlaceholdersValidation();
        }
        
        function sDefReusableFlagChange(selected) {
            var sd;
            var flagCode;

            if (!curPlaceholder) {
                return;
            }
            sd = getPlaceholderSDef(getActualPlaceholder(curPlaceholder));
            flagCode = selected ? sdefReusableFlagOn : sdefReusableFlagOff;
            if (flagCode) {
                sd.select(flagCode, true);
                sDefFlagChange();
            }
        }
        
        function updateSDefUI() {
            var sDefContainer;
            var sd;
            
            if (!curPlaceholder) {
                return;
            }

            sd = getPlaceholderSDef(getActualPlaceholder(curPlaceholder));
            sDefContainer = getCurPlaceholderSDefContainer();
            updateSDefUIFor(sDefContainer.find('.sdef.context'), sd);
            updateSDefUIFor(sDefContainer.find('.sdef.definition'), sd);
            updateSDefUIFor(sDefContainer.find('.sdef.replacement'), sd.getReplacement());
            updateSDefReusableFlagUI(sd);
        }
        function updateSDefUIFor(sDefContainer, sd) {
            var attrCodes;
            var k;
            var attr;
            var f;
            var flagCode;
            var hasFlagEnabled;
            var attrContainer;
            var flagElt;
            var flags;

            attrCodes = sd.getAttributeCodes();
            for (k = 0; k < attrCodes.length; k++) {
                attr = sd.getAttribute(attrCodes[k]);
                hasFlagEnabled = false;
                attrContainer = sDefContainer.find('.sdef-attr.attr-' + attr.code);
                flags = sd.getAttributeFlags(attr);
                for (f = 0; f < flags.length; f++) {
                    flagCode = flags[f].code;
                    flagElt = attrContainer.find(sDefFlagSelector + '[data-flag=' + flagCode + ']');
                    switch (sd.getFlagState(flagCode)) {
                        case FLAG_STATE_DISABLED:
                            flagElt.removeClass('active').addClass('disabled');
                            break;
                        case FLAG_STATE_OFF:
                            flagElt.removeClass('active').removeClass('disabled');
                            hasFlagEnabled = true;
                            break;
                        case FLAG_STATE_ON:
                            flagElt.removeClass('disabled').addClass('active');
                            hasFlagEnabled = true;
                            break;
                    }
                }
                
                attrContainer.toggle(hasFlagEnabled);
            }
        }
        function updateSDefReusableFlagUI(sd) {
            placeholderPopupReusableDefFlagElt.find('input')
                .prop('checked', sd.isFlagOn(sdefReusableFlagOn));
        }

        function updateCtxSamplesUI() {
            var p;
            var origSd;
            var ctxSd;
            var samples;
            var k;
            var container;
            var listElt;
            var invalidElt;
            
            if (curPlaceholder) {
                p = getActualPlaceholder(curPlaceholder);
                origSd = getPlaceholderSDef(p);
                container = placeholderPopupElt.find('.context-samples');
                listElt = container.find('ul').empty();
                invalidElt = container.find('.invalid-msg');
                if (origSd.isValid()) {
                    invalidElt.hide();
                    listElt.show();
                    
                    ctxSd = p.ctxSamplesSd;
                    if (!ctxSd) {
                        p.ctxSamplesSd = ctxSd = new SyntagmeDefinition(origSd.getSyntagmeType());
                    }
                    ctxSd.setOrigDefinition(origSd);
                    ctxSd.update();
                    samples = ctxSd.getFinalCtxSamples(placeholderSamplesText);
                    for (k = 0; k < samples.length; k++) {
                        $('<li></li>').text(samples[k]).appendTo(listElt);
                    }
                } else {
                    listElt.hide();
                    invalidElt.show();
                }
            }
        }


        function switchToEraseMode(eraseModeOn) {
            eraseMode = !!eraseModeOn;
            
            cancelSelection();
            curPlaceholder = null;
            updateCurPlaceholderUI();
            updateGlobalUI();
        }


        function resetArticleTemplate(removedBlocks, placeholdersData) {
            var tabRemovedBlocks;
            var k;
            var blkId;
            var removedBlocksElts;
            var p;
            
            removedBlockIds = [ ];
            placeholders = [ ];
            placeholderNum = 0;
            
            curPlaceholder = null;
            cancelSelection();
            removedBlocksChanged = false;
            placeholdersChanged = false;

            tabRemovedBlocks = removedBlocks;
            if (typeof tabRemovedBlocks === 'string' && tabRemovedBlocks.length > 0) {
                tabRemovedBlocks = tabRemovedBlocks.split(',');
            } else if (typeof tabRemovedBlocks === 'number') {
                tabRemovedBlocks = [ '' + tabRemovedBlocks ];
            }
            if ($.isArray(tabRemovedBlocks)) {
                for (k = 0; k < tabRemovedBlocks.length; k++) {
                    blkId = parseInt(tabRemovedBlocks[k]);
                    if (!isNaN(blkId)) {
                        removedBlockIds.push(blkId);
                    }
                }                
            }

            // Reset containers once with no placeholders, to be able to check
            // placeholder validity
            updateDOMContainersPlaceholders();
            updateDOMRemovedBlocks();

            // Setup placeholders
            if ($.isArray(placeholdersData)) {
                removedBlocksElts = getRemovedTextBlockElts();
                if (descrRejected) {
                    removedBlocksElts = removedBlocksElts.add(descrContainer);
                }
                
                for (k = 0; k < placeholdersData.length; k++) {
                    p = copyProps(placeholdersData[k], getNewPlaceholder());
                    if (typeof p.reference === 'string' && p.reference !== ''
                            && typeof p.fromWordId === 'number' && p.fromWordId > 0
                            && typeof p.nbWords === 'number' && p.nbWords > 0
                            && getWordElt(p.fromWordId).length === 1
                            && getWordElt(p.fromWordId, removedBlocksElts).length === 0
                            && (p.usePlaceholder || getSyntagmeType(p.stCode))) {
                        p.lastWordId = p.fromWordId + p.nbWords - 1;
                        p.num = ++placeholderNum;
                        placeholders.push(p);
                    }
                }
                
                updateDOMContainersPlaceholders();
                updateDOMRemovedBlocks();

            }

            
            updateCurPlaceholderUI();
            updateGlobalUI();
        }
        
        
        function updateDOMRemovedBlocks() {
            getTextBlockElts().removeClass('removed');
            getRemovedTextBlockElts().addClass('removed');
            
            placeholderPopupContentUpdateNeeded = true;
            
            wordCount = origTotalWordCount - getWordElts(getCurrentlyRemovedBlockElts()).length;
            if (descrRejected) {
                wordCount -= origDescrWordCount;
            }
        }
        
        function getTextBlockElts(context) {
            if (!context) {
                context = containersElts;
            }
            return $(textBlockSelector, context);
        }
        
        function getRemovedTextBlockElts(context) {
            var selectors;
            var k;
            
            selectors = [ ];
            for (k = 0; k < removedBlockIds.length; k++) {
                selectors.push('[data-blkid=' + removedBlockIds[k] + ']');
            }
            return selectors.length > 0 ? getTextBlockElts(context).filter(selectors.join(',')) : $();
        }
        
//        function getTextBlockElt(blockId) {
//            return textBlocksElts.filter('[data-blkid=' + blockId + ']').first();
//        }
        
//        function getRemovedBlockElts() {
//            return $('.removed' + textBlockSelector, containersElts);
//        }
        function getCurrentlyRemovedBlockElts() {
            return $(textBlockSelector  + '.removed', containersElts);
        }
//        function getNonRemovedBlockElts() {
//            return $(textBlockSelector  + ':not(.removed)', containersElts);
//        }

//        function getNonRemovedWords() {
//            return $(wordEltSelector, getNonRemovedBlockElts());
//        }
        
        function removePlaceholdersInside(context) {
            var k;
            var p;
            var updateNeeded = false;
            
            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (!p.removed && getPlaceholderElts(p.reference, context).length > 0) {
                    updateNeeded = removePlaceholder(p.reference);
                }
            }
            return updateNeeded;
        }
        
        function blockClick(blockElt) {
            var blockId;
            var toRemove;
            var newRemovedBlockIds;
            var k;
            var p;
            var needsPlaceholdersUpdate;
            var needsRemovedBlocksUpdate;
            var found;

            
            if (!eraseMode) {
                return;
            }
            
            blockId = parseInt(blockElt.data('blkid'));
            if (isNaN(blockId)) {
                return;
            }
            
            toRemove = !blockElt.hasClass('removed');
            
            needsPlaceholdersUpdate = false;
            
            if (toRemove) {                
                // Remove placeholders in this block
                needsPlaceholdersUpdate = removePlaceholdersInside(blockElt);
            }
            
            if (needsPlaceholdersUpdate) {
                updateDOMContainersPlaceholders();
            }
            
            found = false;
            needsRemovedBlocksUpdate = false;
            newRemovedBlockIds = [ ];
            for (k = 0; k < removedBlockIds.length; k++) {
                if (removedBlockIds[k] === blockId) {
                    found = true;
                    if (toRemove) {
                        newRemovedBlockIds.push(removedBlockIds[k]);
                    } else {
                        needsRemovedBlocksUpdate = true;
                    }
                } else {
                    newRemovedBlockIds.push(removedBlockIds[k]);
                }
            }
            if (toRemove && !found) {
                newRemovedBlockIds.push(blockId);
                needsRemovedBlocksUpdate = true;
            }
            
            
            if (needsRemovedBlocksUpdate) {
                removedBlockIds = newRemovedBlockIds;
                updateDOMRemovedBlocks();
            }
            
            removedBlocksChanged = true;
            
            updateGlobalUI();
        }
        
        function getMinPlaceholders() {
            return Math.max(minPlaceholdersConf, 
                    Math.ceil(wordCount / maxWordsByPlaceholdersConf));
        }

        function canFinish() {
            return wordCount >= minWordsConf
                    && getActivePlaceholders().length >= getMinPlaceholders();
        }
        
        function updateGlobalUI() {
            var minPlaceholders;
            
            wordsCountElt.text(wordCount);
            wordsCountMinElt.text(isNaN(minWordsConf) ? '-' : minWordsConf);
            placeholdersCountElt.text(getActivePlaceholders().length);
            minPlaceholders = getMinPlaceholders();
            placeholdersCountMinElt.text(isNaN(minPlaceholders) ? '-' : minPlaceholders);
            
            saveMenuElt
                .toggleClass('disabled', !placeholdersChanged && !removedBlocksChanged)
                .toggleClass('save-needed', placeholdersChanged || removedBlocksChanged);
            finishMenuElt.toggleClass('disabled', !canFinish());
            
            eraseModeMenuElt.toggleClass('active', eraseMode);
            mainContainer.toggleClass('erase-mode', eraseMode);
            mainContainer.toggleClass('select-mode', !eraseMode);
            
            descrContainer.toggle(!descrRejected);
            disableDescrElt.toggle(!descrRejected);
            enableDescrElt.toggle(descrRejected);
        }
        
        function validatePlaceholders() {
            var k;
            var p;
            var sd;
                        
            cancelSelection();
            curPlaceholder = null;
            updateCurPlaceholderUI();
            clearPlaceholdersValidation();
            
            for (k = 0; k < placeholders.length; k++) {
                p = placeholders[k];
                if (!p.removed && !p.usePlaceholder) {
                    sd = getPlaceholderSDef(p);
                    if (!sd.isValid()) {
                        placeholderPopupElt.find('.alert-attr-not-set').show();
                        
                        curPlaceholder = p;
                        updateCurPlaceholderUI();
                        return false;
                    }
                }
            }
            
            return true;
        }
        
        function clearPlaceholdersValidation() {
            placeholderPopupElt.find('.alert-validation').hide();
        }
        
        function updateOnSaveUI() {
            showOverlay(onSave);
        }
        
        function genericError() {
            pageContainer.prepend(genericErrorHtml);
        }
        function userError(typeCode) {
            var elt;
            elt = $(userErrorHtml);
            elt.find('.err-code').text(typeCode);
            pageContainer.prepend(elt);
        }
        
        function getSerializedPlaceholders() {
            var k;
            var placeholdersData;
            var pData;
            
            placeholdersData = [ ];
            for (k = 0; k < placeholders.length; k++) {
                pData = copyProps(placeholders[k], { });
                if (pData.sd) {
                    pData.sdAttributes = pData.sd.getChosenAttributes();
                    pData.sdFlags = pData.sd.getChosenFlags();
                    pData.srAttributes = pData.sd.getReplacement().getChosenAttributes();
                    pData.srFlags = pData.sd.getReplacement().getChosenFlags();
                    delete pData.sd;
                    delete pData.ctxSamplesSd;
                }
                placeholdersData.push(pData);
            }
            return JSON.stringify(placeholdersData);
        }
        
        function save(finish) {
            var saveData;
            
            if (!onSave) {
                onSave = true;
                updateOnSaveUI();
                
                saveData = {
                    'id': templateId,
                    'description_rejected': descrRejected ? '1' : '0',
                    'blocks_to_remove': removedBlockIds.join(','),
                    'placeholders': getSerializedPlaceholders(),
                    'sleep': 4,
                    'action': 'save'
                };
                if (finish) {
                    saveData.finish = true;
                }
                
                $.ajax({
                    'url': saveUrl,
                    'data': saveData,
                    'type': 'POST',
                    'dataType': 'json'
                })
                .done(function (responseData) {
                    if (responseData.errorCode) {
                        userError(responseData.errorCode);
                    } else {
                        if (responseData.redirectTo) {
                            document.location = responseData.redirectTo;
                        } else {
                            resetArticleTemplate(
                                responseData.removedBlocks,
                                responseData.placeholders);
                        }
                    }
                })
                .fail(function () {
                    genericError();
                })
                .always(function () {
                    onSave = false;
                    updateOnSaveUI();
                });
            }
        }
        
        function deleteArticle(reject) {
            if (!onSave) {
                onSave = true;
                updateOnSaveUI();
                
                if (!reject) {
                    $('input[name=reject]', submitForm).remove();
                }
                submitForm.submit();
            }
        }
                
        function setTooltipsEnabled(enabled) {
            var triggerElts;
            var popoverOpts;
            
            enabled = !!enabled;
            if (curTooltipsEnabled !== enabled) {
                triggerElts = placeholderPopupElt.find('[data-toggle="popover"]');

                if (enabled) {
                    popoverOpts = { 'container': placeholderPopupElt };
                    if (flagHasTouch) {
                        triggerElts = triggerElts.filter('[data-popover-allow-touch]');
                    }
                    triggerElts.popover(popoverOpts);
                } else {
                    triggerElts.popover('destroy');
                }
                
                curTooltipsEnabled = enabled;
            }
        }
        
        
        // Init
        containersElts.each(function () {
            var containerElt = $(this);
            var container = {
                'container': containerElt,
                'html': containerElt.html()
            };
            containers.push(container);
        });
        
        (function () {
            var serializedVal;
            var removedBlocks;
            var placeholdersData;

            origTotalWordCount = getWordElts().length;
            origDescrWordCount = getWordElts(descrContainer).length;

            serializedVal = $('.js-lang-data', mainContainer).text();
            if (serializedVal && serializedVal.length > 0) {
                langData = JSON.parse(serializedVal);
                syntagmeTypes = getPropValues(langData.syntagmeTypes);                
            }
            
            placeholderMaxTextLg = parseInt(mainContainer.data('placeholderMaxTextLg'));
            minWordsConf = parseInt(mainContainer.data('minWords'));
            minPlaceholdersConf = parseInt(mainContainer.data('minPlaceholders'));
            maxWordsByPlaceholdersConf = parseInt(mainContainer.data('maxWordsByPlaceholder'));
            
            sdefReusableAttrCode = mainContainer.data('sdefReusableAttr');
            sdefReusableFlagOn = mainContainer.data('sdefReusableFlagOn');
            sdefReusableFlagOff = mainContainer.data('sdefReusableFlagOff');

            removedBlocks = mainContainer.data('removedBlocks');
            
            serializedVal = mainContainer.data('descrRejected');
            descrRejected = serializedVal === true || serializedVal === 'true';

            placeholdersData = mainContainer.data('placeholders');
            resetArticleTemplate(removedBlocks, placeholdersData);

            setTooltipsEnabled(mainContainer.data('sdefTooltipsDisabled') !== 'on');
        })();
        
        function placeholderClickCbk(e) {
            var ref;
            
            e.preventDefault();
            ref = $(this).data('placeholder-ref');
            editPlaceholder(ref);
        }
        
        containersElts.on('click', wordEltSelector, function (e) {
            e.preventDefault();
            wordClick($(this));
        }).on('click', placeholderEltSelector, placeholderClickCbk);
        placeholderPopupTextContainer.on('click', placeholderEltSelector, placeholderClickCbk);
        
        containersElts.on('mousemove', textBlockSelector, function (e) {
            if (eraseMode) {
                e.stopPropagation();
                textBlockEltsCache.removeClass('hover');
                $(this).addClass('hover');
            }
        }).on('mouseleave', textBlockSelector, function (e) {
            if (eraseMode) {
                e.stopPropagation();
                textBlockEltsCache.removeClass('hover');
            }
        }).on('click', textBlockSelector, function (e) {
            if (eraseMode) {
                e.stopPropagation();
                blockClick($(this));
            }
        });
        
        pageContainer.on('click', selectionPopoverSelector + ' .cancel-btn', function (e) {
            e.preventDefault();
            cancelSelection();
        })
        .on('click', selectionPopoverSelector + ' .st-choice', function (e) {
            var stCode;
            
            e.preventDefault();
            
            stCode = $(this).data('st');
            makePlaceholder(stCode);
        })
        .on('click', selectionPopoverSelector + ' .use-placeholder .dropdown-menu a', function (e) {
            var plRef;
            
            e.preventDefault();
            
            plRef = $(this).data('ref');
            if (typeof plRef === 'string') {
                makePlaceholderFromAnother(plRef);
            }
        })
        .on('click', selectionPopoverSelector + ' .st-help-toggle', function (e) {
            var popoverContainerElt;

            e.preventDefault();

            popoverContainerElt = $(this).parents('.selection-popover');
            popoverContainerElt.find('.st-choice .list-group-item-text').collapse('toggle');
            
            popoverContainerElt.find('[data-toggle="popover"]').popover('destroy');
        });
                
        placeholderPopupElt.on('shown.bs.modal', function (e) {
            placeholderPopupState = 'opened';
            focusPlaceholderPopupArticleContent();
        });
        placeholderPopupElt.on('hide.bs.modal', function (e) {
            placeholderPopupState = 'hiding';
        });
        
        placeholderPopupElt.find(sDefFlagSelector).click(function (e) {
            var elt = $(this);
            
            e.preventDefault();
            sDefFlagClick(elt);
        });
        placeholderPopupReusableDefFlagElt.find('input').click(function (e) {
            sDefReusableFlagChange(this.checked);
        });
        placeholderPopupElt.on('hide.bs.modal', function(e) {
            curPlaceholder = null;
        });
        placeholderPopupElt.find('.remove-btn').click(function (e) {
            e.preventDefault();
            removeCurPlaceholder();
        });
        placeholderPopupElt.find('.attr-help-btn').each(function () {
            var attrHelpBtn;
            var attrContainer;
            var detailedAttrContainer;
            var attrHelpContainer;
            
            attrHelpBtn = $(this);
            attrContainer = attrHelpBtn.parents('.sdef-attr');
            detailedAttrContainer = attrContainer.find('.detailed-attr');
            attrHelpContainer = detailedAttrContainer.find('.attr-help');

            // Remove help button if no help
            if (detailedAttrContainer.length === 1 && 
                    attrHelpContainer.length === 0 &&
                    detailedAttrContainer.find('.flag-help').length === 0) {
                detailedAttrContainer.remove();
                $(this).remove();
            }
        })
        .click(function (e) {            
            var attrContainer;
            var flagBtnsContainer;
            var detailedAttrContainer;
            
            e.preventDefault();

            attrContainer = $(this).parents('.sdef-attr');
            flagBtnsContainer = attrContainer.find('.btn-group');
            detailedAttrContainer = attrContainer.find('.detailed-attr');
            
            flagBtnsContainer.toggle();
            detailedAttrContainer.toggle();
        });
//        placeholderPopupElt.find('.attr-info').each(function () {
//            var helpContainer = $(this);
//            var id;
//            
//            id = helpContainer.attr('id');
//            if (id && helpContainer.find('dd').length === 0 
//                        && helpContainer.find('.attr-help').length === 0) {
//                placeholderPopupElt.find('a[href="#' + id + '"]').remove();
//                helpContainer.remove();
//            }
//        });
//        placeholderPopupElt.find('[data-toggle="popover"]').popover({ 'container': placeholderPopupElt });
//        $('[data-toggle="tooltip"]', mainContainer).tooltip();
        
        
        eraseModeMenuElt.find('a').click(function (e) {
            e.preventDefault();
            switchToEraseMode(!eraseModeMenuElt.hasClass('active'));
        });
        saveMenuElt.find('a').click(function (e) {
            e.preventDefault();
            save();
        });
        finishMenuElt.find('a').click(function (e) {
            e.preventDefault();
            // todo : validate
            if (validatePlaceholders()) {
                save(true);
            }
        });
        deleteMenuElt.find('a').click(function (e) {
            e.preventDefault();
            deleteArticle();
        });
        rejectMenuElt.find('a').click(function (e) {
            e.preventDefault();
            deleteArticle(true);
        });
        
        disableDescrElt.find('a').click(function (e) {
            e.preventDefault();
            descrRejected = true;
            
            if (removePlaceholdersInside(descrContainer)) {
                updateDOMContainersPlaceholders();
            }
            updateDOMRemovedBlocks();
            updateGlobalUI();
        });
        enableDescrElt.find('a').click(function (e) {
            e.preventDefault();
            descrRejected = false;

            updateDOMRemovedBlocks();
            updateGlobalUI();
        });
        
        tutoMenuElt.find('a').click(function (e) {
            e.preventDefault();
            tutoContainer.show();
        });
        tutoContainer.find('a.tuto-close').click(function (e) {
            e.preventDefault();
            tutoContainer.hide();
        });
        
        // Expose some elements for testing
        window.getWordRangeText = getWordRangeText;
        window.getLangData = function () { return langData; };
        window.getPlaceholders = function () { return placeholders; };
        window.getRemovedBlocks = function () { return removedBlockIds; };
        window.validatePlaceholders = function () { validatePlaceholders(); };
    }
    
    function initTemplateEdits() {
        $('.template-edit').each(function () {
            initTemplateEdit($(this));
        });
        
    }
    
    
    
    
    // REPLACEMENT FILL
    
    function initReplacementFillForm(container) {
        
        var form = container;
        var textInput = $('input[name=text]', form);
        var userTextInput = $('input[name=user_text]', form);
        var flagsInput = $('input[name=flags]', form);
        var onSave = false;
        
        var langData = null;
//        var fillTextHelpData = null;
//        var fillTextPopoverTplWithEx = 
//                $('.tpl-fill-text-popover-content-with-ex', container).remove().html();
//        var fillTextPopoverExItemTpl = 
//                $('.tpl-fill-text-popover-content-ex', container).remove().html();
        var sType = null;
        var sDef = null;
        var textMaxLg = container.data('textMaxLg');
        var valid = false;
        
        var sDefContainer = $('.placeholder-sdef', container);
        var sDefFlagSelector = '.sdef-flag';
        var ctxSamplesDefaultText = "(...)";
        
        
        function setupFillTextMsg() {
            var fillTextElt;
//            var baseStKey;
            var k;
            var attributeCodes;
            var attr;
            var flags;
            var f;
            var flagCode;
            
            fillTextElt = container.find('.fill-text-msg');
//            baseStKey = 'st.' + sDef.getSyntagmeType().code;
            var elt;
            elt = fillTextElt.find('.st');
            elt.addClass('colored-st');
            setupFillTextPopover(elt, 'st');
            
            var detailsContainer;
            detailsContainer = container.find('.fill-text-details');
            
            attributeCodes = sDef.getAttributeCodes();
            for (k = 0; k < attributeCodes.length; k++) {
                attr = sDef.getAttribute(attributeCodes[k]);
                
                setupFillTextPopover(
                    detailsContainer.find('.attr-' + attr.code),
                    'attr-' + attr.code);

                flags = sDef.getAttributeFlags(attr);
                for (f = 0; f < flags.length; f++) {
                    flagCode = flags[f].code;
                    
                    setupFillTextPopover(
                        detailsContainer.find('.flag-' + flagCode),
                        'flag-' + flagCode);
                }
            }            
        }
        
        function setupFillTextPopover(elt, type) {
            var contentContainer;

            contentContainer = container.find('.fill-text-' + type + '-help-popover');
            if (contentContainer.length === 1) {
                elt.popover({
                    'trigger': 'hover',
                    'placement': 'top',
                    'html': true,
                    'content': contentContainer.html()
                })
                .addClass('with-help');

                contentContainer.remove();
            }
//            help = fillTextHelpData[baseKey + '.help'];
//            ex = fillTextHelpData[baseKey + '.ex'];
//            
//            if (help || ex) {
//                popoverContent = help;
//                if (ex && ex.length > 0) {
//                    popoverContent = fillTextPopoverTplWithEx
//                            .replace(/\{text\}/g, help)
//                            .replace(/\{ex\}/g, ex[0]);
//                }
//
//                elt.popover({
//                    'trigger': 'hover',
//                    'placement': 'top',
//                    'html': true,
//                    'content': popoverContent
//                })
//                .addClass('with-help');
//            }
        }
        
        function updateUI() {
            var tooLong;

            if (sDef) {
                valid = sDef.isValid();
                tooLong = sDef.getText().length > textMaxLg;
                valid = valid && !tooLong;

                $('.form-group.user-text', form).toggleClass('has-error', tooLong);
                $('.form-group.user-text .too-long-text-msg', form).toggle(tooLong);
                updateCtxInfoUI();
                updateSDefUI();
                
            }
            $('.submit-btn', container).toggleClass('disabled', !valid);
        }
        
        function updateCtxInfoUI() {
            var varValue;
            var varContainer;
            var samplesContainer;
            var samples;
            var listCont;
            var k;
            var samplesText;
            
            if (!sDef) {
                return;
            }
            
            varValue = sDef.getCtxPrefix();
            varContainer = container.find('.ctx-prefix');
            varContainer.text(varValue ? varValue : '');
//            if (varValue && varValue.length > 0) {
//                varContainer.text(varValue);
//                varContainer.show();
//            } else {
//                varContainer.hide();
//            }
            varValue = sDef.getCtxSuffix();
            varContainer = container.find('.ctx-suffix');
            varContainer.text(varValue ? varValue : '');
//            if (varValue && varValue.length > 0) {
//                varContainer.text(varValue);
//                varContainer.show();
//            } else {
//                varContainer.hide();
//            }
            
            samplesText = sDef.getText();
            if (samplesText.length === 0) {
                samplesText = ctxSamplesDefaultText;
            }
            samples = sDef.getFinalCtxSamples(samplesText);
            samplesContainer = container.find('.ctx-samples');
            if (samples.length > 0) {
                listCont = samplesContainer.find('ul').empty();
                for (k = 0; k < samples.length; k++) {
                    $('<li></li>').text(samples[k]).appendTo(listCont);
                }
                samplesContainer.show();
            } else {
                samplesContainer.hide();
            }
        }
        
        
        function updateSDefUI() {
            var hasAttrVisible;
            var attrCodes;
            var k;
            var attr;
            var f;
            var flagCode;
            var hasFlagEnabled;
            var attrContainer;
            var flagElt;
            var flags;

            hasAttrVisible = false;
            attrCodes = sDef.getAttributeCodes();
            for (k = 0; k < attrCodes.length; k++) {
                attr = sDef.getAttribute(attrCodes[k]);
                hasFlagEnabled = false;
                attrContainer = sDefContainer.find('.sdef-attr.attr-' + attr.code);
                if (attrContainer.length > 0) {
                    flags = sDef.getAttributeFlags(attr);
                    for (f = 0; f < flags.length; f++) {
                        flagCode = flags[f].code;
                        flagElt = attrContainer.find(sDefFlagSelector + '[data-flag=' + flagCode + ']');
                        switch (sDef.getFlagState(flagCode)) {
                            case FLAG_STATE_DISABLED:
                                flagElt.removeClass('active').addClass('disabled');
                                break;
                            case FLAG_STATE_OFF:
                                flagElt.removeClass('active').removeClass('disabled');
                                hasFlagEnabled = true;
                                break;
                            case FLAG_STATE_ON:
                                flagElt.removeClass('disabled').addClass('active');
                                hasFlagEnabled = true;
                                break;
                        }
                    }

                    if (hasFlagEnabled) {
                        hasAttrVisible = true;
                    }
                    attrContainer.toggle(hasFlagEnabled);
                }
            }
            sDefContainer.toggle(hasAttrVisible);
        }
        
        function updateFinalFlags() {
            var k;
            var attr;
            var f;
            var chosenFlags;
            var flags;
            
            // Filter actually chosen flags
            flags = [ ];
            chosenFlags = sDef.getChosenFlags();
            for (k = 0; k < chosenFlags.length; k++) {
                f = chosenFlags[k];
                attr = sDef.getAttributeByFlag(f);
                if (attr && attr.definitionOnly && !attr.virtual) {
                    flags.push(f);
                }
            }
            flagsInput.val(flags.join(','));
        }
        
        function updateText(text) {
            if (sDef) {
                sDef.setText(text);
                textInput.val(sDef.getText());
                updateUI();
                updateFinalFlags();
            }
        }

        

        (function () {
            var json;
            var stCode;
            var attrData;
            var origCtxFlags;
            var origReplFlags;
            var origSDef;
            
            json = $('.js-lang-data', container).text();
            if (json && json.length > 0) {
                langData = JSON.parse(json);
            }
            json = $('.js-fill-text-help-data', container).text();
            if (json && json.length > 0) {
                fillTextHelpData = JSON.parse(json);
            }
            if (langData) {
                stCode = container.data('stCode');
                if (stCode) {
                    sType = langData.syntagmeTypes[stCode];
                }
            }
            
            origCtxFlags = null;
            origReplFlags = null;
            
            attrData = container.data('origCtxFlags');
            if (typeof attrData === 'string' && attrData.length > 0) {
                origCtxFlags = attrData.split(',');
            }
            attrData = container.data('origReplFlags');
            if (typeof attrData === 'string' && attrData.length > 0) {
                origReplFlags = attrData.split(',');
            }
            
            if (sType) {
                origSDef = new SyntagmeDefinition(sType);
                if (origCtxFlags) {
                    origSDef.resetChosenFlags(null, origCtxFlags);
                }
                if (origReplFlags) {
                    origSDef.getReplacement().resetChosenFlags(null, origReplFlags);
                }
                origSDef.update();
                
                sDef = new SyntagmeDefinition(sType);
                sDef.setOrigDefinition(origSDef);
                if (origCtxFlags) {
                    sDef.resetChosenFlags(null, origCtxFlags);
                }
                if (origReplFlags) {
                    sDef.getReplacement().resetChosenFlags(null, origReplFlags);
                }

                sDef.select(FLAG_COMMON_PHASE_REPL, true);

                updateFinalFlags();
                setupFillTextMsg();
                updateUI();
                
                // Expose for testing
                window.sDef = sDef;
//                window.fillTextHelpData = fillTextHelpData;
            }
            
//            if (container.data('sdefTooltipsDisabled') !== 'on') {
//                $('[data-toggle="popover"]', sDefContainer).popover({
//                    'container': sDefContainer
//                });
//            }

        })();
        
        userTextInput.on('change keyup', function (e) {
            updateText($(this).val());
        });
        
        sDefContainer.find(sDefFlagSelector).click(function (e) {
            var flagElt;
            var flagCode;
            var activeInUI;
            
            e.preventDefault();
            
            flagElt = $(this);
            flagCode = flagElt.data('flag');
            if (sDef && flagCode) {
                activeInUI = flagElt.hasClass('active');

                sDef.select(flagCode, !activeInUI);
                updateUI();
                updateFinalFlags();
            }
        });
        
        form.submit(function (e) {
            if (valid && !onSave) {
                onSave = true;
                container.addClass('on-save');
            } else {
                e.preventDefault();
            }
        });
        
        $(function () {
            userTextInput.focus();
        });
    }
    
    function initReplacementFillForms() {
        $('.fill-replacement-form').each(function () {
            initReplacementFillForm($(this));
        });
    }
    
    function initFinalArticle() {
        $('.final-article .upvote-btn').click(function (e) {
            e.preventDefault();
            
            $(this).parents('.final-article').find('.upvote-form').submit();
        });
    }
    
    updateHasTouch();
    initWebsiteNav();
    registerLinksNoFollow();
    initTemplateEdits();
    initReplacementFillForms();
    initFinalArticle();
    
})(jQuery);