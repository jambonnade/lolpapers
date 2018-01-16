package de.jambonna.lolpapers.core.model.lang.fr;

import de.jambonna.lolpapers.core.model.lang.LanguageAbstract;
import de.jambonna.lolpapers.core.model.lang.SyntagmeAttribute;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinitionUpdate;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinitionUpdate.CtxVarValueType;
import de.jambonna.lolpapers.core.model.lang.SyntagmeFlag;
import de.jambonna.lolpapers.core.model.lang.SyntagmeType;
import de.jambonna.lolpapers.core.model.text.ReplaceContext;
import de.jambonna.lolpapers.core.model.text.Text;
import de.jambonna.lolpapers.core.model.text.TextRange;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class FrLanguage extends LanguageAbstract {

    public static final String CODE = "fr";
    
    public static final String TYPE_NOM = "n";
    
    public static final String ATTR_NOM_CTX_NOMBRE = "cnombre";
    public static final String FLAG_NOM_CTX_NOMBRE_UNKNOWN = "cn0";
    public static final String FLAG_NOM_CTX_NOMBRE_SINGULIER = "cns";
    public static final String FLAG_NOM_CTX_NOMBRE_PLURIEL = "cnp";

    public static final String ATTR_NOM_CTX_GENRE = "cgenre";
    public static final String FLAG_NOM_CTX_GENRE_UNKNOWN = "cg0";
    public static final String FLAG_NOM_CTX_GENRE_FEMININ = "cgf";
    public static final String FLAG_NOM_CTX_GENRE_MASCULIN = "cgm";
    public static final String FLAG_NOM_CTX_GENRE_EPICENE = "cgx";
    
    public static final String ATTR_NOM_CTX_DETERMINANT = "cdet";
    public static final String FLAG_NOM_CTX_DETERMINANT_UNDEF = "cdi";
    public static final String FLAG_NOM_CTX_DETERMINANT_PART = "cdpa";
    public static final String FLAG_NOM_CTX_DETERMINANT_ARTDEF = "cdd";
    public static final String FLAG_NOM_CTX_DETERMINANT_POSS = "cdpo";

    public static final String ATTR_NOM_CTX_COMPL = "ccompl";
    public static final String FLAG_NOM_CTX_COMPL_NONE = "cc0";
    public static final String FLAG_NOM_CTX_COMPL_COMPL = "cc1";
    
//    public static final String ATTR_NOM_NOMBRE = "nombre";
//    public static final String FLAG_NOM_NOMBRE_SINGULIER = "ns";
//    public static final String FLAG_NOM_NOMBRE_PLURIEL = "np";
//
//    public static final String ATTR_NOM_GENRE = "genre";
//    public static final String FLAG_NOM_GENRE_FEMININ = "gf";
//    public static final String FLAG_NOM_GENRE_MASCULIN = "gm";
//    public static final String FLAG_NOM_GENRE_EPICENE = "gx";

    public static final String ATTR_NOM_TYPE = "type";
    public static final String FLAG_NOM_TYPE_ANIME = "ta";
    public static final String FLAG_NOM_TYPE_INANIME = "ti";
    public static final String FLAG_NOM_TYPE_ABSTRAIT = "tabs";
    
    public static final String ATTR_NOM_COMPL = "compl";
    public static final String FLAG_NOM_COMPL_NONE = "c0";
    public static final String FLAG_NOM_COMPL_COMPL = "c1";
    
    
    public static final String TYPE_SNOMINAL = "sn";
    
    public static final String ATTR_SNOMINAL_DETERMINANT = "det";
    public static final String FLAG_SNOMINAL_DETERMINANT_UNDEF = "di";
    public static final String FLAG_SNOMINAL_DETERMINANT_DEF = "dd";

    
    public static final String TYPE_VERBE = "v";
    
//    public static final String AGRP_VERBE_CTX_SUJET = "csujet";
    public static final String ATTR_VERBE_CTX_SUJET_PERS = "csujetp";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_1S = "cs1s";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_2S = "cs2s";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_3S = "cs3s";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_3SM = "cs3sm";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_1P = "cs1p";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_2P = "cs2p";
    public static final String FLAG_VERBE_CTX_SUJET_PERS_3P = "cs3p";

    public static final String ATTR_VERBE_CTX_SUJET_G = "csujetg";
    public static final String FLAG_VERBE_CTX_SUJET_G_0 = "csg0";
    public static final String FLAG_VERBE_CTX_SUJET_G_F = "csgf";
    public static final String FLAG_VERBE_CTX_SUJET_G_M = "csgm";
    public static final String ATTR_VERBE_CTX_SUJET_TYPE = "csujett";
    public static final String FLAG_VERBE_CTX_SUJET_TYPE_A = "csta";
    public static final String FLAG_VERBE_CTX_SUJET_TYPE_I = "csti";
    public static final String FLAG_VERBE_CTX_SUJET_TYPE_ABS = "cstabs";

    public static final String ATTR_VERBE_CTX_PREFLECHI = "cpr";
    public static final String FLAG_VERBE_CTX_PREFLECHI_NO = "cpr0";
    public static final String FLAG_VERBE_CTX_PREFLECHI_YES = "cpr1";

    public static final String ATTR_VERBE_CTX_COD = "ccod";
    public static final String FLAG_VERBE_CTX_COD_NONE = "ccod0";
    public static final String FLAG_VERBE_CTX_COD_AFTER = "ccoda";
    public static final String FLAG_VERBE_CTX_COD_BEFORE = "ccodb";
    
//    public static final String AGRP_VERBE_CTX_CODINFO = "ccodi";
    public static final String ATTR_VERBE_CTX_CODINFO_PERS = "ccodip";
    public static final String FLAG_VERBE_CTX_CODINFO_PERS_1S = "ccod1s";
    public static final String FLAG_VERBE_CTX_CODINFO_PERS_2S = "ccod2s";
    public static final String FLAG_VERBE_CTX_CODINFO_PERS_3S = "ccod3s";
    public static final String FLAG_VERBE_CTX_CODINFO_PERS_1P = "ccod1p";
    public static final String FLAG_VERBE_CTX_CODINFO_PERS_2P = "ccod2p";
    public static final String FLAG_VERBE_CTX_CODINFO_PERS_3P = "ccod3p";
    
    public static final String ATTR_VERBE_CTX_CODINFO_GENRE = "ccodig";
    public static final String FLAG_VERBE_CTX_CODINFO_GENRE_0 = "ccodg0";
    public static final String FLAG_VERBE_CTX_CODINFO_GENRE_F = "ccodgf";
    public static final String FLAG_VERBE_CTX_CODINFO_GENRE_M = "ccodgm";
    
    public static final String ATTR_VERBE_CTX_CODINFO_TYPE = "ccodit";
    public static final String FLAG_VERBE_CTX_CODINFO_TYPE_ANIME = "ccodta";
    public static final String FLAG_VERBE_CTX_CODINFO_TYPE_INANIME = "ccodti";
    public static final String FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT = "ccodtabs";
    
//    public static final String AGRP_VERBE_CTX_PPASSE = "cpp";
    public static final String ATTR_VERBE_CTX_PPASSE_AUX = "cppa";
    public static final String FLAG_VERBE_CTX_PPASSE_AUX_NONE = "cppa0";
    public static final String FLAG_VERBE_CTX_PPASSE_AUX_AVOIR = "cppaa";
    public static final String FLAG_VERBE_CTX_PPASSE_AUX_ETRE = "cppae";
    public static final String ATTR_VERBE_CTX_PPASSE_AUXE = "cppae";
    public static final String FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE = "cppaea";
    public static final String FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE = "cppaep";
    public static final String FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVECDA = "cppaepc";

    public static final String ATTR_VERBE_FORME = "forme";
    public static final String FLAG_VERBE_FORME_INFINITIF = "finfi";
    public static final String FLAG_VERBE_FORME_PRESENT = "fpres";
    public static final String FLAG_VERBE_FORME_PASSECOMPOSE = "fpco";
    public static final String FLAG_VERBE_FORME_PARTICIPEPASSE = "fppas";
    public static final String FLAG_VERBE_FORME_IMPARFAIT = "fimpa";
    public static final String FLAG_VERBE_FORME_PQPARFAIT = "fpqp";
    public static final String FLAG_VERBE_FORME_FUTURANT = "ffuta";
    public static final String FLAG_VERBE_FORME_PASSESIMPLE = "fpass";
    public static final String FLAG_VERBE_FORME_FUTUR = "ffut";
    public static final String FLAG_VERBE_FORME_PARTICIPEPRESENT = "fppre";
    public static final String FLAG_VERBE_FORME_IMPERATIF = "fimpe";
    
    public static final String ATTR_VERBE_NEGATION = "neg";
    public static final String FLAG_VERBE_NEGATION_WITHOUT = "neg0";
    public static final String FLAG_VERBE_NEGATION_WITH = "neg1";
    
    public static final String ATTR_VERBE_CC = "cc";
    public static final String FLAG_VERBE_CC_WITHOUT = "cc0";
    public static final String FLAG_VERBE_CC_WITH = "cc1";

//    public static final String TYPE_SVERBAL = "sv";
    
    
    public static final String TYPE_COMPCIRC = "cc";
    
    public static final String ATTR_COMPCIRC_TYPE = "type";
    public static final String FLAG_COMPCIRC_TYPE_LIEU = "tlieu";
    public static final String FLAG_COMPCIRC_TYPE_TEMPS = "ttps";
    public static final String FLAG_COMPCIRC_TYPE_OTHER = "tother";
    
    
    
    
    public static final String ATTR_COMMON_CTX_REF = "cref";
    public static final String FLAG_COMMON_CTX_REF_NONE = "cr0";
    public static final String FLAG_COMMON_CTX_REF_1PS = "cr1s";
    public static final String FLAG_COMMON_CTX_REF_2PS = "cr2s";
    public static final String FLAG_COMMON_CTX_REF_3P = "cr3";
//    public static final String FLAG_COMMON_CTX_REF_3PS = "cr3s";
    public static final String FLAG_COMMON_CTX_REF_1PP = "cr1p";
    public static final String FLAG_COMMON_CTX_REF_2PP = "cr2p";
//    public static final String FLAG_COMMON_CTX_REF_3PP = "cr3p";

//    public static final String AGRP_COMMON_CTX_REF3P = "cref3p";
    public static final String ATTR_COMMON_CTX_REF3P_NB = "cref3pn";
    public static final String FLAG_COMMON_CTX_REF3P_NB_S = "cr3pns";
    public static final String FLAG_COMMON_CTX_REF3P_NB_P = "cr3pnp";
    public static final String ATTR_COMMON_CTX_REF3P_G = "cref3pg";
    public static final String FLAG_COMMON_CTX_REF3P_G_0 = "cr3pg0";
    public static final String FLAG_COMMON_CTX_REF3P_G_F = "cr3pgf";
    public static final String FLAG_COMMON_CTX_REF3P_G_M = "cr3pgm";
    public static final String ATTR_COMMON_CTX_REF3P_T = "cref3pt";
    public static final String FLAG_COMMON_CTX_REF3P_T_A = "cr3pta";
    public static final String FLAG_COMMON_CTX_REF3P_T_I = "cr3pti";
    
    public static final String ATTR_COMMON_KEEP_MAJ = "kmj";
    public static final String FLAG_COMMON_KEEP_MAJ_ALL = "kmj";
    public static final String FLAG_COMMON_KEEP_MAJ_INSIDE = "kimj";
    public static final String FLAG_COMMON_KEEP_MAJ_NONE = "nomj";
    public static final String ATTR_COMMON_HASPIRE = "ha";
    public static final String FLAG_COMMON_HASPIRE = "ha";
    public static final String FLAG_COMMON_HMUET = "hm";
    public static final String ATTR_COMMON_ELISION = "eli";
    public static final String FLAG_COMMON_ELISION_OFF = "eli0";
    public static final String FLAG_COMMON_ELISION_ON = "eli1";


    public FrLanguage(Locale locale) {
        super(locale);
    }
    public FrLanguage() {
        this(new Locale("fr_FR"));
    }
    
    @Override
    public String getCode() {
        return CODE;
    }
    
    @Override
    protected List<SyntagmeType> createSyntagmeTypes() {
        return Arrays.asList(
            createNom(),
            createSyntagmeNominal(),
            createVerbe(),
            createComplementCirconstanciel()
        );
    }
    
    public SyntagmeType createNom() {
        SyntagmeType.Builder builder;
        
        builder = new SyntagmeType.Builder(this, TYPE_NOM);
        builder.setAllowByPattern(getWordRE());
        builder.setReferencesAllowed(true);
        
        // Defaults
        builder
            .update()
                .setAttributeFlagsTo(FLAG_COMMON_CTX_REF_NONE)
                .end()
            ;

        addCommonNomCtxAttributes1(builder);
        
        builder
            .attribute(ATTR_NOM_CTX_DETERMINANT).setContext(true).setMulti(true).setRequired(true)
                .flag(FLAG_NOM_CTX_DETERMINANT_UNDEF).end()
                .flag(FLAG_NOM_CTX_DETERMINANT_PART).end()
                .flag(FLAG_NOM_CTX_DETERMINANT_ARTDEF).end()
                .flag(FLAG_NOM_CTX_DETERMINANT_POSS).end()
                .end();
        
        addCommonCtxRefAttributes(builder);
        builder.attribute(ATTR_COMMON_CTX_REF).setRequired(true);
        
        addCommonNomCtxAttributes2(builder);

        addCommonNomAttributes1(builder);
        addCommonNomAttributes2(builder);
                        
        addCommonDefAttributes(builder);
        
        
        builder
            .update()
                .condition()
                    .flagsOn(FLAG_NOM_CTX_DETERMINANT_POSS)
                    .end()
                .disableFlags(FLAG_COMMON_CTX_REF_NONE)
                .end()
            .update()
                .condition().flagsOn(FLAG_NOM_CTX_NOMBRE_PLURIEL).end()
                .setAttributeFlagsTo(FLAG_NOM_CTX_GENRE_UNKNOWN)
                .end()
            // Complements possibles dans la définition à partir du texte
            .update()
                .setAndLimitAttributeFlagsTo(FLAG_NOM_COMPL_NONE)
                .end()
            .update()
                .condition().textMatch(getMultipleWordsRE()).end()
                .disableAttributeFlags(ATTR_NOM_COMPL)
                .enableAttributeFlags(ATTR_NOM_COMPL)
                .end()
            ;
        
        addCommonCtxRefAttributesUpdates(builder);
        
        addCommonNomAttributesUpdates(builder);

        
        // Prefixes de mise en situation
        String[][][] prefixes = {
//            { { "un " },    { FLAG_NOM_CTX_DETERMINANT_UNDEF }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_MASCULIN } },
//            { { "une " },   { FLAG_NOM_CTX_DETERMINANT_UNDEF }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_FEMININ } },
//            { { "un·e " },  { FLAG_NOM_CTX_DETERMINANT_UNDEF }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_EPICENE } },
//            { { "des " },   { FLAG_NOM_CTX_DETERMINANT_UNDEF }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            
//            { { "du " },    { FLAG_NOM_CTX_DETERMINANT_PART }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_MASCULIN }, { FLAG_COMMON_ELISION_OFF } },
//            { { "de la " }, { FLAG_NOM_CTX_DETERMINANT_PART }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_FEMININ }, { FLAG_COMMON_ELISION_OFF } },
//            { { "du·de la " }, { FLAG_NOM_CTX_DETERMINANT_PART }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_EPICENE }, { FLAG_COMMON_ELISION_OFF } },
//            { { "de l'" },  { FLAG_NOM_CTX_DETERMINANT_PART }, { FLAG_NOM_NOMBRE_SINGULIER }, { FLAG_COMMON_ELISION_ON } },
//            { { "des " },   { FLAG_NOM_CTX_DETERMINANT_PART }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            
//            { { "le " },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_MASCULIN }, { FLAG_COMMON_ELISION_OFF } },
//            { { "la " },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_FEMININ }, { FLAG_COMMON_ELISION_OFF } },
//            { { "le·la " }, { FLAG_NOM_CTX_DETERMINANT_ARTDEF }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_EPICENE }, { FLAG_COMMON_ELISION_OFF } },
//            { { "l'" },     { FLAG_NOM_CTX_DETERMINANT_ARTDEF }, { FLAG_NOM_NOMBRE_SINGULIER }, { FLAG_COMMON_ELISION_ON } },
//            { { "les " },   { FLAG_NOM_CTX_DETERMINANT_ARTDEF }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            
//            { { "mon " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_MASCULIN }, { FLAG_COMMON_ELISION_OFF } },
//            { { "ma " },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_FEMININ }, { FLAG_COMMON_ELISION_OFF } },
//            { { "mon " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_NOMBRE_SINGULIER, }, { FLAG_COMMON_ELISION_ON } },
//            { { "mes " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            { { "ton " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_MASCULIN }, { FLAG_COMMON_ELISION_OFF } },
//            { { "ta " },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_FEMININ }, { FLAG_COMMON_ELISION_OFF } },
//            { { "ton " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_NOMBRE_SINGULIER }, { FLAG_COMMON_ELISION_ON } },
//            { { "tes " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            { { "son " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_MASCULIN }, { FLAG_COMMON_ELISION_OFF } },
//            { { "sa " },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_NOMBRE_SINGULIER, FLAG_NOM_GENRE_FEMININ }, { FLAG_COMMON_ELISION_OFF } },
//            { { "son " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_NOMBRE_SINGULIER }, { FLAG_COMMON_ELISION_ON } },
//            { { "ses " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            { { "notre " }, { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP }, { FLAG_NOM_NOMBRE_SINGULIER } },
//            { { "nos " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            { { "votre " }, { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP }, { FLAG_NOM_NOMBRE_SINGULIER } },
//            { { "vos " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP }, { FLAG_NOM_NOMBRE_PLURIEL } },
//            { { "leur " },  { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_NOMBRE_SINGULIER } },
//            { { "leurs " }, { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_NOMBRE_PLURIEL } },
            // Note : mettra qd meme les determinants si genre inconnu pour les 
            // mises en situation meme si le contexte d'origine n'avait pas de 
            // determinant indiquant le genre.
            { { "un " },    { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "un " },    { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN } },
            { { "une " },   { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "une " },   { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            // Met ce determinant dans les possibilités si genre inconnu mais 
            // uniquement si le nom a possibilité d'être animé
            { { "un·e " },  { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { FLAG_NOM_TYPE_ANIME } },
            { { "un·e " },  { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE } },
            { { "des " },   { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            
            { { "du " },    { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "du " },    { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "de la " }, { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "de la " }, { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "du·de la " }, { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { FLAG_NOM_TYPE_ANIME }, { FLAG_COMMON_ELISION_OFF } },
            { { "du·de la " }, { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "de l'" },  { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "des " },   { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            
            { { "le " },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "le " },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "la " },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "la " },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "la·le " }, { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN }, { FLAG_NOM_TYPE_ANIME }, { FLAG_COMMON_ELISION_OFF } },
            { { "la·le " }, { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "l'" },     { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "les " },   { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            
            { { "mon " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "ma " },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "mon " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "mes " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "ton " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "ta " },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "ton " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "tes " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "son " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "sa " },    {  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ }, { }, { FLAG_COMMON_ELISION_OFF } },
            { { "son " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "ses " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "notre " }, { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "nos " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "votre " }, { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "vos " },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "leur " },  { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "leurs " }, { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
        };
        
        addCtxVarUpdates(builder, prefixes, SyntagmeDefinition.CTX_VAR_PREFIX, CtxVarValueType.MULTI);
        
        addCommonNomCtxSampleVars(builder);
        
        String[][][] specificSampleVars = {
            { { "SN", "{PREFIX}{TEXT}" } },
        };
        
        addCtxVarUpdates(builder, specificSampleVars, null, CtxVarValueType.DEFAULT);
        
        addCommonNomCtxSamples(builder);
        
        // Flags réellement positionnés par les mots précédents à la création
        String[][][] ctxSelectingFlagsFromPrevWords = {
            { { "un" },     { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN } },
            { { "une" },    { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "un·e" },   { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE } },
            // Note : des peut etre de + les
            { { "des" },    { FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            
            { { "l" },      { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "le" },     { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN } },
            { { "la" },     { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "la·le" },  { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE } },
            { { "les" },    { FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            
            // Note : écrase article défini avec partitif mais on est pas sûr que ce soit partitif
            // c'est pour au moins enlever article défini
            { { "de", "l" },    { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "du" },         { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN } },
            { { "de", "la" },   { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "du·de", "la" }, { FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE } },
            
            { { "mon" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "ma" },     { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "mes" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "ton" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "ta" },     { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "tes" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "son" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "sa" },     { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "ses" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "leur" },   { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "leurs" },  { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "notre" },  { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "nos" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "votre" },  { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN } },
            { { "vos" },    { FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN } },

        };
        
        for (String[][] selectingFlagsItem : ctxSelectingFlagsFromPrevWords) {
            builder
                .update()
                    .condition().prevWords(selectingFlagsItem[0]).end()
                    .setFlagsToSelect(selectingFlagsItem[1])
                ;
        }
        
        return new SyntagmeType(builder);
    }
        
    protected void addCommonNomCtxAttributes1(SyntagmeType.Builder builder) {
        builder
            .attribute(ATTR_NOM_CTX_NOMBRE).setContext(true).setRequired(true)
                .flag(FLAG_NOM_CTX_NOMBRE_SINGULIER).setQuickCode("S").end()
                .flag(FLAG_NOM_CTX_NOMBRE_PLURIEL).setQuickCode("P").end()
                .end()
            .attribute(ATTR_NOM_CTX_GENRE).setContext(true).setRequired(true)
                .flag(FLAG_NOM_CTX_GENRE_UNKNOWN).end()
                .flag(FLAG_NOM_CTX_GENRE_FEMININ).setQuickCode("F").end()
                .flag(FLAG_NOM_CTX_GENRE_MASCULIN).setQuickCode("M").end()
                .flag(FLAG_NOM_CTX_GENRE_EPICENE).setQuickCode("X").end()
                .end()
//            .update()
//                .setAttributeFlagsTo(FLAG_NOM_CTX_GENRE_UNKNOWN)
//                .end()
            ;
    }
    
    protected void addCommonNomCtxAttributes2(SyntagmeType.Builder builder) {
        builder
            .attribute(ATTR_NOM_CTX_COMPL).setContext(true)
                .flag(FLAG_NOM_CTX_COMPL_NONE).end()
                .flag(FLAG_NOM_CTX_COMPL_COMPL).end()
                .end()
            ;
    }
    
    protected void addCommonNomAttributes1(SyntagmeType.Builder builder) {
        builder
//            .attribute(ATTR_NOM_NOMBRE)
//                .flag(FLAG_NOM_NOMBRE_SINGULIER).end()
//                .flag(FLAG_NOM_NOMBRE_PLURIEL).end()
//                .end()
//            .attribute(ATTR_NOM_GENRE)
//                .flag(FLAG_NOM_GENRE_FEMININ).end()
//                .flag(FLAG_NOM_GENRE_MASCULIN).end()
//                .flag(FLAG_NOM_GENRE_EPICENE).end()
//                .end()
            .attribute(ATTR_NOM_TYPE)
                .flag(FLAG_NOM_TYPE_ANIME).setQuickCode("TA").end()
                .flag(FLAG_NOM_TYPE_INANIME).setQuickCode("TI").end()
                .flag(FLAG_NOM_TYPE_ABSTRAIT).setQuickCode("TABS").end()
                .end()
            ;
    }
    
    protected void addCommonNomAttributes2(SyntagmeType.Builder builder) {
        builder
            .attribute(ATTR_NOM_COMPL)
                .flag(FLAG_NOM_COMPL_NONE).setQuickCode("C0").end()
                .flag(FLAG_NOM_COMPL_COMPL).setQuickCode("C1").end()
                .end()
            .update()
                .setReplAttributeFlagsTo(FLAG_NOM_COMPL_NONE, FLAG_NOM_COMPL_COMPL)
            ;
    }
    
    protected void addCommonNomAttributesUpdates(SyntagmeType.Builder builder) {
        builder
            // Positionne le contexte à partir du genre/nombre de la def
//            .update()
//                .condition().flagsOn(FLAG_NOM_NOMBRE_SINGULIER).end()
//                .setAttributeFlagsTo(FLAG_NOM_CTX_NOMBRE_SINGULIER)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_NOMBRE_PLURIEL).end()
//                .setAttributeFlagsTo(FLAG_NOM_CTX_NOMBRE_PLURIEL)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_GENRE_FEMININ).end()
//                .setAttributeFlagsTo(FLAG_NOM_CTX_GENRE_FEMININ)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_GENRE_MASCULIN).end()
//                .setAttributeFlagsTo(FLAG_NOM_CTX_GENRE_MASCULIN)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_GENRE_EPICENE).end()
//                .setAttributeFlagsTo(FLAG_NOM_CTX_GENRE_EPICENE)
//                .end()

            // Fixe genre/nombre de la definition/remplacement à partir du contexte
//            .update()
//                .condition().flagsOn(FLAG_NOM_CTX_NOMBRE_SINGULIER).end()
//                .setAndLimitAttributeFlagsTo(FLAG_NOM_NOMBRE_SINGULIER)
//                .setAndLimitReplAttributeFlagsTo(FLAG_NOM_NOMBRE_SINGULIER)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_CTX_NOMBRE_PLURIEL).end()
//                .setAndLimitAttributeFlagsTo(FLAG_NOM_NOMBRE_PLURIEL)
//                .setAndLimitReplAttributeFlagsTo(FLAG_NOM_NOMBRE_PLURIEL)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_CTX_GENRE_FEMININ).end()
//                .setAndLimitAttributeFlagsTo(FLAG_NOM_GENRE_FEMININ)
//                .setAndLimitReplAttributeFlagsTo(FLAG_NOM_GENRE_FEMININ)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_CTX_GENRE_MASCULIN).end()
//                .setAndLimitAttributeFlagsTo(FLAG_NOM_GENRE_MASCULIN)
//                .setAndLimitReplAttributeFlagsTo(FLAG_NOM_GENRE_MASCULIN)
//                .end()
//            .update()
//                .condition().flagsOn(FLAG_NOM_CTX_GENRE_EPICENE).end()
//                .setAndLimitAttributeFlagsTo(FLAG_NOM_GENRE_EPICENE)
//                .setAndLimitReplAttributeFlagsTo(FLAG_NOM_GENRE_EPICENE)
//                .end()
            
            // Compléments possibles à partir de la définition
            .update()
                .condition().flagsOn(FLAG_NOM_COMPL_NONE).end()
                .setReplAttributeFlagsTo(FLAG_NOM_COMPL_NONE)
                .end()
            .update()
                .condition().flagsOn(FLAG_NOM_COMPL_COMPL).end()
                .setReplAttributeFlagsTo(FLAG_NOM_COMPL_COMPL)
                .end()

            // Complements possibles à partir du contexte
            .update()
                .condition().flagsOn(FLAG_NOM_CTX_COMPL_NONE).end()
                .setReplAttributeFlagsTo(FLAG_NOM_COMPL_NONE, FLAG_NOM_COMPL_COMPL)
                .end()
            .update()
                .condition().flagsOn(FLAG_NOM_CTX_COMPL_COMPL).end()
                .setReplAttributeFlagsTo(FLAG_NOM_COMPL_NONE)
                .end()
            
            // Type Animé si epicene
            .update()
                .condition().flagsOn(FLAG_NOM_CTX_GENRE_EPICENE).end()
                .setAttributeFlagsTo(FLAG_NOM_TYPE_ANIME)
//                .setReplAttributeFlagsTo(FLAG_NOM_TYPE_ANIME)
                .end()
            // Correspondance des types
            .update()
                .condition().flagsOn(FLAG_NOM_TYPE_ANIME).end()
                .setReplAttributeFlagsTo(FLAG_NOM_TYPE_ANIME)
                .end()
            .update()
                .condition().flagsOn(FLAG_NOM_TYPE_INANIME).end()
                .setReplAttributeFlagsTo(FLAG_NOM_TYPE_INANIME)
                .end()
            .update()
                .condition().flagsOn(FLAG_NOM_TYPE_ABSTRAIT).end()
                .setReplAttributeFlagsTo(FLAG_NOM_TYPE_ABSTRAIT)
                .end()
            ;
        

    }
    
    protected void addCommonNomCtxSampleVars(SyntagmeType.Builder builder) {
        String[][][] sampleVars = {
            { { "PMARK1",       "(s)" } },
            { { "PMARK1",       "" },       { FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "PMARK1",       "s" },      { FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            
            { { "GMARK1",       "(e)" } },
            { { "GMARK1",       "" },       { FLAG_NOM_CTX_GENRE_MASCULIN } },
            { { "GMARK1",       "e" },      { FLAG_NOM_CTX_GENRE_FEMININ } },
            
            { { "GNMARK1",      "(e)(s)" } },
            { { "GNMARK1",      "·e·(s)" }, { }, { FLAG_NOM_TYPE_ANIME } },
            { { "GNMARK1",      "(s)" },    { FLAG_NOM_CTX_GENRE_MASCULIN } },
            { { "GNMARK1",      "e(s)" },   { FLAG_NOM_CTX_GENRE_FEMININ } },
            { { "GNMARK1",      "(e)" },    { FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "GNMARK1",      "·e" },     { FLAG_NOM_CTX_NOMBRE_SINGULIER }, { FLAG_NOM_TYPE_ANIME } },
            { { "GNMARK1",      "" },       { FLAG_NOM_CTX_GENRE_MASCULIN, FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "GNMARK1",      "e" },      { FLAG_NOM_CTX_GENRE_FEMININ, FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "GNMARK1",      "(e)s" },   { FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "GNMARK1",      "·e·s" },   { FLAG_NOM_CTX_NOMBRE_PLURIEL }, { FLAG_NOM_TYPE_ANIME } },
            { { "GNMARK1",      "s" },      { FLAG_NOM_CTX_GENRE_MASCULIN, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "GNMARK1",      "es" },     { FLAG_NOM_CTX_GENRE_FEMININ, FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            
            { { "R3PGNMARK1",   "(e)(s)" } },
            { { "R3PGNMARK1",   "·e·(s)" }, { FLAG_COMMON_CTX_REF3P_T_A } },
            { { "R3PGNMARK1",   "(s)" },    { FLAG_COMMON_CTX_REF3P_G_M } },
            { { "R3PGNMARK1",   "e(s)" },   { FLAG_COMMON_CTX_REF3P_G_F } },
            { { "R3PGNMARK1",   "(e)" },    { FLAG_COMMON_CTX_REF3P_NB_S } },
            { { "R3PGNMARK1",   "·e" },     { FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_S } },
            { { "R3PGNMARK1",   "" },       { FLAG_COMMON_CTX_REF3P_G_M, FLAG_COMMON_CTX_REF3P_NB_S } },
            { { "R3PGNMARK1",   "e" },      { FLAG_COMMON_CTX_REF3P_G_F, FLAG_COMMON_CTX_REF3P_NB_S } },
            { { "R3PGNMARK1",   "(e)s" },   { FLAG_COMMON_CTX_REF3P_NB_P } },
            { { "R3PGNMARK1",   "·e·s" },   { FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_P } },
            { { "R3PGNMARK1",   "s" },      { FLAG_COMMON_CTX_REF3P_G_M, FLAG_COMMON_CTX_REF3P_NB_P } },
            { { "R3PGNMARK1",   "es" },     { FLAG_COMMON_CTX_REF3P_G_F, FLAG_COMMON_CTX_REF3P_NB_P } },
            
            { { "PVMARK1",      "(nt)" } },
            { { "PVMARK1",      "" },       { FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "PVMARK1",      "nt" },     { FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            { { "PVMARK2",      "(ssen)t" } },
            { { "PVMARK2",      "t" },      { FLAG_NOM_CTX_NOMBRE_SINGULIER } },
            { { "PVMARK2",      "ssent" },  { FLAG_NOM_CTX_NOMBRE_PLURIEL } },
            
            { { "PPREF3PS",     "il/elle" } },
            { { "PPREF3PS",     "il·elle" }, { FLAG_COMMON_CTX_REF3P_T_A } },
            { { "PPREF3PS",     "il" },     { FLAG_COMMON_CTX_REF3P_G_M } },
            { { "PPREF3PS",     "elle" },   { FLAG_COMMON_CTX_REF3P_G_F } },
            { { "PPREF3PP",     "ils/elles" } },
            { { "PPREF3PP",     "il·elle·s" }, { FLAG_COMMON_CTX_REF3P_T_A } },
            { { "PPREF3PP",     "ils" },    { FLAG_COMMON_CTX_REF3P_G_M } },
            { { "PPREF3PP",     "elles" },  { FLAG_COMMON_CTX_REF3P_G_F } },
            
            { { "SUJETI3PS",    "le machin" } },
            { { "SUJETI3PS",    "la chose" }, { FLAG_COMMON_CTX_REF3P_G_F } },
            { { "SUJETI3PP",    "les machins" } },
            { { "SUJETI3PP",    "les choses" }, { FLAG_COMMON_CTX_REF3P_G_F } },
        };
        
        addCtxVarUpdates(builder, sampleVars, null, CtxVarValueType.DEFAULT);
    }
    
    protected void addCommonNomCtxSamples(SyntagmeType.Builder builder) {
        String[][][] samples = {
            // Mise en situation : en tant que sujet ou CO
            { { "{SN} tombe{PVMARK1} par terre" }, { FLAG_NOM_CTX_NOMBRE_UNKNOWN }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} est tombé{GNMARK1} par terre" }, { FLAG_NOM_CTX_NOMBRE_SINGULIER }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} sont tombé{GNMARK1} par terre" }, { FLAG_NOM_CTX_NOMBRE_PLURIEL }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} se repose{PVMARK1}, oklm" }, { FLAG_NOM_CTX_NOMBRE_UNKNOWN }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} est décédé{GNMARK1}" }, { FLAG_NOM_CTX_NOMBRE_SINGULIER }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} sont décédé{GNMARK1}" }, { FLAG_NOM_CTX_NOMBRE_PLURIEL }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ABSTRAIT } },
            { { "oublier {SN}" }, { FLAG_NOM_CTX_NOMBRE_UNKNOWN }, { FLAG_NOM_TYPE_ABSTRAIT }, { }, { } },
            { { "{SN} a été analysé{GNMARK1}" }, { FLAG_NOM_CTX_NOMBRE_SINGULIER }, { FLAG_NOM_TYPE_ABSTRAIT }, { }, { } },
            { { "{SN} ont été analysé{GNMARK1}" }, { FLAG_NOM_CTX_NOMBRE_PLURIEL }, { FLAG_NOM_TYPE_ABSTRAIT }, { }, { } },
            
            { { "je mange {SN}" }, { FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "tu manges {SN}" }, { FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "nous mangeons {SN}" }, { FLAG_COMMON_CTX_REF_1PP }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "vous mangez {SN}" }, { FLAG_COMMON_CTX_REF_2PP }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{PPREF3PS} mange {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{PPREF3PP} mangent {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SUJETI3PS} annéantit {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_I, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_TYPE_INANIME } },
            { { "{SUJETI3PP} annéantissent {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_I, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_TYPE_INANIME } },
            
            { { "j'embauche {SN} en CDI" }, { FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "tu embauches {SN} en CDI" }, { FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "nous embauchons {SN} en CDI" }, { FLAG_COMMON_CTX_REF_1PP }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "vous embauchez {SN} en CDI" }, { FLAG_COMMON_CTX_REF_2PP }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{PPREF3PS} embauche {SN} en CDI" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{PPREF3PP} embauchent {SN} en CDI" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SUJETI3PS} met {SN} dans l'embarras" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_I, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SUJETI3PP} mettent {SN} dans l'embarras" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_I, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            
            { { "j'oublie {SN}" }, { FLAG_COMMON_CTX_REF_1PS }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "tu oublies {SN}" }, { FLAG_COMMON_CTX_REF_2PS }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "nous oublions {SN}" }, { FLAG_COMMON_CTX_REF_1PP }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "vous oubliez {SN}" }, { FLAG_COMMON_CTX_REF_2PP }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{PPREF3PS} oublie {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{PPREF3PP} oublient {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SUJETI3PS} provoque {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_I, FLAG_COMMON_CTX_REF3P_NB_S }, { FLAG_NOM_TYPE_ABSTRAIT }, { }, { FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_INANIME } },
            { { "{SUJETI3PP} provoquent {SN}" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_I, FLAG_COMMON_CTX_REF3P_NB_P }, { FLAG_NOM_TYPE_ABSTRAIT }, { }, { FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_INANIME } },
            
            // Compléments qui pourraient suivre
            { { "{SN} en bronze" }, { FLAG_NOM_CTX_COMPL_COMPL }, { FLAG_NOM_TYPE_INANIME }, {  }, { FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT } },
            
            { { "{SN} verdâtre{PMARK1}" }, { FLAG_NOM_CTX_COMPL_COMPL }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} qui vibre{PVMARK1}" }, { FLAG_NOM_CTX_COMPL_COMPL }, { FLAG_NOM_TYPE_INANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            
            { { "{SN} expérimenté{GNMARK1}" }, { FLAG_NOM_CTX_COMPL_COMPL }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ABSTRAIT } },
            { { "{SN} qui parle{PVMARK1} allemand" }, { FLAG_NOM_CTX_COMPL_COMPL }, { FLAG_NOM_TYPE_ANIME }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            
//            { { "{SN} qui fume{PVMARK1}" },     { FLAG_NOM_CTX_COMPL_COMPL }, { }, { FLAG_NOM_TYPE_ABSTRAIT } },
            
            { { "{SN} légendaire{PMARK1}" }, { FLAG_NOM_CTX_COMPL_COMPL }, { FLAG_NOM_TYPE_ABSTRAIT }, {  } },

            { { "{SN} qu'on avait un peu oublié{GNMARK1}" },    { FLAG_NOM_CTX_COMPL_COMPL } },
        };

        addCtxSampleUpdates(builder, samples);
    }
    
    
    
    public SyntagmeType createSyntagmeNominal() {
        SyntagmeType.Builder builder;
        
        builder = new SyntagmeType.Builder(this, TYPE_SNOMINAL);
        builder.setAllowByPattern(getWordRE());
        builder.setReferencesAllowed(true);
        
        // Defaults
        builder
            .update()
                .setAttributeFlagsTo(FLAG_NOM_CTX_NOMBRE_UNKNOWN, FLAG_NOM_CTX_GENRE_UNKNOWN)
                .setReplAttributeFlagsTo(FLAG_SNOMINAL_DETERMINANT_UNDEF, FLAG_SNOMINAL_DETERMINANT_DEF)
                .end()
            ;
        
        addCommonNomCtxAttributes1(builder);
        builder
            .attribute(ATTR_NOM_CTX_NOMBRE)
                .flag(FLAG_NOM_CTX_NOMBRE_UNKNOWN).end()
                .moveFlagToBottom(FLAG_NOM_CTX_NOMBRE_SINGULIER)
                .moveFlagToBottom(FLAG_NOM_CTX_NOMBRE_PLURIEL)
                .end()
            ;

        
        addCommonCtxMultiRefAttributes(builder);
        
        addCommonNomCtxAttributes2(builder);
        
        
        addCommonNomAttributes1(builder);
        
        builder
            .attribute(ATTR_SNOMINAL_DETERMINANT)
                .flag(FLAG_SNOMINAL_DETERMINANT_UNDEF).setQuickCode("U").end()
                .flag(FLAG_SNOMINAL_DETERMINANT_DEF).setQuickCode("D").end()
                .end()
            .update()
                .condition().flagsOn(FLAG_SNOMINAL_DETERMINANT_UNDEF).end()
                .setReplAttributeFlagsTo(FLAG_SNOMINAL_DETERMINANT_UNDEF)
                .end()
            .update()
                .condition().flagsOn(FLAG_SNOMINAL_DETERMINANT_DEF).end()
                .setReplAttributeFlagsTo(FLAG_SNOMINAL_DETERMINANT_DEF)
                .end()
            ;
        
        addCommonNomAttributes2(builder);
        
        addCommonDefAttributes(builder);
        
        
        addCommonCtxRefAttributesUpdates(builder);
        
        addCommonNomAttributesUpdates(builder);

        addCommonNomCtxSampleVars(builder);
        
        String[][][] specificSampleVars = {
            { { "SN", "{TEXT}" } },
        };
        
        addCtxVarUpdates(builder, specificSampleVars, null, CtxVarValueType.DEFAULT);
        
        addCommonNomCtxSamples(builder);
        
//        String[][][] samples = {
//            // On veut spécifiquement un sn avec determinant défini : doit marcher avec l'expression "Vive xxx"
//            { { "vive{PVMARK1} {SN}" }, { }, { FLAG_SNOMINAL_DETERMINANT_DEF }, { }, { FLAG_SNOMINAL_DETERMINANT_UNDEF } },
//        };
//
//        addCtxSampleUpdates(builder, samples);

        return new SyntagmeType(builder);
    }
    
    public SyntagmeType createVerbe() {
        SyntagmeType.Builder builder;
        
        builder = new SyntagmeType.Builder(this, TYPE_VERBE);
        builder.setAllowByPattern(getWordRE());
        builder.setReferencesAllowed(true);
        
        // Defaults
        builder
            .update()
                .disableAttributeFlags(ATTR_VERBE_CTX_SUJET_TYPE,
                    ATTR_VERBE_CTX_CODINFO_TYPE, ATTR_VERBE_CTX_CODINFO_PERS,
                    ATTR_VERBE_CTX_CODINFO_GENRE,
                    ATTR_VERBE_CTX_PPASSE_AUXE)
                // Pas de CC ni negation avec un seul mot
                .limitAttributeFlagsTo(FLAG_VERBE_CC_WITHOUT, FLAG_VERBE_NEGATION_WITHOUT)
                .setAttributeFlagsTo(FLAG_VERBE_CTX_PREFLECHI_NO, FLAG_VERBE_CTX_PPASSE_AUX_NONE,
                    FLAG_VERBE_CC_WITHOUT, FLAG_VERBE_NEGATION_WITHOUT)
//                .setReplAttributeFlagsTo(
//                        FLAG_VERBE_NEGATION_WITH, FLAG_VERBE_NEGATION_WITHOUT)
                .end()
            ;
        
        builder
            .attribute(ATTR_VERBE_CTX_SUJET_PERS).setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_SUJET)
                .flag(FLAG_VERBE_CTX_SUJET_PERS_1S).end()
                .flag(FLAG_VERBE_CTX_SUJET_PERS_2S).end()
                .flag(FLAG_VERBE_CTX_SUJET_PERS_3S).end()
                .flag(FLAG_VERBE_CTX_SUJET_PERS_3SM).end()
                .flag(FLAG_VERBE_CTX_SUJET_PERS_1P).end()
                .flag(FLAG_VERBE_CTX_SUJET_PERS_2P).end()
                .flag(FLAG_VERBE_CTX_SUJET_PERS_3P).end()
                .end()
            .attribute(ATTR_VERBE_CTX_SUJET_G)
                .setContext(true).setRequired(false)
//                .setGroupCode(AGRP_VERBE_CTX_SUJET)
                .flag(FLAG_VERBE_CTX_SUJET_G_0).end()
                .flag(FLAG_VERBE_CTX_SUJET_G_F).end()
                .flag(FLAG_VERBE_CTX_SUJET_G_M).end()
                .end()
            .attribute(ATTR_VERBE_CTX_SUJET_TYPE)
                .setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_SUJET)
                .flag(FLAG_VERBE_CTX_SUJET_TYPE_A).end()
                .flag(FLAG_VERBE_CTX_SUJET_TYPE_I).end()
                .flag(FLAG_VERBE_CTX_SUJET_TYPE_ABS).end()
                .end()
                
            .attribute(ATTR_VERBE_CTX_PREFLECHI)
                .setContext(true).setRequired(true)
                .flag(FLAG_VERBE_CTX_PREFLECHI_NO).end()
                .flag(FLAG_VERBE_CTX_PREFLECHI_YES).end()
                .end()
                
            .attribute(ATTR_VERBE_CTX_COD)
                .setContext(true).setRequired(true)
                .flag(FLAG_VERBE_CTX_COD_NONE).end()
                .flag(FLAG_VERBE_CTX_COD_AFTER).setQuickCode("TR").end()
                .flag(FLAG_VERBE_CTX_COD_BEFORE).setQuickCode("TR").end()
                .end()
            .attribute(ATTR_VERBE_CTX_CODINFO_PERS)
                .setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_CODINFO)
                .flag(FLAG_VERBE_CTX_CODINFO_PERS_1S).end()
                .flag(FLAG_VERBE_CTX_CODINFO_PERS_2S).end()
                .flag(FLAG_VERBE_CTX_CODINFO_PERS_3S).end()
                .flag(FLAG_VERBE_CTX_CODINFO_PERS_1P).end()
                .flag(FLAG_VERBE_CTX_CODINFO_PERS_2P).end()
                .flag(FLAG_VERBE_CTX_CODINFO_PERS_3P).end()
                .end()
            .attribute(ATTR_VERBE_CTX_CODINFO_GENRE)
                .setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_CODINFO)
                .flag(FLAG_VERBE_CTX_CODINFO_GENRE_0).end()
                .flag(FLAG_VERBE_CTX_CODINFO_GENRE_F).end()
                .flag(FLAG_VERBE_CTX_CODINFO_GENRE_M).end()
                .end()
            .attribute(ATTR_VERBE_CTX_CODINFO_TYPE)
                .setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_CODINFO)
                .flag(FLAG_VERBE_CTX_CODINFO_TYPE_ANIME).end()
                .flag(FLAG_VERBE_CTX_CODINFO_TYPE_INANIME).end()
                .flag(FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT).end()
                .end()
            .attribute(ATTR_VERBE_CTX_PPASSE_AUX)
                .setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_PPASSE)
                .flag(FLAG_VERBE_CTX_PPASSE_AUX_NONE).end()
                .flag(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR).end()
                .flag(FLAG_VERBE_CTX_PPASSE_AUX_ETRE).end()
                .end()
            .attribute(ATTR_VERBE_CTX_PPASSE_AUXE)
                .setContext(true).setRequired(true)
//                .setGroupCode(AGRP_VERBE_CTX_PPASSE)
                .flag(FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE).end()
                .flag(FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE).end()
                .flag(FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVECDA).end()
                .end()
            ;
        
        addCommonCtxMultiRefAttributes(builder);
        
        builder
            .attribute(ATTR_VERBE_FORME)
                .flag(FLAG_VERBE_FORME_INFINITIF).setQuickCode("INF").end()
                .flag(FLAG_VERBE_FORME_PRESENT).setQuickCode("PRE").end()
                .flag(FLAG_VERBE_FORME_PASSECOMPOSE).setQuickCode("PCO").end()
                .flag(FLAG_VERBE_FORME_PARTICIPEPASSE).setQuickCode("PPA").end()
                .flag(FLAG_VERBE_FORME_IMPARFAIT).setQuickCode("IMPA").end()
                .flag(FLAG_VERBE_FORME_PQPARFAIT).setQuickCode("PQP").end()
                .flag(FLAG_VERBE_FORME_FUTURANT).setQuickCode("FANT").end()
                .flag(FLAG_VERBE_FORME_PASSESIMPLE).setQuickCode("PASS").end()
                .flag(FLAG_VERBE_FORME_FUTUR).setQuickCode("FUT").end()
                .flag(FLAG_VERBE_FORME_IMPERATIF).setQuickCode("IMPE").end()
                .flag(FLAG_VERBE_FORME_PARTICIPEPRESENT).setQuickCode("PPE").end()
                .end()
            .attribute(ATTR_VERBE_NEGATION)
                .flag(FLAG_VERBE_NEGATION_WITHOUT).end()
                .flag(FLAG_VERBE_NEGATION_WITH).end()
                .end()
            .attribute(ATTR_VERBE_CC)
                .flag(FLAG_VERBE_CC_WITHOUT).end()
                .flag(FLAG_VERBE_CC_WITH).end()
                .end()
            ;
        
        
        addCommonDefAttributes(builder);
        
        addCommonCtxRefAttributesUpdates(builder);
        
        addVerbeUpdatesFromSDText(builder);
        
        builder
            .update()
                .condition().flagsOn(FLAG_VERBE_NEGATION_WITH).end()
                .setReplAttributeFlagsTo(FLAG_VERBE_NEGATION_WITH)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_NEGATION_WITHOUT).end()
                .setReplAttributeFlagsTo(FLAG_VERBE_NEGATION_WITHOUT)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CC_WITH).end()
                .setReplAttributeFlagsTo(FLAG_VERBE_CC_WITH)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CC_WITHOUT).end()
                .setReplAttributeFlagsTo(FLAG_VERBE_CC_WITHOUT)
                .end()

            .update()
                .condition().or()
                    .flagsOn(FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_PERS_3P)
                    .end()
                .enableAttributeFlags(ATTR_VERBE_CTX_SUJET_TYPE)
                .setAttributeFlagsTo(FLAG_VERBE_CTX_SUJET_TYPE_A)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_AFTER).end()
                .enableAttributeFlags(ATTR_VERBE_CTX_CODINFO_TYPE)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_BEFORE).end()
                .enableAttributeFlags(
                        ATTR_VERBE_CTX_CODINFO_PERS, ATTR_VERBE_CTX_CODINFO_GENRE)
                .setAttributeFlagsTo(FLAG_VERBE_CTX_CODINFO_GENRE_0)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_SUJET_PERS_1S).end()
                .disableFlags(FLAG_VERBE_CTX_CODINFO_PERS_1S).end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_SUJET_PERS_2S).end()
                .disableFlags(FLAG_VERBE_CTX_CODINFO_PERS_2S).end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_SUJET_PERS_1P).end()
                .disableFlags(FLAG_VERBE_CTX_CODINFO_PERS_1P).end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_SUJET_PERS_2P).end()
                .disableFlags(FLAG_VERBE_CTX_CODINFO_PERS_2P).end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_PREFLECHI_YES).end()
                .limitAttributeFlagsTo(
                        FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_PERS_3P)
                .end()
            .update()
                .condition().or()
                    .flagsOn(FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_PERS_3P)
                    .end()
                .enableAttributeFlags(ATTR_VERBE_CTX_CODINFO_TYPE)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_PREFLECHI_YES).end()
                .limitAttributeFlagsTo(FLAG_VERBE_CTX_PPASSE_AUX_NONE, FLAG_VERBE_CTX_PPASSE_AUX_ETRE)
                .end()
            ;
        

        // Formes spéciales forcées à partir de la def
        for (String flag : Arrays.asList(FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_FORME_PARTICIPEPASSE,
                FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_FORME_PARTICIPEPRESENT)) {
            builder
                .update()
                    .condition().flagsOn(flag).end()
                    .setAndLimitReplAttributeFlagsTo(flag);
        }

        // Formes pouvant potentiellement être remplacées par d'autres
        final String[] formesCompatibles = {
            FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_FORME_PASSECOMPOSE,
            FLAG_VERBE_FORME_IMPARFAIT, FLAG_VERBE_FORME_PQPARFAIT,
            FLAG_VERBE_FORME_FUTURANT, FLAG_VERBE_FORME_PASSESIMPLE,
            FLAG_VERBE_FORME_FUTUR
        };
        for (String flag : Arrays.asList(formesCompatibles)) {
            builder
                .update()
                    .condition().flagsOn(flag).end()
                    .limitReplAttributeFlagsTo(formesCompatibles)
                    .setReplAttributeFlagsTo(flag);
        }
        
        builder
            .update()
                .condition()
                    .or()
                        .flagsOn(FLAG_VERBE_FORME_PARTICIPEPASSE)
                        .replFlagsOn(FLAG_VERBE_FORME_PARTICIPEPASSE)
                        .parent()
                    .flagsOff(FLAG_VERBE_CTX_PREFLECHI_YES)
                    .end()
                .limitAttributeFlagsTo(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_PPASSE_AUX_ETRE)
                .setAttributeFlagsTo(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR)
                .end()
            .update()
                .condition()
                    .or()
                        .flagsOn(FLAG_VERBE_FORME_PARTICIPEPASSE)
                        .replFlagsOn(FLAG_VERBE_FORME_PARTICIPEPASSE)
                        .parent()
                    .flagsOn(FLAG_VERBE_CTX_PREFLECHI_YES)
                    .end()
                .setAndLimitAttributeFlagsTo(FLAG_VERBE_CTX_PPASSE_AUX_ETRE)
                .end()
            .update()
                .condition().or().flagsOn(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_PPASSE_AUX_ETRE).end()
                .setAndLimitAttributeFlagsTo(FLAG_VERBE_FORME_PARTICIPEPASSE, FLAG_VERBE_NEGATION_WITHOUT)
                .setAndLimitReplAttributeFlagsTo(FLAG_VERBE_FORME_PARTICIPEPASSE, FLAG_VERBE_NEGATION_WITHOUT)
                .end()
            .update()
                .condition().flagsOn(FLAG_VERBE_CTX_PPASSE_AUX_ETRE)
                    .flagsOff(FLAG_VERBE_CTX_PREFLECHI_YES).end()
                .enableAttributeFlags(ATTR_VERBE_CTX_PPASSE_AUXE)
                // Pas de COD si aux etre sans pronom reflechi
                .limitAttributeFlagsTo(FLAG_VERBE_CTX_COD_NONE)
                // On estime que passif agentif est le cas le plus courant
                .setAttributeFlagsTo(FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVECDA,
                    FLAG_VERBE_CTX_COD_NONE)
                .end()
                
            .update()
                .condition().or().flagsOn(FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_FORME_IMPERATIF).end()
                // Limite les sujets si imperatif
                .limitAttributeFlagsTo(FLAG_VERBE_CTX_SUJET_PERS_2S,
                        FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_SUJET_PERS_2P,
                        // Empeche aussi le COD pronominal 
                        // (tant pis pour les attributs liés déjà visibles ; cas rare)
                        FLAG_VERBE_CTX_COD_NONE, FLAG_VERBE_CTX_COD_AFTER
                )
                .end()
            ;
        
        // Variables pour prefixes
        String[][][] prefixSampleVars = {
            { { "PP",           "je " },    { FLAG_VERBE_CTX_SUJET_PERS_1S } },
            { { "PP",           "tu " },    { FLAG_VERBE_CTX_SUJET_PERS_2S } },
            { { "PP",           "il/elle " }, { FLAG_VERBE_CTX_SUJET_PERS_3S } },
            { { "PP",           "il·elle " }, { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_A } },
            { { "PP",           "il " },    { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M } },
            { { "PP",           "elle " },  { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F } },
            { { "PP",           "on " },    { FLAG_VERBE_CTX_SUJET_PERS_3SM } },
            { { "PP",           "nous " },  { FLAG_VERBE_CTX_SUJET_PERS_1P } },
            { { "PP",           "vous " },  { FLAG_VERBE_CTX_SUJET_PERS_2P } },
            { { "PP",           "ils/elles " }, { FLAG_VERBE_CTX_SUJET_PERS_3P } },
            { { "PP",           "il·elle·s " }, { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_A } },
            { { "PP",           "ils " },   { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M } },
            { { "PP",           "elles " }, { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F } },
            { { "PPE",          "{PP}" } },
            { { "PPE",          "j'" },     { FLAG_VERBE_CTX_SUJET_PERS_1S } },


            { { "PRMARK",       "se " } },
            { { "PRMARK",       "me " },    { FLAG_VERBE_CTX_SUJET_PERS_1S } },
            { { "PRMARK",       "te " },    { FLAG_VERBE_CTX_SUJET_PERS_2S } },
            { { "PRMARK",       "nous " },  { FLAG_VERBE_CTX_SUJET_PERS_1P } },
            { { "PRMARK",       "vous " },  { FLAG_VERBE_CTX_SUJET_PERS_2P } },
            { { "PRMARKE",      "{PRMARK}" } },
            { { "PRMARKE",      "s'" },     { FLAG_VERBE_CTX_SUJET_PERS_3S }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "PRMARKE",      "s'" },     { FLAG_VERBE_CTX_SUJET_PERS_3SM }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "PRMARKE",      "s'" },     { FLAG_VERBE_CTX_SUJET_PERS_3P }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "PRMARKE",      "m'" },     { FLAG_VERBE_CTX_SUJET_PERS_1S }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "PRMARKE",      "t'" },     { FLAG_VERBE_CTX_SUJET_PERS_2S }, { }, { FLAG_COMMON_ELISION_ON } },
            
            { { "PRCOD",        "me " },    { FLAG_VERBE_CTX_CODINFO_PERS_1S } },
            { { "PRCOD",        "te " },    { FLAG_VERBE_CTX_CODINFO_PERS_2S } },
            { { "PRCOD",        "la/le " }, { FLAG_VERBE_CTX_CODINFO_PERS_3S } },
            { { "PRCOD",        "la·le " }, { FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME } },
            { { "PRCOD",        "la " },    { FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F } },
            { { "PRCOD",        "le " },    { FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M } },
            { { "PRCOD",        "nous " },  { FLAG_VERBE_CTX_CODINFO_PERS_1P } },
            { { "PRCOD",        "vous " },  { FLAG_VERBE_CTX_CODINFO_PERS_2P } },
            { { "PRCOD",        "les " },   { FLAG_VERBE_CTX_CODINFO_PERS_3P } },
            { { "PRCODE",       "{PRCOD}" } },
            { { "PRCODE",       "m'" },     { FLAG_VERBE_CTX_CODINFO_PERS_1S } },
            { { "PRCODE",       "t'" },     { FLAG_VERBE_CTX_CODINFO_PERS_2S } },
            { { "PRCODE",       "l'" },     { FLAG_VERBE_CTX_CODINFO_PERS_3S } },
        };
        
        addCtxVarUpdates(builder, prefixSampleVars, null, CtxVarValueType.DEFAULT);
        
        // Prefixes de mise en situation
        String[][][] prefixes = {
            { { "{PP}" },               { } },
            { { "{PPE}" },              { }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "{PP}{PRCOD}" },        { FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PP}{PRCODE}" },       { FLAG_VERBE_CTX_COD_BEFORE }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "{PP}{PRMARK}" },       { FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}{PRMARKE}" },      { FLAG_VERBE_CTX_PREFLECHI_YES }, { }, { FLAG_COMMON_ELISION_ON } },
            { { "{PP}{PRMARK}{PRCOD}" },    { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PP}{PRMARK}{PRCODE}" },   { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE }, { }, { FLAG_COMMON_ELISION_ON } },

            { { "{PPE}ai " },           { FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}ai " },    { FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            // Etre : considère que c'est passif en général, donc par défaut ; montre une forme où ya que le passif qu'est possible
            { { "{PPE}ai été " },       { FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PP}suis " },          { FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}me suis " },       { FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}me {PRCOD}suis " },{ FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },

            { { "{PPE}as " },           { FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}as " },    { FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PPE}as été " },       { FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PPE}es " },           { FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}t'es " },          { FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}te {PRCODE}es " }, { FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },

            { { "{PPE}a " },            { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}a " },     { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PPE}a été " },        { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PPE}est " },          { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}s'est " },         { FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}se {PRCODE}est " },{ FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },

            { { "{PPE}a " },            { FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}a " },     { FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PPE}a été " },        { FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PPE}est " },          { FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}s'est " },         { FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}se {PRCODE}est " },{ FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },

            { { "{PPE}avons " },        { FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}avons " }, { FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PPE}avons été " },    { FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PP}sommes " },        { FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}nous sommes " },   { FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}nous {PRCOD}sommes " },{ FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },

            { { "{PPE}avez " },         { FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}avez " },  { FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PPE}avez été " },     { FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PP}êtes " },          { FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}vous êtes " },     { FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}vous {PRCODE}êtes " }, { FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },

            { { "{PPE}ont " },          { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR } },
            { { "{PP}{PRCODE}ont " },   { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE } },
            { { "{PPE}ont été " },      { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE } },
            { { "{PP}sont " },          { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE } },
            { { "{PP}se sont " },       { FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES } },
            { { "{PP}se {PRCOD}sont " },{ FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE } },


            { { "" },                   { }, { FLAG_VERBE_FORME_INFINITIF } },
            { { "{PRCOD}" },            { FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_INFINITIF } },
            { { "{PRCODE}" },           { FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_INFINITIF }, { FLAG_COMMON_ELISION_ON } },
            { { "{PRMARK}" },           { FLAG_VERBE_CTX_PREFLECHI_YES }, { FLAG_VERBE_FORME_INFINITIF } },
            { { "{PRMARKE}" },          { FLAG_VERBE_CTX_PREFLECHI_YES }, { FLAG_VERBE_FORME_INFINITIF }, { FLAG_COMMON_ELISION_ON } },
            { { "{PRMARK}{PRCOD}" },    { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_INFINITIF } },
            { { "{PRMARK}{PRCODE}" },   { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_INFINITIF }, { FLAG_COMMON_ELISION_ON } },

            { { "" },                   { }, { FLAG_VERBE_FORME_PARTICIPEPRESENT } },
            { { "{PRCOD}" },            { FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_PARTICIPEPRESENT } },
            { { "{PRCODE}" },           { FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_PARTICIPEPRESENT }, { FLAG_COMMON_ELISION_ON } },
            { { "{PRMARK}" },           { FLAG_VERBE_CTX_PREFLECHI_YES }, { FLAG_VERBE_FORME_PARTICIPEPRESENT } },
            { { "{PRMARKE}" },          { FLAG_VERBE_CTX_PREFLECHI_YES }, { FLAG_VERBE_FORME_PARTICIPEPRESENT }, { FLAG_COMMON_ELISION_ON } },
            { { "{PRMARK}{PRCOD}" },    { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_PARTICIPEPRESENT } },
            { { "{PRMARK}{PRCODE}" },   { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE }, { FLAG_VERBE_FORME_PARTICIPEPRESENT }, { FLAG_COMMON_ELISION_ON } },

            { { "" },                   { }, { FLAG_VERBE_FORME_IMPERATIF } },
        };
        
        addCtxVarUpdates(builder, prefixes, SyntagmeDefinition.CTX_VAR_PREFIX, CtxVarValueType.DEFAULT);

        // Suffixes de mise en situation
        String[][][] suffixes = {
            { { "" } },
            { { " quelque chose" },     { FLAG_VERBE_CTX_COD_AFTER } },
            { { " quelqu'un" },         { FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME } },
            { { " par ..." },           { FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVECDA } },
            
            { { "-toi" },               { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_SUJET_PERS_2S }, { FLAG_VERBE_FORME_IMPERATIF } },
            { { "-nous" },              { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_SUJET_PERS_1P }, { FLAG_VERBE_FORME_IMPERATIF } },
            { { "-vous" },              { FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_SUJET_PERS_2P }, { FLAG_VERBE_FORME_IMPERATIF } },
        };
        
        addCtxVarUpdates(builder, suffixes, SyntagmeDefinition.CTX_VAR_SUFFIX, CtxVarValueType.DEFAULT);

        // Verbes semi auxiliaires pour l'infinitif / sujet animé
        String[][][] semiAuxInfiValues = {
            { { "{PPE}aime" },      { FLAG_VERBE_CTX_SUJET_PERS_1S } },
            { { "{PPE}aimes" },     { FLAG_VERBE_CTX_SUJET_PERS_2S } },
            { { "{PPE}aime" },      { FLAG_VERBE_CTX_SUJET_PERS_3S } },
            { { "{PPE}aime" },      { FLAG_VERBE_CTX_SUJET_PERS_3SM } },
            { { "{PPE}aimons" },    { FLAG_VERBE_CTX_SUJET_PERS_1P } },
            { { "{PPE}aimez" },     { FLAG_VERBE_CTX_SUJET_PERS_2P } },
            { { "{PPE}aiment" },    { FLAG_VERBE_CTX_SUJET_PERS_3P } },
            
            { { "{PP}préfère" },    { FLAG_VERBE_CTX_SUJET_PERS_1S } },
            { { "{PP}préfères" },   { FLAG_VERBE_CTX_SUJET_PERS_2S } },
            { { "{PP}préfère" },    { FLAG_VERBE_CTX_SUJET_PERS_3S } },
            { { "{PP}préfère" },    { FLAG_VERBE_CTX_SUJET_PERS_3SM } },
            { { "{PP}préfèrons" },  { FLAG_VERBE_CTX_SUJET_PERS_1P } },
            { { "{PP}préfèrez" },   { FLAG_VERBE_CTX_SUJET_PERS_2P } },
            { { "{PP}préfèrent" },  { FLAG_VERBE_CTX_SUJET_PERS_3P } },

            { { "{PP}voudrais" },   { FLAG_VERBE_CTX_SUJET_PERS_1S } },
            { { "{PP}voudrais" },   { FLAG_VERBE_CTX_SUJET_PERS_2S } },
            { { "{PP}voudrait" },   { FLAG_VERBE_CTX_SUJET_PERS_3S } },
            { { "{PP}voudrait" },   { FLAG_VERBE_CTX_SUJET_PERS_3SM } },
            { { "{PP}voudrions" },  { FLAG_VERBE_CTX_SUJET_PERS_1P } },
            { { "{PP}voudriez" },   { FLAG_VERBE_CTX_SUJET_PERS_2P } },
            { { "{PP}voudraient" }, { FLAG_VERBE_CTX_SUJET_PERS_3P } },
        };
        
        addCtxVarUpdates(builder, semiAuxInfiValues, "SEMIAUX1", CtxVarValueType.RAND);

        String[][][] samples = {
            { { "{PREFIX}{TEXT}{SUFFIX}" } },
            { { "{SEMIAUX1} {PREFIX}{TEXT}{SUFFIX}" }, { }, { FLAG_VERBE_FORME_INFINITIF }, { FLAG_VERBE_CTX_SUJET_TYPE_I, FLAG_VERBE_CTX_SUJET_TYPE_ABS } },
        };

        addCtxSampleUpdates(builder, samples);


        addVerbeUpdatesSelectingFlags(builder);

        return new SyntagmeType(builder);
    }
        
    protected void addVerbeUpdatesFromSDText(SyntagmeType.Builder builder) {
        builder
            .update()
                .condition().textMatch(getMultipleWordsRE()).end()
                .disableAttributeFlags(ATTR_VERBE_CC, ATTR_VERBE_NEGATION)
                .enableAttributeFlags(ATTR_VERBE_CC, ATTR_VERBE_NEGATION)
                .setReplAttributeFlagsTo(
                        FLAG_VERBE_CC_WITH, FLAG_VERBE_CC_WITHOUT,
                        FLAG_VERBE_NEGATION_WITH, FLAG_VERBE_NEGATION_WITHOUT)
                .end()
            .update()
                .condition().textMatch(
                        "^[^" + getWordREChars() + "]*(?:ne|n)[^" + getWordREChars() + "]", true, false).end()
                .setAttributeFlagsTo(FLAG_VERBE_NEGATION_WITH)
                .end()
            ;
    }
    
    protected void addVerbeUpdatesSelectingFlags(SyntagmeType.Builder builder) {
        builder
            .update()
                .condition().or().prevWords("se").prevWords("s").end()
                .setFlagsToSelect(FLAG_VERBE_CTX_PREFLECHI_YES)
                .end()
            ;
    }
    
    public SyntagmeType createComplementCirconstanciel() {
        SyntagmeType.Builder builder;
        
        builder = new SyntagmeType.Builder(this, TYPE_COMPCIRC);
        builder.setAllowByPattern(getWordRE());
        builder.setReferencesAllowed(true);

        
        // Defaults
        builder
            .update()
                .setReplAttributeFlagsTo(FLAG_COMPCIRC_TYPE_LIEU, 
                        FLAG_COMPCIRC_TYPE_TEMPS, FLAG_COMPCIRC_TYPE_OTHER)
                .end()
            ;
        
        addCommonCtxMultiRefAttributes(builder);
//        builder.attribute(ATTR_COMMON_CTX_REF).setRequired(true);
        
        builder
            .attribute(ATTR_COMPCIRC_TYPE)
                .flag(FLAG_COMPCIRC_TYPE_LIEU).setQuickCode("CCL").end()
                .flag(FLAG_COMPCIRC_TYPE_TEMPS).setQuickCode("CCT").end()
                .flag(FLAG_COMPCIRC_TYPE_OTHER).setQuickCode("CCM").end()
                .end()
            ;
        
        addCommonDefAttributes(builder);
        
        addCommonCtxRefAttributesUpdates(builder);

        
//        String[][][] ppSampleVar = {
//            { { "je" },     { FLAG_COMMON_CTX_REF_1PS } },
//            { { "tu" },     { FLAG_COMMON_CTX_REF_2PS } },
//            { { "il/elle" }, { FLAG_COMMON_CTX_REF_3P } },
//            { { "il·elle" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_T_A } },
//            { { "il" },     { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_G_M } },
//            { { "elle" },   { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_G_F } },
//            { { "ils/elles" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P } },
//            { { "il·elle·s" }, { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_COMMON_CTX_REF3P_T_A } },
//            { { "ils" },    { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_COMMON_CTX_REF3P_G_M } },
//            { { "elles" },  { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_COMMON_CTX_REF3P_G_F } },
//            { { "nous" },   { FLAG_COMMON_CTX_REF_1PP } },
//            { { "vous" },   { FLAG_COMMON_CTX_REF_2PP } },
//        };
//        
//        addCtxVarUpdates(builder, ppSampleVar, "PP", CtxVarValueType.DEFAULT);
//
//        String[][][] svSampleVars = {
//            { { "SV1S",     "dors" } },
//            { { "SV2S",     "dors" } },
//            { { "SV1P",     "dormons" } },
//            { { "SV2P",     "dormez" } },
//            { { "SV3S",     "dort" } },
//            { { "SV3P",     "dorment" } },
//
//            { { "SVP1S",    "dormais" } },
//            { { "SVP2S",    "dormais" } },
//            { { "SVP1P",    "dormions" } },
//            { { "SVP2P",    "dormiez" } },
//            { { "SVP3S",    "dormait" } },
//            { { "SVP3P",    "dormaient" } },
//
//            { { "SVF1S",    "dormirai" } },
//            { { "SVF2S",    "dormiras" } },
//            { { "SVF1P",    "dormirons" } },
//            { { "SVF2P",    "dormirez" } },
//            { { "SVF3S",    "dormira" } },
//            { { "SVF3P",    "dormiront" } },
//        };
//        
//        addCtxVarUpdates(builder, svSampleVars, null, CtxVarValueType.RAND);
//
//        String[][][] samples = {
//            { { "{PP} {SV1S} {TEXT}" },     { FLAG_COMMON_CTX_REF_1PS } },
//            { { "{PP} {SV2S} {TEXT}" },     { FLAG_COMMON_CTX_REF_2PS } },
//            { { "{PP} {SV1P} {TEXT}" },     { FLAG_COMMON_CTX_REF_1PP } },
//            { { "{PP} {SV2P} {TEXT}" },     { FLAG_COMMON_CTX_REF_2PP } },
//            { { "{PP} {SV3S} {TEXT}" },     { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S } },
//            { { "{PP} {SV3P} {TEXT}" },     { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P } },
//
//            { { "{PP} {SVP1S} {TEXT}" },    { FLAG_COMMON_CTX_REF_1PS } },
//            { { "{PP} {SVP2S} {TEXT}" },    { FLAG_COMMON_CTX_REF_2PS } },
//            { { "{PP} {SVP1P} {TEXT}" },    { FLAG_COMMON_CTX_REF_1PP } },
//            { { "{PP} {SVP2P} {TEXT}" },    { FLAG_COMMON_CTX_REF_2PP } },
//            { { "{PP} {SVP3S} {TEXT}" },    { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S } },
//            { { "{PP} {SVP3P} {TEXT}" },    { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P } },
//
//            { { "{PP} {SVF1S} {TEXT}" },    { FLAG_COMMON_CTX_REF_1PS } },
//            { { "{PP} {SVF2S} {TEXT}" },    { FLAG_COMMON_CTX_REF_2PS } },
//            { { "{PP} {SVF1P} {TEXT}" },    { FLAG_COMMON_CTX_REF_1PP } },
//            { { "{PP} {SVF2P} {TEXT}" },    { FLAG_COMMON_CTX_REF_2PP } },
//            { { "{PP} {SVF3S} {TEXT}" },    { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S } },
//            { { "{PP} {SVF3P} {TEXT}" },    { FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P } },
//        };
//
//        addCtxSampleUpdates(builder, samples);
        
        return new SyntagmeType(builder);
    }
    
    
    protected void addCommonCtxRefAttributes(SyntagmeType.Builder builder) {
        builder
            .attribute(ATTR_COMMON_CTX_REF).setContext(true)
                .flag(FLAG_COMMON_CTX_REF_NONE).end()
                .flag(FLAG_COMMON_CTX_REF_1PS).end()
                .flag(FLAG_COMMON_CTX_REF_2PS).end()
                .flag(FLAG_COMMON_CTX_REF_3P).end()
                .flag(FLAG_COMMON_CTX_REF_1PP).end()
                .flag(FLAG_COMMON_CTX_REF_2PP).end()
                .end()
            .attribute(ATTR_COMMON_CTX_REF3P_NB).setContext(true).setRequired(true)
//                .setGroupCode(AGRP_COMMON_CTX_REF3P)
                .flag(FLAG_COMMON_CTX_REF3P_NB_S).end()
                .flag(FLAG_COMMON_CTX_REF3P_NB_P).end()
                .end()
            .attribute(ATTR_COMMON_CTX_REF3P_G).setContext(true).setRequired(true)
//                .setGroupCode(AGRP_COMMON_CTX_REF3P)
                .flag(FLAG_COMMON_CTX_REF3P_G_0).end()
                .flag(FLAG_COMMON_CTX_REF3P_G_F).end()
                .flag(FLAG_COMMON_CTX_REF3P_G_M).end()
                .end()
            .attribute(ATTR_COMMON_CTX_REF3P_T).setContext(true).setRequired(true)
//                .setGroupCode(AGRP_COMMON_CTX_REF3P)
                .flag(FLAG_COMMON_CTX_REF3P_T_A).end()
                .flag(FLAG_COMMON_CTX_REF3P_T_I).end()
                .end()
            .update()
                .disableAttributeFlags(ATTR_COMMON_CTX_REF3P_NB, 
                        ATTR_COMMON_CTX_REF3P_G, ATTR_COMMON_CTX_REF3P_T)
                .end()
            ;
    }
    
    protected void addCommonCtxMultiRefAttributes(SyntagmeType.Builder builder) {
        addCommonCtxRefAttributes(builder);
        builder
            .attribute(ATTR_COMMON_CTX_REF).setMulti(true)
                .removeFlag(FLAG_COMMON_CTX_REF_NONE);
    }
    
    protected void addCommonCtxRefAttributesUpdates(SyntagmeType.Builder b) {
        b
            .update()
                .condition()
                    .flagsOn(FLAG_COMMON_CTX_REF_3P)
                    .end()
                .enableAttributeFlags(ATTR_COMMON_CTX_REF3P_NB, ATTR_COMMON_CTX_REF3P_G, ATTR_COMMON_CTX_REF3P_T)
                .setAttributeFlagsTo(FLAG_COMMON_CTX_REF3P_NB_S, FLAG_COMMON_CTX_REF3P_G_0, FLAG_COMMON_CTX_REF3P_T_A)
                .end()
            ;
    }
    
    @Override
    public void addCommonDefAttributes(SyntagmeType.Builder builder) {
        super.addCommonDefAttributes(builder);
        
        builder
            .attribute(ATTR_COMMON_KEEP_MAJ)
                .setDefinitionOnly(true).setRequired(true)
                .flag(FLAG_COMMON_KEEP_MAJ_ALL).end()
                .flag(FLAG_COMMON_KEEP_MAJ_INSIDE).end()
                .flag(FLAG_COMMON_KEEP_MAJ_NONE).end()
                .end()
            .attribute(ATTR_COMMON_HASPIRE)
                .setDefinitionOnly(true).setRequired(true)
                .flag(FLAG_COMMON_HASPIRE).end()
                .flag(FLAG_COMMON_HMUET).end()
                .end()
            .attribute(ATTR_COMMON_ELISION)
                .setDefinitionOnly(true).setVirtual(true)
                .flag(FLAG_COMMON_ELISION_OFF).end()
                .flag(FLAG_COMMON_ELISION_ON).end()
                .end()
            .update()
                .disableAttributeFlags(ATTR_COMMON_KEEP_MAJ, ATTR_COMMON_HASPIRE)
                .setAttributeFlagsTo(FLAG_COMMON_ELISION_OFF)
                .end()
            .update()
                .condition().flagsOn(FLAG_COMMON_REUSABLE_ON).textMatch("\\p{Lu}").end()
                .enableAttributeFlags(ATTR_COMMON_KEEP_MAJ)
                .end()
            .update()
                .condition().flagsOn(FLAG_COMMON_PHASE_REPL).textMatch("\\p{Lu}").end()
                .setAttributeFlagsTo(FLAG_COMMON_KEEP_MAJ_ALL)
                .end()
            .update()
                // Premier caractere de mot rencontré est un H
                .condition().flagsOn(FLAG_COMMON_REUSABLE_ON).textMatch("^[^" + getWordREChars() + "]*[hH]").end()
                .enableAttributeFlags(ATTR_COMMON_HASPIRE)
                .end()
            .update()
                // Maj du flag eliision
                .condition()
                    .textMatch("^[^" + getWordREChars() + "]*[aeiouh]", true, true)
                    .flagsOff(FLAG_COMMON_HASPIRE)
                    .end()
                .setAttributeFlagsTo(FLAG_COMMON_ELISION_ON)
            ;
    }

    protected void addCtxVarUpdate(SyntagmeType.Builder builder, String var,
            String[] values, CtxVarValueType valuesType, 
            String[] origDefFlags, String[] origReplFlags, String[] curDefFlags) {
        SyntagmeDefinitionUpdate.Builder ub = builder.update();
        if (origDefFlags != null && origDefFlags.length > 0) {
            ub.condition().origFlagsOn(origDefFlags);
        }
        if (origReplFlags != null && origReplFlags.length > 0) {
            ub.condition().origReplFlagsOn(origReplFlags);
        }
        if (curDefFlags != null && curDefFlags.length > 0) {
            ub.condition().flagsOn(curDefFlags);
        }
        ub.setCtxVarValue(var, values, valuesType);
    }
//    protected void addCtxVarUpdates(SyntagmeType.Builder builder, String[][] varsData, String var, boolean multiValues) {
//        for (String[] varData : varsData) {
//            int dataPos = 0;
//            String curVar = var;
//            if (curVar == null) {
//                curVar = varData[0];
//                dataPos = 1;
//            }
//            addCtxVarUpdate(builder, curVar, varData[dataPos], multiValues, 
//                    Arrays.copyOfRange(varData, dataPos + 1, varData.length));
//        }
//    }
    protected void addCtxVarUpdates(SyntagmeType.Builder builder, 
            String[][][] varsData, String var, CtxVarValueType valuesType) {
        for (String[][] varData : varsData) {
            String curVar = var;
            String[] curValues = varData[0];
            if (curVar == null) {
                curVar = varData[0][0];
                curValues = Arrays.copyOfRange(varData[0], 1, varData[0].length);
            }
            addCtxVarUpdate(builder, curVar, curValues, valuesType,
                varData.length > 1 ? varData[1] : null,
                varData.length > 2 ? varData[2] : null,
                varData.length > 3 ? varData[3] : null);
        }
    }

    protected void addCtxSampleUpdate(SyntagmeType.Builder builder, String[] samples,
            String[] origDefFlags, String[] origReplFlags, 
            String[] origDefFlagsOff, String[] origReplFlagsOff) {
        SyntagmeDefinitionUpdate.Builder ub = builder.update();
        if (origDefFlags != null && origDefFlags.length > 0) {
            ub.condition().origFlagsOn(origDefFlags);
        }
        if (origReplFlags != null && origReplFlags.length > 0) {
            ub.condition().origReplFlagsOn(origReplFlags);
        }
        if (origDefFlagsOff != null && origDefFlagsOff.length > 0) {
            ub.condition().origFlagsOff(origDefFlagsOff);
        }
        if (origReplFlagsOff != null && origReplFlagsOff.length > 0) {
            ub.condition().origReplFlagsOff(origReplFlagsOff);
        }
        ub.setCtxSamplesToAdd(samples);
    }
    protected void addCtxSampleUpdates(SyntagmeType.Builder builder, 
            String[][][] samplesData) {
        for (String[][] sampleData : samplesData) {
            addCtxSampleUpdate(builder, sampleData[0],
                sampleData.length > 1 ? sampleData[1] : null,
                sampleData.length > 2 ? sampleData[2] : null,
                sampleData.length > 3 ? sampleData[3] : null,
                sampleData.length > 4 ? sampleData[4] : null);
        }
    }


    @Override
    public String normalizeSyntagmeText(SyntagmeDefinition sd) {
        String normalized = super.normalizeSyntagmeText(sd);
        
        String selectedFlag = sd.getSelectedAttributeFlag(ATTR_COMMON_KEEP_MAJ);
        if (selectedFlag != null) {
            Text text = new Text(sd.getType().getLanguage(), normalized);
            switch (selectedFlag) {
                case FLAG_COMMON_KEEP_MAJ_NONE:
                    normalized = text.getLcText();
                    break;
                case FLAG_COMMON_KEEP_MAJ_INSIDE:
                    // Enleve la majuscule du premier mot si mot ecrit avec
                    // juste une majuscule en premier mot
                    TextRange first = text.getFirstWord();
                    if (first != null && first.hasOnlyFirstUc()) {
                        normalized = text.transformFirstWordLetter(false);
                    }
                    break;
            }
        }
        return normalized;
    }
    
    
    @Override
    public void alterReplacementText(ReplaceContext ctx) {
        SyntagmeDefinition replacement = ctx.getReplacement();
        Text replacementText = replacement.getTextText();
        TextRange firstReplWord = replacementText.getFirstWord();        
        if (firstReplWord == null) {
            return;
        }
        
        
        if (!ctx.hasPrecedingWords()) {
            // Pas de mots avant : nouvelle phrase
            ctx.setFinalReplacementText(replacementText.transformFirstWordLetter(true));
            // Rien d'autre à modifier dans le remplacement ou dans les mots precedents
            return;
        }
        
        String normalizedFirstReplWord = 
                removeTextDiacritricalMarks(firstReplWord.getCharSequence())
                    .toLowerCase(getLocale());
//        System.out.println("1st word '" + normalizedFirstReplWord + "'");

        boolean needsElision = replacement.isFlagOn(FLAG_COMMON_ELISION_ON);
//        if (normalizedFirstReplWord.length() > 0) {
//            switch (normalizedFirstReplWord.charAt(0)) {
//                case 'a':
//                case 'e':
//                case 'i':
//                case 'o':
//                case 'u':
//                    needsElision = true;
//                    break;
//                case 'h':
//                    needsElision = !replacement.isFlagOn(FLAG_COMMON_HASPIRE);
//                    break;
//            }
//        }
        

        String lastWord = getNormalizedPrecedingWords(ctx, 1);
        String lastTwoWords = getNormalizedPrecedingWords(ctx, 2);
        
//        System.out.println("prev word '" + lastWord + "'");
//        System.out.println("prev 2 words '" + lastTwoWords + "'");
        boolean ctxEpi = replacement.isFlagOn(FLAG_NOM_CTX_GENRE_EPICENE);
        boolean ctxFem = replacement.isFlagOn(FLAG_NOM_CTX_GENRE_FEMININ);
        String result = null;
        int nbReplacePrecedingWords = 1;
        
        if (needsElision) {
            switch (lastWord) {
                case "le":
                case "la":
                case "de":
                case "ne":
                case "me":
                case "se":
                case "te":
                case "je":
                case "que":
                case "lorsque":
                case "puisque":
                    result = lastWord.substring(0, lastWord.length() - 1) + "'";
                    break;
                case "la·le":
                    result = "l'";
                    break;
                case "ma":
                case "ta":
                case "sa":
                    if (replacement.isA(TYPE_NOM) && ctxFem) {
                        result = lastWord.substring(0, lastWord.length() - 1) + "on ";
                    }
                    break;
            }
            
            if (result == null && replacement.isA(TYPE_NOM)) {
                switch (lastWord) {
                    case "du":
                        result = "de l'";
                        break;
                    case "au":
                        result = "à l'";
                        break;
                    case "ce":
                        result = "cet ";
                        break;
                }
            }
        } else {
            // de l' / à l' dans le texte precedent
            if (replacement.isA(TYPE_NOM)) {

                switch (lastTwoWords) {
                    case "de l":
                        if (ctxEpi) {
                            result = "du·de la ";
                        } else {
                            result = "du ";
                        }
                        nbReplacePrecedingWords = 2;
                        break;
                    case "à l":
                        if (ctxEpi) {
                            result = "à la·le ";
                        } else {
                            result = "au ";
                        }
                        nbReplacePrecedingWords = 2;
                        break;
                }
            }
            
            // de/à (d'appartenance) dans le texte precedent et le dans le remplacement
            if (result == null && replacement.isA(TYPE_SNOMINAL) 
                    && "le".equals(normalizedFirstReplWord)) {
                switch (lastWord) {
                    case "de":
                        result = "du ";
                        break;
                    case "à":
                        result = "au ";
                        break;
                }
                // Enleve le premier mot ("le") si cas trouvé
                if (result != null) {
                    ctx.setFinalReplacementText(firstReplWord.getTextAfter().toString());
                }
            }
            if (result == null && replacement.isA(TYPE_SNOMINAL) 
                    && "les".equals(normalizedFirstReplWord)) {
                switch (lastWord) {
                    case "de":
                        result = "des ";
                        break;
                    case "à":
                        result = "aux ";
                        break;
                }
                if (result != null) {
                    ctx.setFinalReplacementText(firstReplWord.getTextAfter().toString());
                }
            }
            
            if (result == null) {
                // Le reste
                switch (lastWord) {
                    case "l":
                        result = "le ";
                        if (replacement.isA(TYPE_NOM)) {
                            if (ctxFem) {
                                result = "la ";
                            } else if (ctxEpi) {
                                result = "la·le ";
                            }
                        }
                        break;
                    case "d":
                    case "n":
                    case "m":
                    case "s":
                    case "t":
                    case "j":
                    case "qu":
                    case "lorsqu":
                    case "puisqu":
                        result = lastWord + "e ";
                        break;
                    case "mon":
                    case "ton":
                    case "son":
                        if (replacement.isA(TYPE_NOM) && ctxFem) {
                            result = lastWord.substring(0, lastWord.length() - 2) + "a ";
                        }
                        break;
                    case "cet":
                        if (replacement.isA(TYPE_NOM) && !ctxFem) {
                            result = "ce ";
                        }
                        break;
                }
            }
        }
        
        if (result != null) {
//            System.out.println("result '" + result + "' / '" + ctx.getFinalReplacementText() + "'");
            doReplacePrecedingText(ctx, nbReplacePrecedingWords, result);
        }
    }
    
    
    

//    @Override
//    protected void addSyntagmeMainDescription(SyntagmeDescriptionCreationCtx ctx) {
//        ctx.setFlagTextSeparator(" ou ");
//        
//        SyntagmeDefinitionAbstract sd = ctx.getSd();
//        String sdTypeCode = sd.getType().getCode();
//
//        switch (sdTypeCode) {
//            case TYPE_NOM:
//                addSyntagmeDescriptionText(ctx, "un");
//                addSyntagmeDescriptionType(ctx, "nom commun");
//                break;
//            case TYPE_SNOMINAL:
//                addSyntagmeDescriptionText(ctx, "un");
//                addSyntagmeDescriptionType(ctx, "syntagme nominal");
//                break;
//            case TYPE_VERBE:
//                addSyntagmeDescriptionText(ctx, "un");
//                addSyntagmeDescriptionType(ctx, "verbe");
//                break;
//            case TYPE_SVERBAL:
//                addSyntagmeDescriptionText(ctx, "un");
//                addSyntagmeDescriptionType(ctx, "syntagme verbal");
//                break;
//            case TYPE_COMPCIRC:
//                addSyntagmeDescriptionText(ctx, "un");
//                addSyntagmeDescriptionType(ctx, "complément circonstanciel");
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported type");
//        }
//        
//        
//        String curAttrGroup = "main";
//        SyntagmeFlag flagSGenre;
//        SyntagmeFlag flagSNombre;
//        SyntagmeFlag flagSPers;
//        switch (sdTypeCode) {
//            case TYPE_NOM:
//            case TYPE_SNOMINAL:
//                
//                Set<SyntagmeFlag> flagsGenre = ctx.getSpecificFlags(ATTR_NOM_GENRE);
//                Set<SyntagmeFlag> flagsNombre = ctx.getSpecificFlags(ATTR_NOM_NOMBRE);
//                if (flagsGenre.size() == 1 && flagsNombre.size() == 1) {
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, flagsGenre);
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, flagsNombre);
//                } else {
//                    ctx.setFlagLabel(FLAG_NOM_NOMBRE_SINGULIER, "au ", "singulier");
//                    ctx.setFlagLabel(FLAG_NOM_NOMBRE_PLURIEL, "au ", "pluriel");
//
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, flagsGenre);
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, flagsNombre);
//                }
//                addSyntagmeDescriptionAttrFlags(ctx, "type", "de type ", ctx.getSpecificFlags(ATTR_NOM_TYPE));
//                addSyntagmeDescriptionAttrFlags(ctx, "compl", null, ctx.getSpecificFlags(ATTR_NOM_COMPLEMENT));
//                break;
//                
//            case TYPE_VERBE:
//            case TYPE_SVERBAL:
//                
//                addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, ctx.getSpecificFlags(ATTR_VERBE_TYPE));
//                
//                ctx.setFlagLabel(FLAG_VERBE_FORME_FUTUR, "au ", "futur");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_IMPARFAIT, "à l'", "imparfait");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_IMPERATIF, "à l'", "impératif");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_INFINITF, "à l'", "infinitif");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_PARTICIPEPASSE, "au ", "paricipe passé");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_PARTICIPEPRESENT, "au ", "paricipe présent");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_PASSESIMPLE, "au ", "passé simple");
//                ctx.setFlagLabel(FLAG_VERBE_FORME_PRESENT, "au ", "présent");
//
//                SyntagmeFlag flagForme = ctx.getSpecificFlag(ATTR_VERBE_FORME);
//                Set<SyntagmeFlag> flagsForme = ctx.getSpecificFlags(ATTR_VERBE_FORME);
//                Set<SyntagmeFlag> flagsConjNombre = ctx.getSpecificFlags(ATTR_VERBE_CONJ_NOMBRE);
//                Set<SyntagmeFlag> flagsConjGenre = ctx.getSpecificFlags(ATTR_VERBE_CONJ_GENRE);
//                Set<SyntagmeFlag> flagsConjPersonne = ctx.getSpecificFlags(ATTR_VERBE_CONJ_PERSONNE);
//                if (sdTypeCode.equals(TYPE_VERBE)) {
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, flagsForme);
//                } else {
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, "avec un verbe ", flagsForme);
//                }
//                if (flagForme != null && FLAG_VERBE_FORME_PARTICIPEPASSE.equals(flagForme.getCode())) {
//                    ctx.setFlagLabel(FLAG_VERBE_CONJ_FEMININ, "au ", "féminin");
//                    ctx.setFlagLabel(FLAG_VERBE_CONJ_MASCULIN, "au ", "masculin");
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, "accordé ", flagsConjGenre);
//                    addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, flagsConjNombre);
//                } else {
//                    if (!flagsConjPersonne.isEmpty() && !flagsConjNombre.isEmpty()) {
//                        addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, "conjugué à la ", flagsConjPersonne);
//                        addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, "du ", flagsConjNombre);
//                    }
//                }
//
//                addSyntagmeDescriptionAttrFlags(ctx, "cc", null, ctx.getSpecificFlags(ATTR_VERBE_COMPCIRC));
//
//                curAttrGroup = "sujet";
//                flagSGenre = ctx.getSpecificFlag(ATTR_VERBE_SUJET_GENRE);
//                flagSNombre = ctx.getSpecificFlag(ATTR_VERBE_SUJET_NOMBRE);
//                flagSPers = ctx.getSpecificFlag(ATTR_COMPCIRC_SUJET_PERSONNE);
//                if (flagSGenre != null && FLAG_COMPCIRC_SUJET_GENRE_NONE.equals(flagSGenre.getCode())
//                        && flagSNombre != null && FLAG_COMPCIRC_SUJET_NOMBRE_NONE.equals(flagSNombre.getCode())
//                        && flagSPers != null && FLAG_COMPCIRC_SUJET_PERSONNE_NONE.equals(flagSPers.getCode())) {
//                    addSyntagmeDescriptionAttrCustom(ctx, curAttrGroup, null, 
//                            "sans référence au sujet", 
//                            "Ne contient rien qui indique le genre, le nombre ou la personne du sujet hormis la conjugaison du verbe");
//                }
//
//                break;
//                
//            case TYPE_COMPCIRC:
//                ctx.setFlagLabel(FLAG_COMPCIRC_TYPE_LIEU, "de ", "lieu");
//                ctx.setFlagLabel(FLAG_COMPCIRC_TYPE_TEMPS, "de ", "temps");
//                ctx.setFlagLabel(FLAG_COMPCIRC_TYPE_OTHER, "de ", "manière ou autres");
//
//                addSyntagmeDescriptionAttrFlags(ctx, curAttrGroup, null, ctx.getSpecificFlags(ATTR_COMPCIRC_TYPE));
//                
//                curAttrGroup = "sujet";
//                flagSGenre = ctx.getSpecificFlag(ATTR_COMPCIRC_SUJET_GENRE);
//                flagSNombre = ctx.getSpecificFlag(ATTR_COMPCIRC_SUJET_NOMBRE);
//                flagSPers = ctx.getSpecificFlag(ATTR_COMPCIRC_SUJET_PERSONNE);
//                if (flagSGenre != null && FLAG_COMPCIRC_SUJET_GENRE_NONE.equals(flagSGenre.getCode())
//                        && flagSNombre != null && FLAG_COMPCIRC_SUJET_NOMBRE_NONE.equals(flagSNombre.getCode())
//                        && flagSPers != null && FLAG_COMPCIRC_SUJET_PERSONNE_NONE.equals(flagSPers.getCode())) {
//                    addSyntagmeDescriptionAttrCustom(ctx, curAttrGroup, null, 
//                            "sans référence au sujet", 
//                            "Ne contient rien qui indique le genre, le nombre ou la personne du sujet");
//                }
//
//                break;
//        }
//    }

    @Override
    protected void addSDefReplacementHTMLInfo(SDefHTMLInfoCtx ctx) {
        /**
         * De type *animé*, *inanimé* ou *abstrait*
         * De type inanimé ou abstrait
         * De type animé
         * *Avec* ou *sans compléments*
         * *Avec compléments*
         * *Sans compléments*
         * Déterminant *défini* ou *indéfini*
         * 
         * Au présent ou à l'imparfait
         * Peut contenir les marques de négation
         * Sans les marques de négation
         * Avec ou sans complément circonstanciel
         * 
         */
        ctx.setFlagOptionSeparatorLast(" ou ");
        
        
        String sdType = ctx.getSdr().getType().getCode();
        switch (sdType) {
            case TYPE_NOM:
            case TYPE_SNOMINAL:
                ctx.setFlagLabel(FLAG_NOM_TYPE_ANIME, "animé");
                ctx.setFlagLabel(FLAG_NOM_TYPE_INANIME, "inanimé");
                ctx.setFlagLabel(FLAG_NOM_TYPE_ABSTRAIT, "abstrait");
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_NOM_TYPE, "De type ", null);
                closeSDefHTMLInfoCurItem(ctx);
                
                if (TYPE_SNOMINAL.equals(sdType)) {
                    ctx.setFlagLabel(FLAG_SNOMINAL_DETERMINANT_UNDEF, "indéfini");
                    ctx.setFlagLabel(FLAG_SNOMINAL_DETERMINANT_DEF, "défini");
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_SNOMINAL_DETERMINANT, "Déterminant ", null);
                    closeSDefHTMLInfoCurItem(ctx);
                }

                ctx.setFlagLabel(FLAG_NOM_COMPL_COMPL, "avec compléments");
                ctx.setFlagLabel(FLAG_NOM_COMPL_NONE, "sans compléments");
                if (ctx.areAllFlagsOn(FLAG_NOM_COMPL_NONE, FLAG_NOM_COMPL_COMPL)) {
                    ctx.setFlagLabel(FLAG_NOM_COMPL_COMPL, "avec");
                }
                addSDefSelectedFlagsHTMLInfo(ctx, null, null, FLAG_NOM_COMPL_COMPL, FLAG_NOM_COMPL_NONE);
                closeSDefHTMLInfoCurItem(ctx);
                break;
                
            case TYPE_VERBE:
                ctx.setFlagLabel(FLAG_VERBE_FORME_INFINITIF, "à l'", "infinitif", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_PRESENT, "au ", "présent", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_PASSECOMPOSE, "au ", "passé composé", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_PARTICIPEPASSE, "au ", "participe passé", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_IMPARFAIT, "à l'", "imparfait", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_PQPARFAIT, "au ", "plus-que-parfait", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_FUTURANT, "au ", "futur antérieur", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_PARTICIPEPRESENT, "au ", "participe présent", null);
                ctx.setFlagLabel(FLAG_VERBE_FORME_IMPERATIF, "à l'", "impératif", null);
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_FORME, null, null);
                closeSDefHTMLInfoCurItem(ctx);

                if (ctx.areAllFlagsOn(FLAG_VERBE_NEGATION_WITH, FLAG_VERBE_NEGATION_WITHOUT)) {
                    ctx.setFlagLabel(FLAG_VERBE_NEGATION_WITH, "peut ", "contenir les marques de négation", null);
                    addSDefSelectedFlagsHTMLInfo(ctx, null, null, FLAG_VERBE_NEGATION_WITH);
                } else if (ctx.areAllFlagsOn(FLAG_VERBE_NEGATION_WITH)) {
                    ctx.setFlagLabel(FLAG_VERBE_NEGATION_WITH, "doit ", "contenir les marques de négation", null);
                    addSDefSelectedFlagsHTMLInfo(ctx, null, null, FLAG_VERBE_NEGATION_WITH);
                } else {
                    ctx.setFlagLabel(FLAG_VERBE_NEGATION_WITHOUT, "sans ", "marques de négation", null);
                    addSDefSelectedFlagsHTMLInfo(ctx, null, null, FLAG_VERBE_NEGATION_WITHOUT);
                }
                closeSDefHTMLInfoCurItem(ctx);
                
                ctx.setFlagLabel(FLAG_VERBE_CC_WITH, "avec compléments circonstanciels");
                ctx.setFlagLabel(FLAG_VERBE_CC_WITHOUT, "sans compléments circonstanciels");
                if (ctx.areAllFlagsOn(FLAG_VERBE_CC_WITH, FLAG_VERBE_CC_WITHOUT)) {
                    ctx.setFlagLabel(FLAG_VERBE_CC_WITH, "avec");
                }
                addSDefSelectedFlagsHTMLInfo(ctx, null, null, FLAG_VERBE_CC_WITH, FLAG_VERBE_CC_WITHOUT);
                closeSDefHTMLInfoCurItem(ctx);
                break;
            
            case TYPE_COMPCIRC:
                ctx.setFlagLabel(FLAG_COMPCIRC_TYPE_LIEU, "de lieu");
                ctx.setFlagLabel(FLAG_COMPCIRC_TYPE_TEMPS, "de temps");
                ctx.setFlagLabel(FLAG_COMPCIRC_TYPE_OTHER, "de manière/autres");
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_COMPCIRC_TYPE, null, null);
                closeSDefHTMLInfoCurItem(ctx);
                break;
                
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    @Override
    protected void addSDefContextHTMLInfo(SDefHTMLInfoCtx ctx) {
        /**
         * Indications genre/nombre : aucunes
         * Indications genre/nombre : masculin singulier
         * Indications genre/nombre : pluriel
         * Indications genre/nombre : épicene pluriel
         * Nom introduit par article indef et article def
         * Nom introduit par pronom possessif (voir référent potentiel)
         * Suivi de compléments
         * Non suivi de compléments
         * Pas de référent potentiel
         * Référent potentiel : 1ere p du pluriel
         * Référents potentiels : 1ere personne du singulier, 3ème personne du singulier/féminin/inanimé
         * 
         * Sujet : 2eme personne singulier, masculin
         * Sujet : animé féminin pluriel
         * Sujet : inanimé pluriel
         * Pronom réfléchi en amont
         * Suivi d'un syntagme nominal inanimé
         * COD pronom en amont : 2ème personne du pluriel
         * COD pronom en amont : 3ème personne du singulier animé
         * Contexte participe passé : auxiliaire être (passif)
         * Autres référents potentiels : 1ere personne du singulier, 3ème personne du singulier/féminin/inanimé
         */
        
        ctx.setFlagOptionSeparatorLast(" et ");
        
        String ctxRefTitle = "Référents potentiels";
        
        
        String sdType = ctx.getSd().getType().getCode();
        switch (sdType) {
            case TYPE_NOM:
            case TYPE_SNOMINAL:
                ctx.setFlagLabel(FLAG_NOM_CTX_NOMBRE_UNKNOWN, "aucune");
                ctx.setFlagLabel(FLAG_NOM_CTX_NOMBRE_SINGULIER, "singulier");
                ctx.setFlagLabel(FLAG_NOM_CTX_NOMBRE_PLURIEL, "pluriel");
                ctx.setFlagLabel(FLAG_NOM_CTX_GENRE_UNKNOWN, "aucune");
                ctx.setFlagLabel(FLAG_NOM_CTX_GENRE_MASCULIN, "masculin");
                ctx.setFlagLabel(FLAG_NOM_CTX_GENRE_FEMININ, "féminin");
                ctx.setFlagLabel(FLAG_NOM_CTX_GENRE_EPICENE, "épicène");
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_NOM_CTX_GENRE, "Indications genre : ", null);
                closeSDefHTMLInfoCurItem(ctx);
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_NOM_CTX_NOMBRE, "Indications nombre : ", null);
                closeSDefHTMLInfoCurItem(ctx);
                
                if (TYPE_NOM.equals(sdType)) {
                    ctx.setFlagLabel(FLAG_NOM_CTX_DETERMINANT_UNDEF, "un ", "déterminant indéfini", null);
                    ctx.setFlagLabel(FLAG_NOM_CTX_DETERMINANT_PART, "l'", "article partitif", null);
                    ctx.setFlagLabel(FLAG_NOM_CTX_DETERMINANT_ARTDEF, "un ", "article défini", null);
                    ctx.setFlagLabel(FLAG_NOM_CTX_DETERMINANT_POSS, "un ", "adjectif possessif", null);
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_NOM_CTX_DETERMINANT, "Nom introduit par ", null);
                    closeSDefHTMLInfoCurItem(ctx);
                }

                ctx.setFlagLabel(FLAG_NOM_CTX_COMPL_COMPL, "suivi de compléments");
                ctx.setFlagLabel(FLAG_NOM_CTX_COMPL_NONE, "non suivi de compléments");
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_NOM_CTX_COMPL, null, null);
                closeSDefHTMLInfoCurItem(ctx);
                break;
                
            case TYPE_VERBE:
                appendSDefHTMLInfoCurItemText(ctx, "Sujet : ");

                ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_G_F, "féminin");
                ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_G_M, "masculin");
                
                String sujet3PNombre = null;
                if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_SUJET_PERS_3S)) {
                    sujet3PNombre = " singulier";
                } else if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_SUJET_PERS_3P)) {
                    sujet3PNombre = " pluriel";
                }
                if (sujet3PNombre == null) {
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_PERS_1S, "1ère personne du singulier");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_PERS_2S, "2ème personne du singulier");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_PERS_3SM, "3ème personne indéfini (\"on\")");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_PERS_1P, "1ère personne du pluriel");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_PERS_2P, "2ème personne du pluriel");
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_SUJET_PERS, null, ", ");
                } else {
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_TYPE_A, "animé");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_TYPE_I, "inanimé");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_SUJET_TYPE_ABS, "abstrait");
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_SUJET_TYPE, null, " ");
                }
                if (!ctx.getSd().isFlagOn(FLAG_VERBE_CTX_SUJET_G_0)) {
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_SUJET_G, null, null);
                }
                if (sujet3PNombre != null) {
                    appendSDefHTMLInfoCurItemText(ctx, sujet3PNombre);
                }
                closeSDefHTMLInfoCurItem(ctx);
                
                if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_PREFLECHI_YES)) {
                    ctx.setFlagLabel(FLAG_VERBE_CTX_PREFLECHI_YES, "Pronom réflechi dans le contexte");
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_PREFLECHI, null, null);
                    closeSDefHTMLInfoCurItem(ctx);
                }
                
                ctx.setFlagLabel(FLAG_VERBE_CTX_COD_NONE, "pas de COD dans le contexte");
                ctx.setFlagLabel(FLAG_VERBE_CTX_COD_AFTER, "suivi d'un COD");
                ctx.setFlagLabel(FLAG_VERBE_CTX_COD_BEFORE, "COD pronom en amont");
                addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_COD, null, null);

                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_GENRE_F, "féminin");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_GENRE_M, "masculin");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_TYPE_ANIME, "animé");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_TYPE_INANIME, "inanimé");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT, "abstrait");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_PERS_1S, "1ère personne du singulier");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_PERS_2S, "2ème personne du singulier");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_PERS_3S, "3ème personne du singulier");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_PERS_1P, "1ère personne du pluriel");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_PERS_2P, "2ème personne du pluriel");
                ctx.setFlagLabel(FLAG_VERBE_CTX_CODINFO_PERS_3P, "3ème personne du pluriel");
                if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_COD_BEFORE)) {
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_CODINFO_PERS, " à la ", null);
                    if (!ctx.getSd().isFlagOn(FLAG_VERBE_CTX_CODINFO_GENRE_0)) {
                        addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_CODINFO_GENRE, ", ", null);
                    }
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_CODINFO_TYPE, ", de type ", null);
                } else if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_COD_AFTER)) {
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_CODINFO_TYPE, " de type ", null);
                }
                closeSDefHTMLInfoCurItem(ctx);
                
                String ctxPPasseAttrTitle = "Contexte participe passé : ";
                if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR)) {
                    ctx.setFlagLabel(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, "temps composé voix active (aux. avoir)");
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_PPASSE_AUX, ctxPPasseAttrTitle, null);
                } else if (ctx.getSd().isFlagOn(FLAG_VERBE_CTX_PPASSE_AUX_ETRE)) {
                    ctx.setFlagLabel(FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE, "temps composé voix active (aux. être)");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE, "passif non agentif");
                    ctx.setFlagLabel(FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVECDA, "passif agentif");
                    addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_VERBE_CTX_PPASSE_AUXE, ctxPPasseAttrTitle, null);
                }
                closeSDefHTMLInfoCurItem(ctx);
                
                ctxRefTitle = "Autres référents potentiels";
                break;
            
            case TYPE_COMPCIRC:
                break;
            
            default:
                throw new IllegalArgumentException("Unknown type");
        }
        
        if (ctxRefTitle != null) {
            SyntagmeAttribute attr = ctx.getAttribute(ATTR_COMMON_CTX_REF);
            addSDefAttrHTMLInfo(ctx, attr.getCode(), ctxRefTitle);
            appendSDefHTMLInfoCurItemText(ctx, " : ");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF_NONE, "aucun");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF_1PS, "1e pers. singulier");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF_2PS, "2e pers. singulier");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF_1PP, "1e pers. pluriel");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF_2PP, "2e pers. pluriel");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF_3P, "3e pers.");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF3P_G_F, "féminin");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF3P_G_M, "masculin");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF3P_NB_S, "singulier");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF3P_NB_P, "pluriel");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF3P_T_A, "animé");
            ctx.setFlagLabel(FLAG_COMMON_CTX_REF3P_T_I, "inanimé");
            boolean first = true;
            for (SyntagmeFlag flag : attr.getFlags()) {
                String flagCode = flag.getCode();
                if (ctx.getSd().isFlagOn(flagCode)) {
                    if (first) {
                        first = false;
                    } else {
                        appendSDefHTMLInfoCurItemText(ctx, ctx.getFlagOptionSeparator());
                    }
                    addSDefFlagHTMLInfo(ctx, flagCode);
                    
                    if (FLAG_COMMON_CTX_REF_3P.equals(flagCode)) {
                        ctx.setNextSeparator("/");
                        addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_COMMON_CTX_REF3P_NB, null, "/");
                        if (!ctx.getSd().isFlagOn(FLAG_COMMON_CTX_REF3P_G_0)) {
                            addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_COMMON_CTX_REF3P_G, null, "/");
                        }
                        addSDefSelectedAttrFlagsHTMLInfo(ctx, ATTR_COMMON_CTX_REF3P_T, null, "/");
                        ctx.setNextSeparator(null);
                    }

                }
            }
            if (first) {
                addSDefFlagHTMLInfo(ctx, FLAG_COMMON_CTX_REF_NONE);
            }
            closeSDefHTMLInfoCurItem(ctx);
        }
    }

}
