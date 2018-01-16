package de.jambonna.lolpapers.core.model.lang.fr;

import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.Languages;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeFlagState;
import de.jambonna.lolpapers.core.model.lang.SyntagmeReplacementDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeType;
import de.jambonna.lolpapers.core.model.text.ReplaceContext;
import de.jambonna.lolpapers.core.model.text.Sentence;
import de.jambonna.lolpapers.core.model.text.TextRange;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.jambonna.lolpapers.core.model.lang.fr.FrLanguage.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import org.junit.Ignore;

public class FrLanguageTest {
    
    private static Language frLang;

    @BeforeClass
    public static void setUpClass() throws Exception {
        frLang = Languages.getInstance().getFr();
    }
    
    public SyntagmeDefinition getNewSDNom() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(TYPE_NOM));
        return sd;
    }
    public SyntagmeDefinition getNewSDNom(String text) {
        SyntagmeDefinition sd = getNewSDNom();
        sd.setText(text);
        return sd;
    }
    public SyntagmeDefinition getNewSDNom(String text, String... flags) {
        SyntagmeDefinition sd = getNewSDNom(text);
        sd.resetChosenFlags(Arrays.asList(flags));
        sd.update();
        return sd;
    }
    public SyntagmeDefinition getNewSDSNominal() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(TYPE_SNOMINAL));
        return sd;
    }
    public SyntagmeDefinition getNewSDSNominal(String text, String... flags) {
        SyntagmeDefinition sd = getNewSDSNominal();
        sd.resetChosenFlags(Arrays.asList(flags));
        sd.setText(text);
        return sd;
    }
    public SyntagmeDefinition getNewSDVerbe() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(TYPE_VERBE));
        return sd;
    }
    public SyntagmeDefinition getNewSDVerbe(String text, String... flags) {
        SyntagmeDefinition sd = getNewSDVerbe();
        sd.setText(text);
        sd.resetChosenFlags(Arrays.asList(flags));
        sd.update();
        return sd;
    }
    public SyntagmeDefinition getNewSDCompCirc() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(TYPE_COMPCIRC));
        return sd;
    }

    @Test
    public void testSDefNom() {
        SyntagmeDefinition sd = getNewSDNom();
        
        // Pas dispo sans texte
        assertFalse(sd.isAvailable());
        sd.update();
        assertFalse(sd.isAvailable());
        sd.setText("zzz");
        assertTrue(sd.isAvailable());
        
        String cnb;
        String cg;
        String cdet;
        String cref;
        String cref3pn;
        String cref3pg;
        String cref3pt;
        String ccompl;
        String dtype;
        String dcompl;
        String dphase;
        String dreu;
        String dkmj;
        String dha;
        String deli;
        String rtype;
        String rcompl;

        String defCnb       = "cnombre: cns_ cnp_ ";
        String defCg        = "cgenre: cg0_ cgf_ cgm_ cgx_ ";
        String defCdet      = "cdet: cdi_ cdpa_ cdd_ cdpo_ ";
        String defCref      = "cref: cr0+ cr1s_ cr2s_ cr3_ cr1p_ cr2p_ ";
        String defCref3pn   = "cref3pn: cr3pns# cr3pnp# ";
        String defCref3pg   = "cref3pg: cr3pg0# cr3pgf# cr3pgm# ";
        String defCref3pt   = "cref3pt: cr3pta# cr3pti# ";
        String defCcompl    = "ccompl: cc0_ cc1_ ";
        String defDphase    = "phase: def+ repl_ ";
        String defDreu      = "reu: reu0_ reu1+ ";
        String defDtype     = "type: ta_ ti_ tabs_ ";
        String defDcompl    = "compl: c0+ c1# ";
        String defDkmj      = "kmj: kmj# kimj# nomj# ";
        String defDha       = "ha: ha# hm# ";
        String defDeli      = "eli: eli0+ eli1_ ";
        String defRtype     = "type: ta_ ti_ tabs_ ";
        String defRcompl    = "compl: c0+ c1_ ";
        
        cnb     = defCnb;
        cg      = defCg;
        cdet    = defCdet;
        cref    = defCref;
        cref3pn = defCref3pn;
        cref3pg = defCref3pg;
        cref3pt = defCref3pt;
        ccompl  = defCcompl;
        dphase  = defDphase;
        dreu    = defDreu;
        dtype   = defDtype;
        dcompl  = defDcompl;
        dkmj    = defDkmj;
        dha     = defDha;
        deli    = defDeli;
        rtype   = defRtype;
        rcompl  = defRcompl;

        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        // Positionner un texte à un mot ne change rien
        sd.setText("blabla");
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        // Positionne un texte à plusieurs mots active le choix "avec complement"
        sd.setText("blabla blabla");
        dcompl = "compl: c0_ c1_ ";
        rcompl = "compl: c0+ c1+ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        // Plusieurs mots texte non normalizé
        sd.setText(" \" blabla,  blabla \" ");
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        // Pluriel positionne genre à inconnu par défaut
        sd.selectFlag(FLAG_NOM_CTX_NOMBRE_SINGULIER);
        cnb = "cnombre= cns* cnp_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_CTX_NOMBRE_PLURIEL);
        cnb = "cnombre= cns_ cnp* ";
        cg = "cgenre: cg0+ cgf_ cgm_ cgx_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        // Epicene positionne type
        sd.selectFlag(FLAG_NOM_CTX_GENRE_FEMININ);
        cg = "cgenre= cg0_ cgf* cgm_ cgx_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_CTX_GENRE_EPICENE);
        cg = "cgenre= cg0_ cgf_ cgm_ cgx* ";
        dtype = "type: ta+ ti_ tabs_ ";
        rtype = "type: ta+ ti_ tabs_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        
        // CRef choix unique pour les noms
        sd.selectFlag(FLAG_COMMON_CTX_REF_1PS);
        cref = "cref= cr0_ cr1s* cr2s_ cr3_ cr1p_ cr2p_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        
        sd.selectFlag(FLAG_COMMON_CTX_REF_2PP);
        cref = "cref= cr0_ cr1s_ cr2s_ cr3_ cr1p_ cr2p* ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        // Determinant choix multiple
        sd.selectFlag(FLAG_NOM_CTX_DETERMINANT_UNDEF);
        cdet = "cdet= cdi* cdpa_ cdd_ cdpo_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        cdet = "cdet= cdi* cdpa_ cdd* cdpo_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        
        // Determinant possessif enleve referent aucun
        sd.selectFlag(FLAG_NOM_CTX_DETERMINANT_POSS);
        cdet = "cdet= cdi* cdpa_ cdd* cdpo* ";
        cref = "cref= cr0# cr1s_ cr2s_ cr3_ cr1p_ cr2p* ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.unselectFlag(FLAG_NOM_CTX_DETERMINANT_UNDEF);
        sd.unselectFlag(FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        cdet = "cdet= cdi_ cdpa_ cdd_ cdpo* ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        
        // CRef 3P active les attributs liés
        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        cref = "cref= cr0# cr1s_ cr2s_ cr3* cr1p_ cr2p_ ";
        cref3pn = "cref3pn: cr3pns+ cr3pnp_ ";
        cref3pg = "cref3pg: cr3pg0+ cr3pgf_ cr3pgm_ ";
        cref3pt = "cref3pt: cr3pta+ cr3pti_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        // Type à partir de la def
        sd.selectFlag(FLAG_NOM_TYPE_INANIME);
        dtype = "type= ta_ ti* tabs_ ";
        rtype = "type: ta_ ti+ tabs_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_TYPE_ANIME);
        dtype = "type= ta* ti_ tabs_ ";
        rtype = "type: ta+ ti_ tabs_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_TYPE_ABSTRAIT);
        dtype = "type= ta_ ti_ tabs* ";
        rtype = "type: ta_ ti_ tabs+ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        // Type de complement à partir de la def
        sd.selectFlag(FLAG_NOM_COMPL_NONE);
        dcompl = "compl= c0* c1_ ";
        rcompl = "compl: c0+ c1_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_COMPL_COMPL);
        dcompl = "compl= c0_ c1* ";
        rcompl = "compl: c0_ c1+ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        
        // Type de complément à partir du contexte : écrase la valeur par défaut de la def
        sd.selectFlag(FLAG_NOM_CTX_COMPL_NONE);
        ccompl = "ccompl= cc0* cc1_ ";
        rcompl = "compl: c0+ c1+ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_CTX_COMPL_COMPL);
        ccompl = "ccompl= cc0_ cc1* ";
        rcompl = "compl: c0+ c1_ ";
        assertEquals(cnb + cg + cdet + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rcompl,  sd.toString());

        
    }
    

    
    @Test
    public void testSDefNomPrecText() {
        SyntagmeDefinition sd = getNewSDNom();
        sd.update();
        assertNull(sd.getFlagsToSelect());
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa un", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        
        // Ne change pas les flags directement
//        System.out.println(sd);
        assertEquals("cnombre: cns_ cnp_ cgenre: cg0_ cgf_ cgm_ cgx_ cdet: cdi_ cdpa_ cdd_ cdpo_ cref: cr0+ cr1s_ cr2s_ cr3_ cr1p_ cr2p_ cref3pn: cr3pns# cr3pnp# cref3pg: cr3pg0# cr3pgf# cr3pgm# cref3pt: cr3pta# cr3pti# ccompl: cc0_ cc1_ type: ta_ ti_ tabs_ compl: c0+ c1# phase: def+ repl_ reu: reu0_ reu1+ kmj: kmj# kimj# nomj# ha: ha# hm# eli: eli0+ eli1_  [R] type: ta_ ti_ tabs_ compl: c0+ c1_ ", sd.toString());
        
        // Chaque update reset le resultat
        sd.setPrecedingText("aaa aaa aaa");
        assertNull(sd.getFlagsToSelect());
        sd.setPrecedingText(" ");
        assertNull(sd.getFlagsToSelect());
        // Majuscules / caracteres parasites ne changent rien
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa Un", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa UN, :  ", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        // Que le mot qu'il faut en texte précédent
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "Un", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa une", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa un·e", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa des", FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa l'", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        // Check pas de pb avec cas "de l'"
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "l'", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa le", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa la", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa la·le", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa aaa les", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa de l'", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa du", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa de la", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa du·de la", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa mon", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa ma", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa mes", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa ton", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa ta", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa tes", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa son", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa sa", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa ses", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa leur", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa leurs", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa notre", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa nos", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "aaa votre", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "vos vos", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        

    }
    
    public void assertSDFlagsToSelectWithPrecedingTextEquals(SyntagmeDefinition sd, String precedingText, String... flags) {
        sd.setPrecedingText(precedingText);
        assertSDFlagsToSelectEquals(sd, flags);
    }
    public void assertSDFlagsToSelectEquals(SyntagmeDefinition sd, String... flags) {
        if (flags.length > 0) {
            assertEquals(new HashSet<>(Arrays.asList(flags)), sd.getFlagsToSelect());        
        } else {
            assertNull(sd.getFlagsToSelect());
        }
    }
    
    @Test
    public void testNomCtxPrefix() {
        SyntagmeDefinition sd;
        
        // Note : on ne va pas gérer les cas où les flags de contexte obligatoires
        // ne sont pas positionnés (ex : genre)
        
        sd = getNewSDNomWithOrigDef(null, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN,
                FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_INANIME);
        // Il y a les differentes déclinaisons si pas de genre donné
        // Note : cela ne devrait pas arriver en pratique car si il y avait un 
        // déterminant dans le texte d'origine, la personne qui fait la 
        // définition aurait pu voir le genre (sauf si élision)
        assertSDCtxPrefixEquals("un  / une  / un·e ", sd);
        
        // Sans le type animé : enlevé la version épicene dans les choix si genre inconnu
        sd.getOrigDefinition().getReplacementDefinition().unselectFlag(FLAG_NOM_TYPE_ANIME);
        sd.update();
        assertSDCtxPrefixEquals("un  / une ", sd);
        
        assertSDNomCtxPrefixEquals("un ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("une ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("un·e ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        
        assertSDNomCtxPrefixEquals("des ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertSDNomCtxPrefixEquals("des ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDNomCtxPrefixEquals("des ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("des ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("des ", null, FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_EPICENE);

        assertSDNomCtxPrefixEquals("du  / de la ", null, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDNomCtxPrefixEquals("du  / de la  / du·de la ", null, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_TYPE_ANIME);
        assertSDNomCtxPrefixEquals("du ", null, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("de la ", null, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("du·de la ", null, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        assertSDNomCtxPrefixEquals("des ", null, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        
        // Consonne => pas d'élision 
        assertSDNomCtxPrefixEquals("du  / de la ", "texte", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        // Valeur possible en mode élidé sans genre
        assertSDNomCtxPrefixEquals("de l'", "âme", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDNomCtxPrefixEquals("de l'", "âme", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_TYPE_ANIME);
        // Memes valeurs quel que soit le genre
        assertSDNomCtxPrefixEquals("de l'", "âme", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("de l'", "âme", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("de l'", "âme", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        assertSDNomCtxPrefixEquals("des ", "âme", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_PLURIEL);

        // H pas considéré par défaut comme aspiré
        sd = getNewSDNomWithOrigDef("hache", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDCtxPrefixEquals("de l'", sd);
        sd.selectFlag(FLAG_COMMON_HASPIRE);
        assertSDCtxPrefixEquals("du  / de la ", sd);
        sd.getOrigDefinition().selectFlag(FLAG_NOM_CTX_GENRE_MASCULIN);
        sd.update();
        assertSDCtxPrefixEquals("du ", sd);
        sd.getOrigDefinition().selectFlag(FLAG_NOM_CTX_GENRE_FEMININ);
        sd.update();
        assertSDCtxPrefixEquals("de la ", sd);
        sd.getOrigDefinition().selectFlag(FLAG_NOM_CTX_GENRE_EPICENE);
        sd.update();
        assertSDCtxPrefixEquals("du·de la ", sd);
        
        // Pas de pb avec les caractères parasites / majuscules
        assertSDNomCtxPrefixEquals("de l'", " ( => \" Âme ! )", FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);

        
        assertSDNomCtxPrefixEquals("le  / la ", null, FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDNomCtxPrefixEquals("le  / la  / la·le ", null, FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_TYPE_ANIME);
        assertSDNomCtxPrefixEquals("le ", null, FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("la ", null, FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("la·le ", null, FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        assertSDNomCtxPrefixEquals("l'", "Huître", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN);
        assertSDNomCtxPrefixEquals("l'", "Huître", FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("les ", null, FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_NOMBRE_PLURIEL);

        // Plusieurs types de déterminants
        sd = getNewSDNomWithOrigDef(null, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_DETERMINANT_PART, FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        assertSDCtxPrefixEquals("du  / de la  / le  / la ", sd);
        sd.getOrigDefinition().selectFlag(FLAG_NOM_CTX_GENRE_FEMININ);
        sd.update();
        assertSDCtxPrefixEquals("de la  / la ", sd);
        
        assertSDNomCtxPrefixEquals("mon ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("mon ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("ma ", null,    FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("mes ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertSDNomCtxPrefixEquals("ton ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("ton ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("ta ", null,    FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("tes ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertSDNomCtxPrefixEquals("son ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("son ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("sa ", null,    FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("ses ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertSDNomCtxPrefixEquals("notre ", null, FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals("nos ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PP, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertSDNomCtxPrefixEquals("votre ", null, FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals("vos ", null,   FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PP, FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertSDNomCtxPrefixEquals("leur ", null,  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals("leurs ", null, FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_P, FLAG_NOM_CTX_NOMBRE_PLURIEL);

        // Mets certains determinants dans tous les cas si elision
        assertSDNomCtxPrefixEquals(null, null, FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals(null, "texte", FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals("mon ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_MASCULIN);
        assertSDNomCtxPrefixEquals("mon ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ);
        assertSDNomCtxPrefixEquals("mon ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_EPICENE);
        assertSDNomCtxPrefixEquals("mon ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_1PS, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals("ton ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_2PS, FLAG_NOM_CTX_NOMBRE_SINGULIER);
        assertSDNomCtxPrefixEquals("son ", "âme",  FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_NOM_CTX_NOMBRE_SINGULIER);

    }
    
    protected SyntagmeDefinition getOrigSD(SyntagmeType type, String... flags) {
        SyntagmeDefinition sd = new SyntagmeDefinition(type);
        List<String> dFlags = new ArrayList<>();
        List<String> rFlags = new ArrayList<>();
        for (String flag : flags) {
            if (sd.getAttributeByFlagCode(flag).isContext()) {
                dFlags.add(flag);
            } else {
                rFlags.add(flag);
            }
        }
        sd.resetChosenFlags(dFlags);
        sd.getReplacementDefinition().resetChosenFlags(rFlags);
        sd.update();
        return sd;
    }
    
    protected SyntagmeDefinition getNewSDNomWithOrigDef(String text, String... flags) {
        SyntagmeDefinition sd = getNewSDNom();
        sd.setOrigDefinition(getOrigSD(sd.getType(), flags));
        sd.setText(text);
        return sd;
    }
    
    protected void assertSDNomCtxPrefixEquals(String expected, String text, String... flags) {
        SyntagmeDefinition sd = getNewSDNomWithOrigDef(text, flags);
        assertSDCtxPrefixEquals(expected, sd);
    }
    protected SyntagmeDefinition assertSDCtxPrefixEquals(String expected, SyntagmeDefinition sd) {
//        System.out.println(sd.getOrigDefinition());
        assertEquals(expected, sd.getCtxPrefix());
        return sd;
    }
    
    @Test
    public void testNomCtxSamples() {
//        SyntagmeDefinition sd;
//        List<String> samples;
        long defaultSeed = 1234L;
//        String ref;
//        String refBefore;
//        String refAfter;
        
        assertSDNomCtxSamplesEqual(
                "un  / une  / un·e <xxx> a été analysé·e\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER,
                FLAG_NOM_CTX_DETERMINANT_UNDEF,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT);
        
        assertSDNomCtxSamplesEqual(
                "un  / une  / un·e <xxx> a été analysé·e\n" +
                "un  / une  / un·e <xxx> légendaire\n" +
                "un  / une  / un·e <xxx> qu'on avait un peu oublié·e\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT);
        
        assertSDNomCtxSamplesEqual(
                "des <xxx> ont été analysé·e·s\n" +
                "des <xxx> légendaires\n" +
                "des <xxx> qu'on avait un peu oublié·e·s\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_PLURIEL, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT);
        
        assertSDNomCtxSamplesEqual(
                "un  / une  / un·e <biscuit appéritif> est tombé·e par terre\n" +
                "un  / une  / un·e <biscuit appéritif> verdâtre\n" +
                "un  / une  / un·e <biscuit appéritif> qui vibre\n" +
                "un  / une  / un·e <biscuit appéritif> qui parle allemand\n" +
                "un  / une  / un·e <biscuit appéritif> qu'on avait un peu oublié·e\n",
                defaultSeed, "biscuit appéritif", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME);
        
        assertSDNomCtxSamplesEqual(
                "un  / une <chaussette> est tombé(e) par terre\n" +
                "un  / une <chaussette> en bronze\n" +
                "un  / une <chaussette> verdâtre\n" +
                "un  / une <chaussette> qui vibre\n" +
                "un  / une <chaussette> qu'on avait un peu oublié(e)\n",
                defaultSeed, "chaussette", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME);
        
        assertSDNomCtxSamplesEqual(
                "des <chaussettes> sont tombé(e)s par terre\n" +
                "des <chaussettes> en bronze\n" +
                "des <chaussettes> verdâtres\n" +
                "des <chaussettes> qui vibrent\n" +
                "des <chaussettes> qu'on avait un peu oublié(e)s\n",
                defaultSeed, "chaussettes", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_PLURIEL, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME);
        
        assertSDNomCtxSamplesEqual(
                "un  / une  / un·e <collègue> est décédé·e\n" +
                "un  / une  / un·e <collègue> expérimenté·e\n" +
                "un  / une  / un·e <collègue> qui parle allemand\n" +
                "un  / une  / un·e <collègue> qu'on avait un peu oublié·e\n",
                defaultSeed, "collègue", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_ANIME);
        
        assertSDNomCtxSamplesEqual(
                "l'<envie d'uriner> a été analysé(e)\n" +
                "l'<envie d'uriner> légendaire\n" +
                "l'<envie d'uriner> qu'on avait un peu oublié(e)\n",
                defaultSeed, "envie d'uriner", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_ABSTRAIT);
        
        
        assertSDNomCtxSamplesEqual(
                "le  / la  / la·le <choucroute de ma mère> est tombé·e par terre\n" +
                "je mange le  / la  / la·le <choucroute de ma mère>\n" +
                "j'embauche le  / la  / la·le <choucroute de ma mère> en CDI\n" +
                "le  / la  / la·le <choucroute de ma mère> verdâtre\n" +
                "le  / la  / la·le <choucroute de ma mère> qui vibre\n" +
                "le  / la  / la·le <choucroute de ma mère> qui parle allemand\n" +
                "le  / la  / la·le <choucroute de ma mère> qu'on avait un peu oublié·e\n",
                defaultSeed, "choucroute de ma mère", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_ARTDEF, FLAG_NOM_CTX_COMPL_COMPL, FLAG_COMMON_CTX_REF_1PS,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME);

        assertSDNomCtxSamplesEqual(
                "son <oreille droite> est tombée par terre\n" +
                "il·elle mange son <oreille droite>\n" +
                "son <oreille droite> en bronze\n" +
                "son <oreille droite> verdâtre\n" +
                "son <oreille droite> qui vibre\n" +
                "son <oreille droite> qu'on avait un peu oubliée\n",
                defaultSeed, "oreille droite", 
                FLAG_NOM_CTX_GENRE_FEMININ, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_DETERMINANT_POSS, FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_COMMON_CTX_REF_3P, FLAG_COMMON_CTX_REF3P_NB_S, FLAG_COMMON_CTX_REF3P_T_A, 
                FLAG_NOM_TYPE_INANIME);
    }
    
    protected void assertSDNomCtxSamplesEqual(String expected, long randomSeed, 
            String sdText, String... origSdFlags) {
        SyntagmeDefinition sd = getNewSDNomWithOrigDef(sdText, origSdFlags);
        List<String> samples = getSDCtxSamples(sd, randomSeed);
        assertSDCtxSamplesEquals(
                expected, 
                samples);
    }
    
//    protected void assertSDNomHasCtxSamples(String expected, 
//            Random r, String text, String... flags) {
//        SyntagmeDefinition sd = createSDNomForCtxTests(text, flags);
//        assertSDNomCtxSamplesEquals(expected, r, sd);
//    }
    
    protected List<String> getSDCtxSamples(SyntagmeDefinition sd, long randomSeed) {
//        System.out.println(sd);
//        System.out.println(sd.getOrigDefinition());
//        GsonBuilder gsonb = new GsonBuilder();
//        gsonb.setPrettyPrinting();
//        Gson gson = gsonb.create();
//        System.out.println(gson.toJson(sd.getCtxVars()));
//        System.out.println(sd.getCtxSamples());
        Random r = new Random(randomSeed);
        sd.setCtxRng(r);
        List<String> samples = sd.getFinalCtxSamples("<" + sd.getText() + ">");
//        System.out.println(samples);
        return samples;
    }
    protected void assertSDCtxSamplesEquals(String expected, Collection<String> samples) {
        StringBuilder sb = new StringBuilder();
        for (String sample : samples) {
            sb.append(sample);
            sb.append('\n');
        }
        System.out.println(sb.toString());
        assertEquals(expected, sb.toString());
    }
    protected void assertSDCtxSamplesContains(String sample, Collection<String> samples) {
        System.out.println("Contains \"" + sample + "\" ?");
        assertTrue(samples.contains(sample));
    }
    protected void assertSDCtxSamplesDoesNotContain(String sample, Collection<String> samples) {
        System.out.println("Does not contains \"" + sample + "\" ?");
        assertFalse(samples.contains(sample));
    }
    
    
    @Test
    public void testSDefSNominal() {
        SyntagmeDefinition sd = getNewSDSNominal();
        
        // Pas dispo sans texte
        sd.update();
        assertFalse(sd.isAvailable());
        sd.setText("zzz");
        assertTrue(sd.isAvailable());
        
        String cnb;
        String cg;
        String cref;
        String cref3pn;
        String cref3pg;
        String cref3pt;
        String ccompl;
        String dtype;
        String ddet;
        String dcompl;
        String dphase;
        String dreu;
        String dkmj;
        String dha;
        String deli;
        String rtype;
        String rdet;
        String rcompl;

        String defCnb       = "cnombre: cn0+ cns_ cnp_ ";
        String defCg        = "cgenre: cg0+ cgf_ cgm_ cgx_ ";
        String defCref      = "cref: cr1s_ cr2s_ cr3_ cr1p_ cr2p_ ";
        String defCref3pn   = "cref3pn: cr3pns# cr3pnp# ";
        String defCref3pg   = "cref3pg: cr3pg0# cr3pgf# cr3pgm# ";
        String defCref3pt   = "cref3pt: cr3pta# cr3pti# ";
        String defCcompl    = "ccompl: cc0_ cc1_ ";
        String defDtype     = "type: ta_ ti_ tabs_ ";
        String defDdet      = "det: di_ dd_ ";
        String defDcompl    = "compl: c0_ c1_ ";
        String defDphase    = "phase: def+ repl_ ";
        String defDreu      = "reu: reu0_ reu1+ ";
        String defDkmj      = "kmj: kmj# kimj# nomj# ";
        String defDha       = "ha: ha# hm# ";
        String defDeli      = "eli: eli0+ eli1_ ";
        String defRtype     = "type: ta_ ti_ tabs_ ";
        String defRdet      = "det: di+ dd+ ";
        String defRcompl    = "compl: c0+ c1+ ";
        
        cnb     = defCnb;
        cg      = defCg;
        cref    = defCref;
        cref3pn = defCref3pn;
        cref3pg = defCref3pg;
        cref3pt = defCref3pt;
        ccompl  = defCcompl;
        dtype   = defDtype;
        ddet    = defDdet;
        dcompl  = defDcompl;
        dphase  = defDphase;
        dreu    = defDreu;
        dkmj    = defDkmj;
        dha     = defDha;
        deli    = defDeli;
        rtype   = defRtype;
        rdet    = defRdet;
        rcompl  = defRcompl;

        
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        
        // Positionner un texte ne change rien
        sd.setText("blabla zerze rzer");
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.setText(null);
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        
        // Epicene positionne type
        sd.selectFlag(FLAG_NOM_CTX_GENRE_FEMININ);
        cg = "cgenre= cg0_ cgf* cgm_ cgx_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_CTX_GENRE_EPICENE);
        cg = "cgenre= cg0_ cgf_ cgm_ cgx* ";
        dtype = "type: ta+ ti_ tabs_ ";
        rtype = "type: ta+ ti_ tabs_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());

        
        // CRef choix multiple
        sd.selectFlag(FLAG_COMMON_CTX_REF_1PS);
        cref = "cref= cr1s* cr2s_ cr3_ cr1p_ cr2p_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        
        sd.selectFlag(FLAG_COMMON_CTX_REF_2PP);
        cref = "cref= cr1s* cr2s_ cr3_ cr1p_ cr2p* ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());

                
        // CRef 3P active les attributs liés
        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        cref = "cref= cr1s* cr2s_ cr3* cr1p_ cr2p* ";
        cref3pn = "cref3pn: cr3pns+ cr3pnp_ ";
        cref3pg = "cref3pg: cr3pg0+ cr3pgf_ cr3pgm_ ";
        cref3pt = "cref3pt: cr3pta+ cr3pti_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());

        // Type à partir de la def
        sd.selectFlag(FLAG_NOM_TYPE_INANIME);
        dtype = "type= ta_ ti* tabs_ ";
        rtype = "type: ta_ ti+ tabs_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_TYPE_ANIME);
        dtype = "type= ta* ti_ tabs_ ";
        rtype = "type: ta+ ti_ tabs_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_TYPE_ABSTRAIT);
        dtype = "type= ta_ ti_ tabs* ";
        rtype = "type: ta_ ti_ tabs+ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());

        // Determinant à partir de la def
        sd.selectFlag(FLAG_SNOMINAL_DETERMINANT_UNDEF);
        ddet = "det= di* dd_ ";
        rdet = "det: di+ dd_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.selectFlag(FLAG_SNOMINAL_DETERMINANT_DEF);
        ddet = "det= di_ dd* ";
        rdet = "det: di_ dd+ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        
        // Type de complement à partir de la def
        sd.selectFlag(FLAG_NOM_COMPL_NONE);
        dcompl = "compl= c0* c1_ ";
        rcompl = "compl: c0+ c1_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_COMPL_COMPL);
        dcompl = "compl= c0_ c1* ";
        rcompl = "compl: c0_ c1+ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        
        // Type de complément à partir du contexte : écrase la valeur par défaut de la def
        sd.selectFlag(FLAG_NOM_CTX_COMPL_NONE);
        ccompl = "ccompl= cc0* cc1_ ";
        rcompl = "compl: c0+ c1+ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());
        sd.selectFlag(FLAG_NOM_CTX_COMPL_COMPL);
        ccompl = "ccompl= cc0_ cc1* ";
        rcompl = "compl: c0+ c1_ ";
        assertEquals(cnb + cg + cref + cref3pn + cref3pg + cref3pt + ccompl + dtype + ddet + dcompl + dphase + dreu + dkmj + dha + deli + " [R] " + rtype + rdet + rcompl,  sd.toString());

        
    }
    
    @Test
    public void testSNomimnalCtxSamples() {
//        SyntagmeDefinition sd;
//        List<String> samples;
        long defaultSeed = 12345L;
//        String ref;
//        String refBefore;
//        String refAfter;
        
        assertSDSNominalCtxSamplesEqual(
                "oublier <xxx>\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_UNKNOWN,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT);
        
        assertSDSNominalCtxSamplesEqual(
                "oublier <xxx>\n" +
                "<xxx> légendaire(s)\n" +
                "<xxx> qu'on avait un peu oublié·e·(s)\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_UNKNOWN, 
                FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT);
        
        assertSDSNominalCtxSamplesEqual(
                "<un biscuit appéritif> tombe(nt) par terre\n" +
                "<un biscuit appéritif> verdâtre(s)\n" +
                "<un biscuit appéritif> qui vibre(nt)\n" +
                "<un biscuit appéritif> qui parle(nt) allemand\n" +
                "<un biscuit appéritif> qu'on avait un peu oublié·e·(s)\n",
                defaultSeed, "un biscuit appéritif", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_UNKNOWN,
                FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME);
        assertSDSNominalCtxSamplesEqual(
                "<des biscuits appéritif> sont tombé·e·s par terre\n" +
                "<des biscuits appéritif> verdâtres\n" +
                "<des biscuits appéritif> qui vibrent\n" +
                "<des biscuits appéritif> qui parlent allemand\n" +
                "<des biscuits appéritif> qu'on avait un peu oublié·e·s\n",
                defaultSeed, "des biscuits appéritif", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_PLURIEL,
                FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME);
        
        assertSDSNominalCtxSamplesEqual(
                "<une chaussette> est tombé(e) par terre\n" +
                "<une chaussette> en bronze\n" +
                "<une chaussette> verdâtre\n" +
                "<une chaussette> qui vibre\n" +
                "<une chaussette> qu'on avait un peu oublié(e)\n",
                defaultSeed, "une chaussette", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_NOM_CTX_COMPL_COMPL,
                FLAG_NOM_TYPE_INANIME);
        
        
        assertSDSNominalCtxSamplesEqual(
                "<la choucroute de ma mère> est tombé(e) par terre\n" +
                "je mange <la choucroute de ma mère>\n",
                defaultSeed, "la choucroute de ma mère", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_COMMON_CTX_REF_1PS,
                FLAG_NOM_TYPE_INANIME);

        // Referents potentiels multiples
        assertSDSNominalCtxSamplesEqual(
                "<la gardienne de son chateau et de ton appartement> est décédée\n" +
                "tu embauches <la gardienne de son chateau et de ton appartement> en CDI\n" +
                "elle embauche <la gardienne de son chateau et de ton appartement> en CDI\n",
                defaultSeed, "la gardienne de son chateau et de ton appartement", 
                FLAG_NOM_CTX_GENRE_FEMININ, FLAG_NOM_CTX_NOMBRE_SINGULIER, 
                FLAG_COMMON_CTX_REF_2PS, FLAG_COMMON_CTX_REF_3P,
                FLAG_COMMON_CTX_REF3P_NB_S, FLAG_COMMON_CTX_REF3P_T_A, FLAG_COMMON_CTX_REF3P_G_F,
                FLAG_NOM_TYPE_ANIME);
        
        assertSDSNominalCtxSamplesEqual(
                "oublier <xxx>\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_UNKNOWN,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT,
                FLAG_SNOMINAL_DETERMINANT_DEF, FLAG_SNOMINAL_DETERMINANT_UNDEF);
        assertSDSNominalCtxSamplesEqual(
                "oublier <xxx>\n", 
                defaultSeed, "xxx", 
                FLAG_NOM_CTX_GENRE_UNKNOWN, FLAG_NOM_CTX_NOMBRE_UNKNOWN,
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ANIME, FLAG_NOM_TYPE_ABSTRAIT,
                FLAG_SNOMINAL_DETERMINANT_UNDEF);
    }

    protected void assertSDSNominalCtxSamplesEqual(String expected, long randomSeed, 
            String sdText, String... origSdFlags) {
        SyntagmeDefinition sd = getNewSDSNominal();
        sd.setOrigDefinition(getOrigSD(sd.getType(), origSdFlags));
        sd.setText(sdText);
        List<String> samples = getSDCtxSamples(sd, randomSeed);
        assertSDCtxSamplesEquals(
                expected, 
                samples);
    }

    @Test
    public void testSDefVerbe() throws UserException {
        SyntagmeDefinition sd = getNewSDVerbe();
        
        // Pas dispo sans texte
        sd.setText("");
        assertFalse(sd.isAvailable());
        sd.setText("zzz");
        assertTrue(sd.isAvailable());
        
        String csujetp;
        String csujetg;
        String csujett;
        String cpr;
        String ccod;
        String ccodip;
        String ccodig;
        String ccodit;
        String cppa;
        String cppae;
        String cref;
        String cref3pn;
        String cref3pg;
        String cref3pt;
        String dforme;
        String dneg;
        String dcc;
        String dphase;
        String dreu;
        String dkmj;
        String dha;
        String deli;
        String rforme;
        String rneg;
        String rcc;

        String defCsujetp   = "csujetp: cs1s_ cs2s_ cs3s_ cs3sm_ cs1p_ cs2p_ cs3p_ ";
        String defCsujetg   = "csujetg: csg0_ csgf_ csgm_ ";
        String defCsujett   = "csujett: csta# csti# cstabs# ";
        String defCpr       = "cpr: cpr0+ cpr1_ ";
        String defCcod      = "ccod: ccod0_ ccoda_ ccodb_ ";
        String defCcodip    = "ccodip: ccod1s# ccod2s# ccod3s# ccod1p# ccod2p# ccod3p# ";
        String defCcodig    = "ccodig: ccodg0# ccodgf# ccodgm# ";
        String defCcodit    = "ccodit: ccodta# ccodti# ccodtabs# ";
        String defCppa      = "cppa: cppa0+ cppaa_ cppae_ ";
        String defCppae     = "cppae: cppaea# cppaep# cppaepc# ";
        String defCref      = "cref: cr1s_ cr2s_ cr3_ cr1p_ cr2p_ ";
        String defCref3pn   = "cref3pn: cr3pns# cr3pnp# ";
        String defCref3pg   = "cref3pg: cr3pg0# cr3pgf# cr3pgm# ";
        String defCref3pt   = "cref3pt: cr3pta# cr3pti# ";
        String defDforme    = "forme: finfi_ fpres_ fpco_ fppas_ fimpa_ fpqp_ ffuta_ fpass_ ffut_ fimpe_ fppre_ ";
        String defDneg      = "neg: neg0+ neg1# ";
        String defDcc       = "cc: cc0+ cc1# ";
        String defDphase    = "phase: def+ repl_ ";
        String defDreu      = "reu: reu0_ reu1+ ";
        String defDkmj      = "kmj: kmj# kimj# nomj# ";
        String defDha       = "ha: ha# hm# ";
        String defDeli      = "eli: eli0+ eli1_ ";
        String defRforme    = "forme: finfi_ fpres_ fpco_ fppas_ fimpa_ fpqp_ ffuta_ fpass_ ffut_ fimpe_ fppre_ ";
        String defRneg      = "neg: neg0+ neg1_ ";
        String defRcc       = "cc: cc0+ cc1_ ";
        
        csujetp     = defCsujetp;
        csujetg     = defCsujetg;
        csujett     = defCsujett;
        cpr         = defCpr;
        ccod        = defCcod;
        ccodip      = defCcodip;
        ccodig      = defCcodig;
        ccodit      = defCcodit;
        cppa        = defCppa;
        cppae       = defCppae;
        cref        = defCref;
        cref3pn     = defCref3pn;
        cref3pg     = defCref3pg;
        cref3pt     = defCref3pt;
        dforme      = defDforme;
        dneg        = defDneg;
        dcc         = defDcc;
        dphase      = defDphase;
        dreu        = defDreu;
        dkmj        = defDkmj;
        dha         = defDha;
        deli        = defDeli;
        rforme      = defRforme;
        rneg        = defRneg;
        rcc         = defRcc;


        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_2S);
        csujetp = "csujetp= cs1s_ cs2s* cs3s_ cs3sm_ cs1p_ cs2p_ cs3p_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // 3eme personne demande le type, animé par defaut
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_3S);
        csujetp = "csujetp= cs1s_ cs2s_ cs3s* cs3sm_ cs1p_ cs2p_ cs3p_ ";
        csujett = "csujett: csta+ csti_ cstabs_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        
        sd.selectFlag(FLAG_VERBE_CTX_COD_NONE);
        ccod = "ccod= ccod0* ccoda_ ccodb_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        sd.selectFlag(FLAG_VERBE_CTX_COD_BEFORE);
        ccod = "ccod= ccod0_ ccoda_ ccodb* ";
        ccodip = "ccodip: ccod1s_ ccod2s_ ccod3s_ ccod1p_ ccod2p_ ccod3p_ "; 
        ccodig = "ccodig: ccodg0+ ccodgf_ ccodgm_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_1S);
        ccodip = "ccodip= ccod1s* ccod2s_ ccod3s_ ccod1p_ ccod2p_ ccod3p_ "; 
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_3S);
        ccodip = "ccodip= ccod1s_ ccod2s_ ccod3s* ccod1p_ ccod2p_ ccod3p_ ";
        ccodit = "ccodit: ccodta_ ccodti_ ccodtabs_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        
        // Empeche le pronom de la meme personne que le sujet (sauf 3eme personne)
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_1S);
        csujetp = "csujetp= cs1s* cs2s_ cs3s_ cs3sm_ cs1p_ cs2p_ cs3p_ ";
        csujett = "csujett: csta# csti# cstabs# ";
        ccodip = "ccodip= ccod1s# ccod2s_ ccod3s* ccod1p_ ccod2p_ ccod3p_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_2S);
        csujetp = "csujetp= cs1s_ cs2s* cs3s_ cs3sm_ cs1p_ cs2p_ cs3p_ ";
        ccodip = "ccodip= ccod1s_ ccod2s# ccod3s* ccod1p_ ccod2p_ ccod3p_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_1P);
        csujetp = "csujetp= cs1s_ cs2s_ cs3s_ cs3sm_ cs1p* cs2p_ cs3p_ ";
        ccodip = "ccodip= ccod1s_ ccod2s_ ccod3s* ccod1p# ccod2p_ ccod3p_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_2P);
        csujetp = "csujetp= cs1s_ cs2s_ cs3s_ cs3sm_ cs1p_ cs2p* cs3p_ ";
        ccodip = "ccodip= ccod1s_ ccod2s_ ccod3s* ccod1p_ ccod2p# ccod3p_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        
        sd.selectFlag(FLAG_VERBE_CTX_COD_AFTER);
        ccod = "ccod= ccod0_ ccoda* ccodb_ ";
        ccodip = "ccodip= ccod1s# ccod2s# ccod3s% ccod1p# ccod2p# ccod3p# ";
        ccodig = defCcodig;
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // Differents flags de def qui impactent le remplacement
        sd.selectFlag(FLAG_VERBE_FORME_PRESENT);
        dforme = "forme= finfi_ fpres* fpco_ fppas_ fimpa_ fpqp_ ffuta_ fpass_ ffut_ fimpe_ fppre_ ";
        rforme = "forme: finfi# fpres+ fpco_ fppas# fimpa_ fpqp_ ffuta_ fpass_ ffut_ fimpe# fppre# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_FORME_IMPARFAIT);
        dforme = "forme= finfi_ fpres_ fpco_ fppas_ fimpa* fpqp_ ffuta_ fpass_ ffut_ fimpe_ fppre_ ";
        rforme = "forme: finfi# fpres_ fpco_ fppas# fimpa+ fpqp_ ffuta_ fpass_ ffut_ fimpe# fppre# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_FORME_INFINITIF);
        dforme = "forme= finfi* fpres_ fpco_ fppas_ fimpa_ fpqp_ ffuta_ fpass_ ffut_ fimpe_ fppre_ ";
        rforme = "forme: finfi+ fpres# fpco# fppas# fimpa# fpqp# ffuta# fpass# ffut# fimpe# fppre# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // Plusieurs mots : flag "avec complément circonstanciel" possible
        sd.setText("zzzz");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("zzzz zzzz");
        dcc = "cc: cc0_ cc1_ ";
        rcc = "cc: cc0+ cc1+ ";
        dneg = "neg: neg0_ neg1_ ";
        rneg = "neg: neg0+ neg1+ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText(" \" blabla,  blabla \" ");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        sd.selectFlag(FLAG_VERBE_CC_WITH);
        sd.selectFlag(FLAG_VERBE_NEGATION_WITHOUT);
        dcc = "cc= cc0_ cc1* ";
        rcc = "cc: cc0_ cc1+ ";
        dneg = "neg= neg0* neg1_ ";
        rneg = "neg: neg0+ neg1_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CC_WITHOUT);
        sd.selectFlag(FLAG_VERBE_NEGATION_WITH);
        dcc = "cc= cc0* cc1_ ";
        rcc = "cc: cc0+ cc1_ ";
        dneg = "neg= neg0_ neg1* ";
        rneg = "neg: neg0_ neg1+ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // Imperatif : limite la personne sujet, limite aussi l'utilisation du COD
        sd.selectFlag(FLAG_VERBE_FORME_IMPERATIF);
        csujetp = "csujetp= cs1s# cs2s_ cs3s# cs3sm# cs1p_ cs2p* cs3p# ";
        ccod = "ccod= ccod0_ ccoda* ccodb# ";
        dforme = "forme= finfi_ fpres_ fpco_ fppas_ fimpa_ fpqp_ ffuta_ fpass_ ffut_ fimpe* fppre_ ";
        rforme = "forme: finfi# fpres# fpco# fppas# fimpa# fpqp# ffuta# fpass# ffut# fimpe+ fppre# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        
        // Particpe passé active un auxiliaire dans le contexte ce qui empeche les autres formes
        sd.selectFlag(FLAG_VERBE_FORME_PARTICIPEPASSE);
        cppa = "cppa: cppa0# cppaa+ cppae_ ";
        csujetp = "csujetp= cs1s_ cs2s_ cs3s_ cs3sm_ cs1p_ cs2p* cs3p_ ";
        ccod = "ccod= ccod0_ ccoda* ccodb_ ";
        dforme = "forme= finfi# fpres# fpco# fppas* fimpa# fpqp# ffuta# fpass# ffut# fimpe# fppre# ";
        rforme = "forme: finfi# fpres# fpco# fppas+ fimpa# fpqp# ffuta# fpass# ffut# fimpe# fppre# ";
        // Normalement neg0 forcé mais déjà modifié
        dneg = "neg= neg0_ neg1% ";
        rneg = "neg: neg0+ neg1# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // Passif agentif par défaut pour aux etre
        sd.selectFlag(FLAG_VERBE_CTX_PPASSE_AUX_ETRE);
        cppa = "cppa= cppa0# cppaa_ cppae* ";
        cppae = "cppae: cppaea_ cppaep_ cppaepc+ ";
        // Pas de COD si pas de pronom reflechi
        ccod = "ccod= ccod0_ ccoda% ccodb# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        
        // D'autres cas de participe passé
        sd = getNewSDVerbe();
        
        csujetp     = defCsujetp;
        csujetg     = defCsujetg;
        csujett     = defCsujett;
        cpr         = defCpr;
        ccod        = defCcod;
        ccodip      = defCcodip;
        ccodig      = defCcodig;
        ccodit      = defCcodit;
        cppa        = defCppa;
        cppae       = defCppae;
        cref        = defCref;
        cref3pn     = defCref3pn;
        cref3pg     = defCref3pg;
        cref3pt     = defCref3pt;
        dforme      = defDforme;
        dneg        = defDneg;
        dcc         = defDcc;
        dkmj        = defDkmj;
        dha         = defDha;
        deli        = defDeli;
        rforme      = defRforme;
        rneg        = defRneg;
        rcc         = defRcc;
        
        sd.selectFlag(FLAG_VERBE_CTX_PPASSE_AUX_ETRE);
        cppa = "cppa= cppa0_ cppaa_ cppae* ";
        cppae = "cppae: cppaea_ cppaep_ cppaepc+ ";
        ccod = "ccod: ccod0+ ccoda# ccodb# ";
        dforme = "forme: finfi# fpres# fpco# fppas+ fimpa# fpqp# ffuta# fpass# ffut# fimpe# fppre# ";
        rforme = "forme: finfi# fpres# fpco# fppas+ fimpa# fpqp# ffuta# fpass# ffut# fimpe# fppre# ";
        dneg = "neg: neg0+ neg1# ";
        rneg = "neg: neg0+ neg1# ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        
        sd.selectFlag(FLAG_VERBE_CTX_PREFLECHI_YES);
        cpr = "cpr= cpr0_ cpr1* ";
        cppa = "cppa= cppa0_ cppaa# cppae* ";
        cppae = defCppae;
        ccod = defCcod;
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // COD pronominal + pronom reflechi : 3eme personne uniquement
        sd.selectFlag(FLAG_VERBE_CTX_COD_BEFORE);
        ccod = "ccod= ccod0_ ccoda_ ccodb* ";
        ccodip = "ccodip: ccod1s# ccod2s# ccod3s_ ccod1p# ccod2p# ccod3p_ "; 
        ccodig = "ccodig: ccodg0+ ccodgf_ ccodgm_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        // Check pas de pb avec les autres sujets
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_2S);
        csujetp = "csujetp= cs1s_ cs2s* cs3s_ cs3sm_ cs1p_ cs2p_ cs3p_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_3S);
        csujetp = "csujetp= cs1s_ cs2s_ cs3s* cs3sm_ cs1p_ cs2p_ cs3p_ ";
        csujett = "csujett: csta+ csti_ cstabs_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        

        
        // Pas de pb pour selectionner des referents supplémentaires
        sd = getNewSDVerbe();
        
        csujetp     = defCsujetp;
        csujetg     = defCsujetg;
        csujett     = defCsujett;
        cpr         = defCpr;
        ccod        = defCcod;
        ccodip      = defCcodip;
        ccodig      = defCcodig;
        ccodit      = defCcodit;
        cppa        = defCppa;
        cppae       = defCppae;
        cref        = defCref;
        cref3pn     = defCref3pn;
        cref3pg     = defCref3pg;
        cref3pt     = defCref3pt;
        dforme      = defDforme;
        dneg        = defDneg;
        dcc         = defDcc;
        dkmj        = defDkmj;
        dha         = defDha;
        deli        = defDeli;
        rforme      = defRforme;
        rneg        = defRneg;
        rcc         = defRcc;
        
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_1S);
        sd.selectFlag(FLAG_COMMON_CTX_REF_2PP);
        csujetp = "csujetp= cs1s* cs2s_ cs3s_ cs3sm_ cs1p_ cs2p_ cs3p_ ";
        cref = "cref= cr1s_ cr2s_ cr3_ cr1p_ cr2p* ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        // Meme si il y a un cod pronom
        sd.selectFlag(FLAG_VERBE_CTX_COD_BEFORE);
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_2S);
        ccod = "ccod= ccod0_ ccoda_ ccodb* ";
        ccodip = "ccodip= ccod1s# ccod2s* ccod3s_ ccod1p_ ccod2p_ ccod3p_ "; 
        ccodig = "ccodig: ccodg0+ ccodgf_ ccodgm_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        cref = "cref= cr1s_ cr2s_ cr3* cr1p_ cr2p* ";
        cref3pn = "cref3pn: cr3pns+ cr3pnp_ ";
        cref3pg = "cref3pg: cr3pg0+ cr3pgf_ cr3pgm_ ";
        cref3pt = "cref3pt: cr3pta+ cr3pti_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_3P);
        ccodip = "ccodip= ccod1s# ccod2s_ ccod3s_ ccod1p_ ccod2p_ ccod3p* "; 
        ccodit = "ccodit: ccodta_ ccodti_ ccodtabs_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        
        
        // Differents cas de selection auto à partir du texte
        sd = getNewSDVerbe("xxx xxx");
        
        csujetp     = defCsujetp;
        csujetg     = defCsujetg;
        csujett     = defCsujett;
        cpr         = defCpr;
        ccod        = defCcod;
        ccodip      = defCcodip;
        ccodig      = defCcodig;
        ccodit      = defCcodit;
        cppa        = defCppa;
        cppae       = defCppae;
        cref        = defCref;
        cref3pn     = defCref3pn;
        cref3pg     = defCref3pg;
        cref3pt     = defCref3pt;
        dforme      = defDforme;
        dneg        = "neg: neg0_ neg1_ ";
        dcc         = "cc: cc0_ cc1_ ";
        dkmj        = defDkmj;
        dha         = defDha;
        deli        = defDeli;
        rforme      = defRforme;
        rneg        = "neg: neg0+ neg1+ ";
        rcc         = "cc: cc0+ cc1+ ";

        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("mange pas");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("bla ne mange");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("neglige pas");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("ne mange");
        dneg = "neg: neg0_ neg1+ ";
        rneg = "neg: neg0_ neg1+ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("ne mange pas");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("n'éternue");
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());
        sd.setText("NE mange pas");
        dkmj = "kmj: kmj_ kimj_ nomj_ ";
        assertEquals(csujetp + csujetg + csujett + cpr + ccod + ccodip + ccodig + ccodit + cppa + cppae + cref + cref3pn + cref3pg + cref3pt + dforme + dneg + dcc + dphase + dreu + dkmj + dha + deli + " [R] " + rforme + rneg + rcc,  sd.toString());

        
    }

    @Test
    public void testSDefVerbeValidity() throws UserException {
        SyntagmeDefinition sd = getNewSDVerbe();
        sd.setText("xxx");

        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-csujetp", ex.getCode());
        }
        
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_3S);
        // Pas besoin de renseigner le genre et le type est préselectionné avec 3eme p.
        assertTrue(sd.isFlagOn(FLAG_VERBE_CTX_SUJET_TYPE_A));
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-ccod", ex.getCode());
        }
        
        String nextFailCode = "repl-unset-attr-forme";
        
        sd.selectFlag(FLAG_VERBE_CTX_COD_NONE);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals(nextFailCode, ex.getCode());
        }
        sd.selectFlag(FLAG_VERBE_CTX_COD_AFTER);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-ccodit", ex.getCode());
        }
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_TYPE_INANIME);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals(nextFailCode, ex.getCode());
        }

        sd.selectFlag(FLAG_VERBE_CTX_COD_BEFORE);
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_1S);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals(nextFailCode, ex.getCode());
        }

        sd.getReplacementDefinition().selectFlag(FLAG_VERBE_FORME_PRESENT);
        // Le reste a des valeurs par défaut
        sd.validate();
        
        sd.selectFlag(FLAG_VERBE_CTX_PPASSE_AUX_AVOIR);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            // Forme déjà positionnée, donc plus positionnée
            assertEquals("repl-unset-attr-forme", ex.getCode());
        }
        sd.getReplacementDefinition().selectFlag(FLAG_VERBE_FORME_PARTICIPEPASSE);
        sd.validate();

        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        // Que des valeurs par défaut
        sd.validate();


    }
    
    @Test
    public void testSDefVerbePrecText() {
        SyntagmeDefinition sd = getNewSDVerbe();
        sd.update();
        assertNull(sd.getFlagsToSelect());
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "");
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "xxx");
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "se", FLAG_VERBE_CTX_PREFLECHI_YES);
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "s", FLAG_VERBE_CTX_PREFLECHI_YES);
        
        assertSDFlagsToSelectWithPrecedingTextEquals(sd, "xxx xxx");        
    }
    
    @Test
    public void testSDefVerbeCtxSamplesPresent() {        
        final String[][] samples = {
            { "je <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_1S },
            
            // Types COD en aval
            { "je <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_NONE },
            { "je <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
            { "je <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je <xxx> quelqu'un", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            
            // Pronoms COD en amont
            { "je te <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "je nous <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "je vous <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "je la/le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "je la/le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je la/le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je la·le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je la <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "je la <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je la <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je la <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je le <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            
            // Pronom reflechi
            { "je me <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "je me <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_AFTER },
            { "je me la/le <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "je me les <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2S
            { "tu <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "tu <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_AFTER },
            { "tu me <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "tu la/le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "tu te <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "tu te les <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 1P
            { "nous <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "nous <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_AFTER },
            { "nous me <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "nous la/le <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "nous nous <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "nous nous les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2P
            { "vous <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_2P },
            { "vous <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_AFTER },
            { "vous me <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "vous la/le <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "vous vous <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "vous vous les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3S
            { "il·elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S },
            { "il·elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_0 },
            { "il/elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_I },
            { "il/elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_I, FLAG_VERBE_CTX_SUJET_G_0 },
            { "il/elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_ABS },
            { "il·elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_A },
            { "il·elle <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_A, FLAG_VERBE_CTX_SUJET_G_0 },
            { "il <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M },
            { "il <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_SUJET_TYPE_I },
            { "il <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_SUJET_TYPE_A },
            { "elle <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F },
            { "elle <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_SUJET_TYPE_ABS },
            { "elle <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_SUJET_TYPE_A },
            { "il·elle <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_COD_AFTER },
            { "il·elle me <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "il·elle la/le <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "il·elle la <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "il·elle se <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "il·elle se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3SM
            { "on <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_3SM },
            { "on me <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "on la <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "on se <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "on se les <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3P
            { "il·elle·s <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P },
            { "il·elle·s <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_0 },
            { "ils/elles <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_I },
            { "ils/elles <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_I, FLAG_VERBE_CTX_SUJET_G_0 },
            { "ils/elles <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_ABS },
            { "il·elle·s <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_A },
            { "il·elle·s <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_A, FLAG_VERBE_CTX_SUJET_G_0 },
            { "ils <xxx>",          null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M },
            { "ils <xxx>",          null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_SUJET_TYPE_I },
            { "ils <xxx>",          null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_SUJET_TYPE_A },
            { "elles <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F },
            { "elles <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_SUJET_TYPE_ABS },
            { "elles <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_SUJET_TYPE_A },
            { "il·elle·s <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_COD_AFTER },
            { "il·elle·s me <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "il·elle·s la/le <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "il·elle·s la <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "il·elle·s se <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "il·elle·s se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
        };
        
        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }
            
            flags[0] = FLAG_VERBE_FORME_PRESENT;
            assertSDVerbeCtxSamplesEqual(result, 1234L, "xxx", flags);
        }
    }

    @Test
    public void testSDefVerbeCtxSamplesFormesSimples() {        

        final String[] formesSimilairesPresent = {
            FLAG_VERBE_FORME_PASSECOMPOSE, FLAG_VERBE_FORME_FUTUR,
            FLAG_VERBE_FORME_IMPARFAIT, FLAG_VERBE_FORME_PQPARFAIT,
            FLAG_VERBE_FORME_FUTURANT, FLAG_VERBE_FORME_PASSESIMPLE,
        };
        
        // Echantillon d'exemples avec autres temps avec gestion identique
        final String[][] samples = {
            { "je <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
            { "tu les <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            { "elle se <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "ils se les <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
        };
        
        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }
            
            for (String forme : formesSimilairesPresent) {
                flags[0] = forme;
                assertSDVerbeCtxSamplesEqual(result, 1234L, "xxx", flags);
            }
        }
    }
    
    @Test
    public void testSDefVerbeCtxSamplesPasseCompose() {        

        final String[][] samples = {
            { "j'ai <xxx>",         FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "j'ai <xxx> quelque chose", FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_AFTER },
            { "j'ai <xxx> quelqu'un", FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            
            { "je t'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "je nous ai <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "je vous ai <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je l'ai <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je les ai <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P, FLAG_VERBE_CTX_CODINFO_GENRE_M },
                        
            { "j'ai été <xxx> par ...", FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE },
            { "j'ai été <xxx> par ...", FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVECDA },
            { "j'ai été <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "je suis <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "je me suis <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "je me la suis <xxx>",FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },

            // 2S
            { "tu as <xxx>",        FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "tu m'as <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "tu nous as <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "tu vous as <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "tu l'as <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "tu les as <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
                        
            { "tu as été <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "tu es <xxx>",        FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "tu t'es <xxx>",      FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "tu te l'es <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },

            // 1P
            { "nous avons <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "nous m'avons <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "nous t'avons <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "nous vous avons <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "nous l'avons <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "nous les avons <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
                        
            { "nous avons été <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "nous sommes <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "nous nous sommes <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "nous nous la sommes <xxx>", FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            
            // 2P
            { "vous avez <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "vous m'avez <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "vous t'avez <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "vous nous avez <xxx>", FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "vous l'avez <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "vous les avez <xxx>", FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
                        
            { "vous avez été <xxx>", FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "vous êtes <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "vous vous êtes <xxx>", FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "vous vous l'êtes <xxx>", FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            
            // 3S
            { "il a <xxx>",         FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "il m'a <xxx>",       FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "il t'a <xxx>",       FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "il nous a <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "il vous a <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "il l'a <xxx>",       FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "il les a <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
                        
            { "il a été <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "il est <xxx>",       FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "il s'est <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "il se l'est <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            
            // 3SM
            { "on a <xxx>",         FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "on m'a <xxx>",       FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "on les a <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },

            { "on a été <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "on est <xxx>",       FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "on s'est <xxx>",     FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "on se l'est <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            
            // 3P
            { "elles ont <xxx>",    FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "elles m'ont <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "elles t'ont <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "elles nous ont <xxx>", FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "elles vous ont <xxx>", FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "elles l'ont <xxx>",  FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "elles les ont <xxx>", FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
                        
            { "elles ont été <xxx>", FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "elles sont <xxx>",   FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "elles se sont <xxx>", FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "elles se la sont <xxx>", FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            
        };
        
        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }

            assertSDVerbeCtxSamplesEqual(result, 1234L, "xxx", flags);
        }
        
    }
    
    @Test
    public void testSDefVerbeCtxSamplesPPresent() {        

        final String[][] samples = {
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P },
            { "<xxx>",          FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P },
            
            { "<xxx> quelque chose", FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelque chose", FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_SUJET_PERS_1S },
            { "<xxx> quelqu'un", FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            
            { "te <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "nous <xxx>",     FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "vous <xxx>",     FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "la/le <xxx>",    FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "la <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "le <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "les <xxx>",      FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "me la/le <xxx>", FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "me les <xxx>",   FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2S
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la/le <xxx>",    FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "te <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "te les <xxx>",   FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 1P
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la/le <xxx>",    FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "nous <xxx>",     FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "nous les <xxx>", FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2P
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la/le <xxx>",    FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "vous <xxx>",     FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "vous les <xxx>", FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3S
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "se <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>",   FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3SM
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "se <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>",   FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3P
            { "me <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "se <xxx>",       FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>",   FLAG_VERBE_FORME_PARTICIPEPRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
        };

        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }

            assertSDVerbeCtxSamplesEqual(result, 1234L, "xxx", flags);
        }
    }
    
    @Test
    public void testSDefVerbeCtxSamplesInfinitif() {        
        
        final String[][] samples = {
            { "<xxx>",                              null },
            
            { "<xxx>\nje voudrais <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_1S },
            { "<xxx> quelque chose\nje voudrais <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelqu'un\nje voudrais <xxx> quelqu'un", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            
            { "te <xxx>\nje voudrais te <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "nous <xxx>\nje voudrais nous <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "vous <xxx>\nje voudrais vous <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "la <xxx>\nje voudrais la <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "les <xxx>\nje voudrais les <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // Pronom reflechi
            { "me <xxx>\nje voudrais me <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "me <xxx> quelque chose\nje voudrais me <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_AFTER },
            { "me le <xxx>\nje voudrais me le <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M},
            { "me les <xxx>\nje voudrais me les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2S
            { "<xxx>\ntu voudrais <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "<xxx> quelque chose\ntu voudrais <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_AFTER },
            { "me <xxx>\ntu voudrais me <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "le <xxx>\ntu voudrais le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "te <xxx>\ntu voudrais te <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "te les <xxx>\ntu voudrais te les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 1P
            { "<xxx>\nnous voudrions <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "<xxx> quelque chose\nnous voudrions <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_AFTER },
            { "me <xxx>\nnous voudrions me <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "le <xxx>\nnous voudrions le <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "nous <xxx>\nnous voudrions nous <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "nous les <xxx>\nnous voudrions nous les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2P
            { "<xxx>\nvous voudriez <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_2P },
            { "<xxx> quelque chose\nvous voudriez <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_AFTER },
            { "me <xxx>\nvous voudriez me <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "le <xxx>\nvous voudriez le <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "vous <xxx>\nvous voudriez vous <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "vous les <xxx>\nvous voudriez vous les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3S
            { "<xxx>\nelle voudrait <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F },
            { "<xxx> quelque chose\nelle voudrait <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },
            { "me <xxx>\nelle voudrait me <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "le <xxx>\nelle voudrait le <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "se <xxx>\nelle voudrait se <xxx>",   null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>\nelle voudrait se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3S non animé
            { "<xxx> quelque chose",                null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_I, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelque chose",                null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_ABS, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },
            
            // 3SM
            { "<xxx>\non voudrait <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_3SM },
            { "me <xxx>\non voudrait me <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la <xxx>\non voudrait la <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "se <xxx>\non voudrait se <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>\non voudrait se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3P
            { "<xxx>\nils voudraient <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M },
            { "<xxx> quelque chose\nils voudraient <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_COD_AFTER },
            { "me <xxx>\nils voudraient me <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "la <xxx>\nils voudraient la <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "se <xxx>\nils voudraient se <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>\nils voudraient se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3P non animé
            { "<xxx> quelque chose",                null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_I, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelque chose",                null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_TYPE_ABS, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },
        };

        final String[][] samples2 = {
            { "<xxx>\nj'aime <xxx>",                null, FLAG_VERBE_CTX_SUJET_PERS_1S },
            { "<xxx> quelque chose\nj'aime <xxx> quelque chose", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelqu'un\nj'aime <xxx> quelqu'un", null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
                        
            { "la <xxx>\nj'aime la <xxx>",          null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            
            { "me <xxx>\nj'aime me <xxx>",          null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "me le <xxx>\nj'aime me le <xxx>",    null, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M},
            
            // 2S
            { "<xxx>\ntu aimes <xxx>",              null, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "le <xxx>\ntu aimes le <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "te <xxx>\ntu aimes te <xxx>",        null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "te les <xxx>\ntu aimes te les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 1P
            { "<xxx>\nnous aimons <xxx>",           null, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "le <xxx>\nnous aimons le <xxx>",     null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "nous <xxx>\nnous aimons nous <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "nous les <xxx>\nnous aimons nous les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 2P
            { "<xxx>\nvous aimez <xxx>",            null, FLAG_VERBE_CTX_SUJET_PERS_2P },
            { "me <xxx>\nvous aimez me <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "vous <xxx>\nvous aimez vous <xxx>",  null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "vous les <xxx>\nvous aimez vous les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3S
            { "<xxx>\nelle aime <xxx>",             null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F },
            { "me <xxx>\nelle aime me <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "se <xxx>\nelle aime se <xxx>",       null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>\nelle aime se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3S non animé
            { "<xxx> quelque chose",                null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_I, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelque chose",                null, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_TYPE_ABS, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_AFTER },

            // 3SM
            { "<xxx>\non aime <xxx>",               null, FLAG_VERBE_CTX_SUJET_PERS_3SM },
            { "me <xxx>\non aime me <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "se <xxx>\non aime se <xxx>",         null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>\non aime se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            // 3P
            { "<xxx>\nils aiment <xxx>",            null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M },
            { "me <xxx>\nils aiment me <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "se <xxx>\nils aiment se <xxx>",      null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se les <xxx>\nils aiment se les <xxx>", null, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
        };

        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }

            flags[0] = FLAG_VERBE_FORME_INFINITIF;
            assertSDVerbeCtxSamplesEqual(result, 1234L, "xxx", flags);
        }
        for (String[] sampleData : samples2) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }

            flags[0] = FLAG_VERBE_FORME_INFINITIF;
            assertSDVerbeCtxSamplesEqual(result, 7774L, "xxx", flags);
        }
    }
    
    @Test
    public void testSDefVerbeCtxSamplesImpe() {        
        
        final String[][] samples = {
            { "<xxx>",          FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "<xxx>",          FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "<xxx>",          FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_2P },
            
            { "<xxx> quelque chose", FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_COD_AFTER },
            { "<xxx> quelque chose", FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "<xxx> quelqu'un", FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
                        
            { "<xxx>-toi",      FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "<xxx>-nous",     FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "<xxx>-vous",     FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES },
        };

        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }

            assertSDVerbeCtxSamplesEqual(result, 1234L, "xxx", flags);
        }
    }
    
    @Test
    public void testSDefVerbeCtxSamplesEli() {        
        final String[][] samples = {
            // Quelques cas Présent
            { "j'<eee>",            FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S },
            
            { "j'<eee> quelque chose", FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
            { "j'<eee> quelqu'un",  FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            
            { "je t'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "je nous <eee>",      FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "je vous <eee>",      FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_INANIME },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ANIME },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F, FLAG_VERBE_CTX_CODINFO_TYPE_ABSTRAIT },
            { "je l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M },
            { "je les <eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            { "je m'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "je m'<eee> quelque chose", FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_AFTER },
            { "je me l'<eee>",      FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            
            { "tu <eee>",           FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "tu m'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "tu l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "tu t'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "tu te l'<eee>",      FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            
            { "nous <eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "nous vous <eee>",    FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "nous l'<eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "nous nous <eee>",    FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "nous nous l'<eee>",  FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            
            { "vous <eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P },
            { "vous nous <eee>",    FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "vous l'<eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "vous vous <eee>",    FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "vous vous l'<eee>",  FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_2P, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            
            { "elle <eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F },
            { "elle m'<eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "elle l'<eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "elle l'<eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "elle s'<eee>",       FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "elle se les <eee>",  FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            { "on <eee>",           FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM },
            { "on vous <eee>",      FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "on l'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "on s'<eee>",         FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "on se l'<eee>",      FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3SM, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            
            { "ils <eee>",          FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M },
            { "ils vous <eee>",     FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2P },
            { "ils l'<eee>",        FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "ils s'<eee>",        FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "ils se les <eee>",   FLAG_VERBE_FORME_PRESENT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            
            // Quelques cas formes similaires à Présent
            { "j'<eee> quelque chose", FLAG_VERBE_FORME_FUTUR, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
            { "tu l'<eee>",         FLAG_VERBE_FORME_PQPARFAIT, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "elle s'<eee>",       FLAG_VERBE_FORME_PASSESIMPLE, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "ils se les <eee>",   FLAG_VERBE_FORME_IMPARFAIT, FLAG_VERBE_CTX_SUJET_PERS_3P, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },

            
            // Quelques cas Participe passé
            { "j'ai <eee>",         FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            
            { "je t'ai <eee>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "je nous ai <eee>",   FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "je l'ai <eee>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "je les ai <eee>",    FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
                        
            { "j'ai été <eee> par ...", FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE },
            { "j'ai été <eee>",     FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_PASSIVE },
            { "je suis <eee>",      FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "je me suis <eee>",   FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "je me la suis <eee>",FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },

            { "tu as <eee>",        FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "tu m'as <eee>",      FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "tu t'es <eee>",      FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "tu te l'es <eee>",   FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },

            { "il a <eee>",         FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR },
            { "il m'a <eee>",       FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_AVOIR, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1S },
            { "il est <eee>",       FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PPASSE_AUXE_ACTIVE },
            { "il s'est <eee>",     FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_M, FLAG_VERBE_CTX_PPASSE_AUX_ETRE, FLAG_VERBE_CTX_PREFLECHI_YES },
            

            
            // Quelques cas infinitif
            { "<eee>",                              FLAG_VERBE_FORME_INFINITIF },
            
            { "<eee> quelque chose\nje voudrais <eee> quelque chose", FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_AFTER },
                        
            { "t'<eee>\nje voudrais t'<eee>",       FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_2S },
            { "nous <eee>\nje voudrais nous <eee>", FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_1P },
            { "l'<eee>\nje voudrais l'<eee>",       FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            { "l'<eee>\nje voudrais l'<eee>",       FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_F },
            { "les <eee>\nje voudrais les <eee>",   FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            { "m'<eee>\nje voudrais m'<eee>",       FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "me l'<eee>\nje voudrais me l'<eee>", FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S, FLAG_VERBE_CTX_CODINFO_GENRE_M},
            { "me les <eee>\nje voudrais me les <eee>", FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1S, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3P },
            
            { "<eee>\ntu voudrais <eee>",           FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_2S },
            { "t'<eee>\ntu voudrais t'<eee>",       FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },
            
            { "<eee>\nnous voudrions <eee>",        FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1P },
            { "nous <eee>\nnous voudrions nous <eee>", FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_1P, FLAG_VERBE_CTX_PREFLECHI_YES },
                        
            { "<eee>\nelle voudrait <eee>",         FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F },
            { "s'<eee>\nelle voudrait s'<eee>",     FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES },
            { "se l'<eee>\nelle voudrait se l'<eee>", FLAG_VERBE_FORME_INFINITIF, FLAG_VERBE_CTX_SUJET_PERS_3S, FLAG_VERBE_CTX_SUJET_G_F, FLAG_VERBE_CTX_PREFLECHI_YES, FLAG_VERBE_CTX_COD_BEFORE, FLAG_VERBE_CTX_CODINFO_PERS_3S },
            
            // Quelques cas impératif
            { "<eee>",          FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_2S },            
            { "<eee> quelque chose", FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_COD_AFTER },
            { "<eee>-toi",      FLAG_VERBE_FORME_IMPERATIF, FLAG_VERBE_CTX_SUJET_PERS_2S, FLAG_VERBE_CTX_PREFLECHI_YES },

        };
        
        for (String[] sampleData : samples) {
            String[] flags = Arrays.copyOfRange(sampleData, 1, sampleData.length);
            String result = sampleData[0];
            if (result.length() > 0) {
                result += "\n";
            }
            
            assertSDVerbeCtxSamplesEqual(result, 1234L, "eee", flags);
        }
    }

    protected void assertSDVerbeCtxSamplesEqual(String expected, long randomSeed, 
            String sdText, String... origSdFlags) {
        SyntagmeDefinition sd = getNewSDVerbe();
        sd.setOrigDefinition(getOrigSD(sd.getType(), origSdFlags));
        sd.setText(sdText);
        List<String> samples = getSDCtxSamples(sd, randomSeed);
        System.out.println(sd.getOrigDefinition());
        assertSDCtxSamplesEquals(
                expected, 
                samples);
    }

    
    @Test
    public void testSDefCompCirc() {
        SyntagmeDefinition sd = getNewSDCompCirc();
        
        // Pas dispo sans texte
        sd.setText(" , ! ");
        assertFalse(sd.isAvailable());
        sd.setText("zzz");
        assertTrue(sd.isAvailable());
        
        String cref;
        String cref3pn;
        String cref3pg;
        String cref3pt;
        String dtype;
        String dphase;
        String dreu;
        String dkmj;
        String dha;
        String deli;
        String rtype;

        String defCref      = "cref: cr1s_ cr2s_ cr3_ cr1p_ cr2p_ ";
        String defCref3pn   = "cref3pn: cr3pns# cr3pnp# ";
        String defCref3pg   = "cref3pg: cr3pg0# cr3pgf# cr3pgm# ";
        String defCref3pt   = "cref3pt: cr3pta# cr3pti# ";
        String defDtype     = "type: tlieu_ ttps_ tother_ ";
        String defDphase    = "phase: def+ repl_ ";
        String defDreu      = "reu: reu0_ reu1+ ";
        String defDkmj      = "kmj: kmj# kimj# nomj# ";
        String defDha       = "ha: ha# hm# ";
        String defDeli      = "eli: eli0+ eli1_ ";
        String defRtype     = "type: tlieu+ ttps+ tother+ ";
        
        cref    = defCref;
        cref3pn = defCref3pn;
        cref3pg = defCref3pg;
        cref3pt = defCref3pt;
        dtype   = defDtype;
        dphase  = defDphase;
        dreu    = defDreu;
        dkmj    = defDkmj;
        dha     = defDha;
        deli    = defDeli;
        rtype   = defRtype;
        
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());
        
        // Positionner un texte ne change rien
        sd.setText("blabla zerze rzer");
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());
        sd.setText(null);
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());
        
        // Pas de lien def/repl
        sd.selectFlag(FLAG_COMPCIRC_TYPE_LIEU);
        dtype = "type= tlieu* ttps_ tother_ ";
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());
        sd.getReplacementDefinition().unselectFlag(FLAG_COMPCIRC_TYPE_TEMPS);
        rtype = "type= tlieu* ttps_ tother* ";
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());

        // CRef choix multiple
        sd.selectFlag(FLAG_COMMON_CTX_REF_1PS);
        cref = "cref= cr1s* cr2s_ cr3_ cr1p_ cr2p_ ";
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());
        
        // CRef 3P active les attributs liés
        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        cref = "cref= cr1s* cr2s_ cr3* cr1p_ cr2p_ ";
        cref3pn = "cref3pn: cr3pns+ cr3pnp_ ";
        cref3pg = "cref3pg: cr3pg0+ cr3pgf_ cr3pgm_ ";
        cref3pt = "cref3pt: cr3pta+ cr3pti_ ";
        assertEquals(cref + cref3pn + cref3pg + cref3pt + dtype + dphase + dreu + dkmj + dha + deli + " [R] " + rtype,  sd.toString());
    }    
    
    @Test
    public void testSDefCompCircValidity() throws UserException {
        SyntagmeDefinition sd = getNewSDCompCirc();
        sd.setText("xxxx");

//        try {
//            sd.validate();
//            fail();
//        } catch (UserException ex) {
//            assertEquals("unset-attr-cref", ex.getCode());
//        }
//        
//        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        sd.validate();
    }

    @Test
    public void testCompCircCtxSamples() {
        long defaultSeed = 12345L;
        
        assertSDCompCircCtxSamplesEqual(
                "", 
                defaultSeed, "xxx",
                FLAG_COMMON_CTX_REF_1PS);
        
    }

    protected void assertSDCompCircCtxSamplesEqual(String expected, long randomSeed, 
            String sdText, String... origSdFlags) {
        SyntagmeDefinition sd = getNewSDCompCirc();
        sd.setOrigDefinition(getOrigSD(sd.getType(), origSdFlags));
        sd.setText(sdText);
        List<String> samples = getSDCtxSamples(sd, randomSeed);
        assertSDCtxSamplesEquals(
                expected, 
                samples);
    }

    
    @Test
    public void testExtractSentencesData() {
        
        assertTextHasSentenceData("", "");
        assertTextHasSentenceData("bla", "wc: 1, lwc: 1, in: 0, pt: 0");
        assertTextHasSentenceData("Bla bla bla", "wc: 3, lwc: 2, in: 1, pt: 0");
        assertTextHasSentenceData("BLa Bla bLA", "wc: 3, lwc: 0, in: 0, pt: 0");
        assertTextHasSentenceData("Blà blà blà", "wc: 3, lwc: 2, in: 1, pt: 0");
        assertTextHasSentenceData("BlÔ blà blà", "wc: 3, lwc: 2, in: 0, pt: 0");
        assertTextHasSentenceData("Bla - bla", "wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("-Bla bla", "wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("- Bla bla", "wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("Bla-bla bla", "wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("A zzz zz 34", "wc: 4, lwc: 3, in: 1, pt: 0");
        assertTextHasSentenceData("Ô hÂhâ où éé", "wc: 4, lwc: 2, in: 1, pt: 0");
        assertTextHasSentenceData("Âme charitable", "wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("L'amour d'aujourd'hui", "wc: 5, lwc: 4, in: 1, pt: 0");
        assertTextHasSentenceData(".", "");
        assertTextHasSentenceData(".?!", "");
        assertTextHasSentenceData("  .  ... ! ? .. ", "");
        assertTextHasSentenceData("Bla bla bla.", "wc: 3, lwc: 2, in: 1, pt: 1");
        assertTextHasSentenceData("Bla bla bla.?.!!", "wc: 3, lwc: 2, in: 1, pt: 1");
        assertTextHasSentenceData("Bla bla bla ... ?? !", "wc: 3, lwc: 2, in: 1, pt: 1");
        assertTextHasSentenceData("  .. !! .  Bla bla bla.?.!!", "wc: 3, lwc: 2, in: 1, pt: 1");
        assertTextHasSentenceData("Bla.bla", "wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("Une AK-47", "wc: 2, lwc: 0, in: 1, pt: 0");
        assertTextHasSentenceData("Un·e chargé·e de clientèle", "wc: 4, lwc: 3, in: 1, pt: 0");
        
        assertTextHasSentenceData("Bla bla. Bla bla", "wc: 2, lwc: 1, in: 1, pt: 1 - wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("Bla bla… Bla bla", "wc: 2, lwc: 1, in: 1, pt: 1 - wc: 2, lwc: 1, in: 1, pt: 0");
        assertTextHasSentenceData("BLA BLA !!! ! bla bla ??", "wc: 2, lwc: 0, in: 0, pt: 1 - wc: 2, lwc: 2, in: 0, pt: 1");
        assertTextHasSentenceData(" Bla bla : « Blab bla bla ? »", "wc: 5, lwc: 3, in: 1, pt: 1");
        assertTextHasSentenceData("Bla bla. Bla bla ? bla..", "wc: 2, lwc: 1, in: 1, pt: 1 - wc: 2, lwc: 1, in: 1, pt: 1 - wc: 1, lwc: 1, in: 0, pt: 1");

    }
    
    public void assertTextHasSentenceData(String text, String strData) {
        List<Sentence> ls = frLang.extractSentences(text);
        StringBuilder output = new StringBuilder();
        for (Sentence s : ls) {
            if (output.length() > 0) {
                output.append(" - ");
            }
            output.append(s);
        }
        assertEquals(strData, output.toString());
    }

    
    @Test
    public void testKeepMajFlags() {
        SyntagmeDefinition sd = getNewSDSNominal();
        sd.update();
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_NONE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_INSIDE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_ALL));
        assertEquals("", frLang.normalizeSyntagmeText(sd));
        sd.setText("un chien");
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_NONE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_INSIDE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_ALL));
        assertEquals("un chien", frLang.normalizeSyntagmeText(sd));
        sd.setText("Un Chien ANDALOU");
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_NONE));
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_INSIDE));
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_ALL));
        assertEquals("Un Chien ANDALOU", frLang.normalizeSyntagmeText(sd));
        sd.selectFlag(FLAG_COMMON_KEEP_MAJ_NONE);
        assertEquals("un chien andalou", frLang.normalizeSyntagmeText(sd));
        sd.setText("  \"Un\"  Chien, ANDALOU  ");
        assertEquals("\"un\" chien, andalou", frLang.normalizeSyntagmeText(sd));
        
        sd.selectFlag(FLAG_COMMON_KEEP_MAJ_ALL);
        assertEquals("\"Un\" Chien, ANDALOU", frLang.normalizeSyntagmeText(sd));
        
        sd.selectFlag(FLAG_COMMON_KEEP_MAJ_INSIDE);
        assertEquals("\"un\" Chien, ANDALOU", frLang.normalizeSyntagmeText(sd));
        // Ne touche pas la casse du premier mot si il a une casse speciale
        sd.setText("  \"ROBERT\" lE Chien, ANDALOU  ");
        assertEquals("\"ROBERT\" lE Chien, ANDALOU", frLang.normalizeSyntagmeText(sd));
        // Meme chose sans caractere parasyte
        sd.setText("ROBERT lE Chien, ANDALOU");
        assertEquals("ROBERT lE Chien, ANDALOU", frLang.normalizeSyntagmeText(sd));
        // Avec un seul mot
        sd.setText("ROBERT");
        assertEquals("ROBERT", frLang.normalizeSyntagmeText(sd));
        sd.setText("RoberT");
        assertEquals("RoberT", frLang.normalizeSyntagmeText(sd));
        sd.setText("Robert");
        assertEquals("robert", frLang.normalizeSyntagmeText(sd));
        // Plusieurs mots
        sd.setText("RoberT chien");
        assertEquals("RoberT chien", frLang.normalizeSyntagmeText(sd));
        sd.setText("Robert chien");
        assertEquals("robert chien", frLang.normalizeSyntagmeText(sd));
        
        
        // Gestion par défaut en phase remplacement
        sd = getNewSDSNominal();
        sd.selectFlag(FLAG_COMMON_PHASE_REPL);
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_NONE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_INSIDE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_ALL));
        sd.setText("un chien");
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_NONE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_INSIDE));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_ALL));
        for (String text : Arrays.asList("Un chien", "un Chien", "UN CHIEN", "un chIen")) {
            sd.setText(text);
            assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_NONE));
            assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_INSIDE));
            assertEquals(SyntagmeFlagState.ON, sd.getFlagState(FLAG_COMMON_KEEP_MAJ_ALL));
        }
    }
    
    @Test
    public void testHAspireFlags() {
        SyntagmeDefinition sd = getNewSDSNominal();
        sd.update();
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_HMUET));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_HASPIRE));
        sd.setText("un chien");
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_HMUET));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_HASPIRE));
        sd.setText("une hache");
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_HMUET));
        assertEquals(SyntagmeFlagState.DISABLED, sd.getFlagState(FLAG_COMMON_HASPIRE));
        sd.setText("hache");
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_HMUET));
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_HASPIRE));
        sd.setText("Hache");
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_HMUET));
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_HASPIRE));
        sd.setText("« Hache !!");
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_HMUET));
        assertEquals(SyntagmeFlagState.OFF, sd.getFlagState(FLAG_COMMON_HASPIRE));

    }
    
    @Test
    public void testAlterReplacementText() {
        // Le / la general
        SyntagmeDefinition chien = getNewSDNom("chien");
        SyntagmeDefinition amour = getNewSDNom("amour");
        assertAlterReplacementTextEquals(null, null, "le ", chien);
        assertAlterReplacementTextEquals("l'", null, "le ", amour);
        assertAlterReplacementTextEquals(null, null, "l'", amour);
        assertAlterReplacementTextEquals("le ", null, "l'", chien);
        SyntagmeDefinition chienne = getNewSDNom("chienne", FLAG_NOM_CTX_GENRE_FEMININ);
        SyntagmeDefinition asperge = getNewSDNom("asperge", FLAG_NOM_CTX_GENRE_FEMININ);
        assertAlterReplacementTextEquals(null, null, "la ", chienne);
        assertAlterReplacementTextEquals("la ", null, "l'", chienne);
        assertAlterReplacementTextEquals("l'", null, "la ", asperge);
        assertAlterReplacementTextEquals(null, null, "l'", asperge);
        SyntagmeDefinition education = getNewSDNom("éducation", FLAG_NOM_CTX_GENRE_FEMININ);
        assertAlterReplacementTextEquals("l'", null, "la ", education);
        SyntagmeDefinition homme = getNewSDNom("homme");
        SyntagmeDefinition hache = getNewSDNom("hache", FLAG_NOM_CTX_GENRE_FEMININ, FLAG_COMMON_HASPIRE);
        SyntagmeDefinition yolo = getNewSDNom("yolo");
        assertAlterReplacementTextEquals("l'", null, "le ", homme);
        assertAlterReplacementTextEquals(null, null, "l'", homme);
        assertAlterReplacementTextEquals(null, null, "la ", hache);
        assertAlterReplacementTextEquals("la ", null, "l'", hache);
        assertAlterReplacementTextEquals(null, null, "le ", yolo);
        assertAlterReplacementTextEquals("le ", null, "l'", yolo);
        
        // Le / la mots epicenes
        SyntagmeDefinition comptable = getNewSDNom("comptable", FLAG_NOM_CTX_GENRE_EPICENE);
        SyntagmeDefinition artiste = getNewSDNom("artiste", FLAG_NOM_CTX_GENRE_EPICENE);
        assertAlterReplacementTextEquals(null, null, "la·le ", comptable);
        assertAlterReplacementTextEquals("l'", null, "la·le ", artiste);
        assertAlterReplacementTextEquals(null, null, "l'", artiste);
        assertAlterReplacementTextEquals("la·le ", null, "l'", comptable);
        
        // Plusieurs mots precedents : pas de pb
        assertAlterReplacementTextEquals(null, null, "abc def le ", chien);
        assertAlterReplacementTextEquals("abc def l'", null, "abc def le ", amour);
        assertAlterReplacementTextEquals(null, null, "abc def l'", amour);
        assertAlterReplacementTextEquals("abc def le ", null, "abc def l'", chien);

        // Casse spéciale du texte precedent
        assertAlterReplacementTextEquals(null, null, "Le ", chien);
        assertAlterReplacementTextEquals("L'", null, "Le ", amour);
        assertAlterReplacementTextEquals("L'", null, "LE ", amour);
        assertAlterReplacementTextEquals(null, null, "L'", amour);
        assertAlterReplacementTextEquals("Le ", null, "L'", chien);
        assertAlterReplacementTextEquals("CET ", null, "CE ", amour);
                
        // Caracteres parasites
        assertAlterReplacementTextEquals(null, null, "le «", chien);
        assertAlterReplacementTextEquals("l'", null, "le «", amour);
        assertAlterReplacementTextEquals(null, null, "l'«", amour);
        assertAlterReplacementTextEquals("le ", null, "l'«", chien);
        
        // 1ere lettre du mot remplaçant en majuscule : pas de pb
        SyntagmeDefinition chienMaj = getNewSDNom("Chien");
        SyntagmeDefinition amourMaj = getNewSDNom("Amour");
        SyntagmeDefinition educationMaj = getNewSDNom("Éducation", FLAG_NOM_CTX_GENRE_FEMININ);
        SyntagmeDefinition hommeMaj = getNewSDNom("Homme");
        SyntagmeDefinition hacheMaj = getNewSDNom("Hache", FLAG_NOM_CTX_GENRE_FEMININ, FLAG_COMMON_HASPIRE);
        SyntagmeDefinition yoloMaj = getNewSDNom("Yolo");
        assertAlterReplacementTextEquals(null, null, "le ", chienMaj);
        assertAlterReplacementTextEquals("le ", null, "l'", chienMaj);
        assertAlterReplacementTextEquals("l'", null, "le ", amourMaj);
        assertAlterReplacementTextEquals("l'", null, "la ", educationMaj);
        assertAlterReplacementTextEquals("l'", null, "le ", hommeMaj);
        assertAlterReplacementTextEquals("la ", null, "l'", hacheMaj);
        assertAlterReplacementTextEquals("le ", null, "l'", yoloMaj);
        
        // Caracteres parasites mot remplaçant
        SyntagmeDefinition educationX = getNewSDNom("« éducation »", FLAG_NOM_CTX_GENRE_FEMININ);
        SyntagmeDefinition hacheX = getNewSDNom("[[homme]]", FLAG_NOM_CTX_GENRE_FEMININ, FLAG_COMMON_HASPIRE);
        assertAlterReplacementTextEquals("l'", null, "la ", educationX);
        assertAlterReplacementTextEquals("la ", null, "l'", hacheX);
        
        // Pas de mot precedent : nouvelle phrase
        assertAlterReplacementTextEquals(null, "Chien", "", chien);
        assertAlterReplacementTextEquals(null, "Éducation", "", education);
        assertAlterReplacementTextEquals(null, "« Éducation »", "", educationX);
        

        // de / que
        assertAlterReplacementTextEquals(null, null, "de ", chien);
        assertAlterReplacementTextEquals(null, null, "que ", chien);
        assertAlterReplacementTextEquals("d'", null, "de ", amour);
        assertAlterReplacementTextEquals("qu'", null, "que ", amour);
        assertAlterReplacementTextEquals(null, null, "d'", amour);
        assertAlterReplacementTextEquals(null, null, "qu'", amour);
        assertAlterReplacementTextEquals("de ", null, "d'", chien);
        assertAlterReplacementTextEquals("que ", null, "qu'", chien);
        
        // la / le en tant que pronom avant verbe
        SyntagmeDefinition enfonce = getNewSDVerbe("enfonce");
        SyntagmeDefinition defonce = getNewSDVerbe("défonce");
        assertAlterReplacementTextEquals("l'", null, "la ", enfonce);
        assertAlterReplacementTextEquals("l'", null, "le ", enfonce);
        assertAlterReplacementTextEquals(null, null, "l'", enfonce);
        assertAlterReplacementTextEquals(null, null, "le ", defonce);
        assertAlterReplacementTextEquals(null, null, "la ", defonce);
        // Aucun moyen de savoir
        assertAlterReplacementTextEquals("le ", null, "l'", defonce);

        // je de ne me te se que lorsque puisque + verbe
        assertAlterReplacementTextEquals(null, null, "je ", defonce);
        assertAlterReplacementTextEquals(null, null, "de ", defonce);
        assertAlterReplacementTextEquals(null, null, "ne ", defonce);
        assertAlterReplacementTextEquals(null, null, "me ", defonce);
        assertAlterReplacementTextEquals(null, null, "te ", defonce);
        assertAlterReplacementTextEquals(null, null, "se ", defonce);
        assertAlterReplacementTextEquals(null, null, "que ", defonce);
        assertAlterReplacementTextEquals(null, null, "lorsque ", defonce);
        assertAlterReplacementTextEquals(null, null, "puisque ", defonce);
        assertAlterReplacementTextEquals("j'", null, "je ", enfonce);
        assertAlterReplacementTextEquals("d'", null, "de ", enfonce);
        assertAlterReplacementTextEquals("n'", null, "ne ", enfonce);
        assertAlterReplacementTextEquals("m'", null, "me ", enfonce);
        assertAlterReplacementTextEquals("t'", null, "te ", enfonce);
        assertAlterReplacementTextEquals("s'", null, "se ", enfonce);
        assertAlterReplacementTextEquals("qu'", null, "que ", enfonce);
        assertAlterReplacementTextEquals("lorsqu'", null, "lorsque ", enfonce);
        assertAlterReplacementTextEquals("puisqu'", null, "puisque ", enfonce);
        assertAlterReplacementTextEquals(null, null, "j'", enfonce);
        assertAlterReplacementTextEquals(null, null, "s'", enfonce);
        assertAlterReplacementTextEquals("je ", null, "j'", defonce);
        assertAlterReplacementTextEquals("se ", null, "s'", defonce);
        
        // du / au
        assertAlterReplacementTextEquals(null, null, "du ", chien);
        assertAlterReplacementTextEquals(null, null, "au ", chien);
        assertAlterReplacementTextEquals("du ", null, "de l'", chien);
        assertAlterReplacementTextEquals("au ", null, "à l'", chien);
        assertAlterReplacementTextEquals("de l'", null, "du ", amour);
        assertAlterReplacementTextEquals("à l'", null, "au ", amour);
        assertAlterReplacementTextEquals(null, null, "de l'", amour);
        assertAlterReplacementTextEquals(null, null, "à l'", amour);

        assertAlterReplacementTextEquals("du·de la ", null, "de l'", comptable);
        assertAlterReplacementTextEquals("à la·le ", null, "à l'", comptable);
        assertAlterReplacementTextEquals(null, null, "de l'", artiste);
        assertAlterReplacementTextEquals(null, null, "à l'", artiste);
        
        // ma ta sa
        assertAlterReplacementTextEquals(null, null, "ma ", chienne);
        assertAlterReplacementTextEquals(null, null, "ta ", chienne);
        assertAlterReplacementTextEquals(null, null, "sa ", chienne);
        assertAlterReplacementTextEquals("mon ", null, "ma ", asperge);
        assertAlterReplacementTextEquals("ton ", null, "ta ", asperge);
        assertAlterReplacementTextEquals("son ", null, "sa ", asperge);
        assertAlterReplacementTextEquals("ma ", null, "mon ", chienne);
        assertAlterReplacementTextEquals("ta ", null, "ton ", chienne);
        assertAlterReplacementTextEquals("sa ", null, "son ", chienne);
        assertAlterReplacementTextEquals(null, null, "mon ", asperge);
        assertAlterReplacementTextEquals(null, null, "ton ", asperge);
        assertAlterReplacementTextEquals(null, null, "son ", asperge);
        assertAlterReplacementTextEquals(null, null, "son ", chien);
        
        // ce
        assertAlterReplacementTextEquals(null, null, "ce ", chien);
        assertAlterReplacementTextEquals("ce ", null, "cet ", chien);
        assertAlterReplacementTextEquals("cet ", null, "ce ", amour);
        assertAlterReplacementTextEquals(null, null, "cet ", amour);
        
        // à le / de le / à les / de les pour syntagmes nominaux
        SyntagmeDefinition leChien = getNewSDSNominal("le chien");
        SyntagmeDefinition laVie = getNewSDSNominal("la vie");
        SyntagmeDefinition lesChiens = getNewSDSNominal("les chiens");
        assertAlterReplacementTextEquals(null, null, "de ", laVie);
        assertAlterReplacementTextEquals("du ", " chien", "de ", leChien);
        assertAlterReplacementTextEquals(null, null, "à ", laVie);
        assertAlterReplacementTextEquals("au ", " chien", "à ", leChien);
        assertAlterReplacementTextEquals("des ", " chiens", "de ", lesChiens);
        assertAlterReplacementTextEquals("aux ", " chiens", "à ", lesChiens);
        // l'inverse n'est pas possible

        
    }
    
    public void assertAlterReplacementTextEquals(
            String expectedPrecedingText, String expectedReplacementText, 
            String precedingText, SyntagmeDefinition replacement) {
        List<TextRange> precedingWords = frLang.extractWords(precedingText);
        System.out.println(precedingWords);
        ReplaceContext rctx = new ReplaceContext(replacement, precedingWords);
        frLang.alterReplacementText(rctx);
        assertEquals(expectedPrecedingText, rctx.getNewTotalPrecedingText());
        if (expectedReplacementText != null) {
            assertEquals(expectedReplacementText, rctx.getFinalReplacementText());
        }
    }
    
    @Test
    @Ignore("For manual testing")
    public void testJsonSerialization() {
        String json = frLang.toJson(true);
        System.out.println(json);
    }
    
    @Test
    public void testGetSDefNomHTMLInfo() {
        SyntagmeDefinition sd = getNewSDNom();
        SyntagmeReplacementDefinition sdr = sd.getReplacementDefinition();
        
        sd.resetChosenFlags(Arrays.asList(
                FLAG_NOM_CTX_NOMBRE_SINGULIER, FLAG_NOM_CTX_GENRE_FEMININ,
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_NONE,
                FLAG_NOM_TYPE_ANIME));
        sd.update();
        
        assertSDefCtxHTMLInfoEquals(
                " <li>Indications genre : <em class=\"flag flag-cgf\">féminin</em></li>\n" +
                " <li>Indications nombre : <em class=\"flag flag-cns\">singulier</em></li>\n" +
                " <li>Nom introduit par un <em class=\"flag flag-cdi\">déterminant indéfini</em></li>\n" +
                " <li><em class=\"flag flag-cc0\">Non suivi de compléments</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Référents potentiels</em> : <em class=\"flag flag-cr0\">aucun</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>De type <em class=\"flag flag-ta\">animé</em></li>\n" +
                " <li><em class=\"flag flag-c1\">Avec</em> ou <em class=\"flag flag-c0\">sans compléments</em></li>\n",
                sd);
        
        sd.selectFlag(FLAG_NOM_CTX_NOMBRE_PLURIEL);
        sd.selectFlag(FLAG_NOM_CTX_GENRE_UNKNOWN);
        sd.selectFlag(FLAG_NOM_CTX_DETERMINANT_POSS);
        sd.selectFlag(FLAG_NOM_CTX_COMPL_COMPL);
        sd.selectFlag(FLAG_COMMON_CTX_REF_2PS);
        sdr.selectFlag(FLAG_NOM_TYPE_INANIME);
        sdr.selectFlag(FLAG_NOM_TYPE_ABSTRAIT);
        
        assertSDefCtxHTMLInfoEquals(
                " <li>Indications genre : <em class=\"flag flag-cg0\">aucune</em></li>\n" +
                " <li>Indications nombre : <em class=\"flag flag-cnp\">pluriel</em></li>\n" +
                " <li>Nom introduit par un <em class=\"flag flag-cdi\">déterminant indéfini</em> et un <em class=\"flag flag-cdpo\">adjectif possessif</em></li>\n" +
                " <li><em class=\"flag flag-cc1\">Suivi de compléments</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Référents potentiels</em> : <em class=\"flag flag-cr2s\">2e pers. singulier</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>De type <em class=\"flag flag-ta\">animé</em>, <em class=\"flag flag-ti\">inanimé</em> ou <em class=\"flag flag-tabs\">abstrait</em></li>\n" +
                " <li><em class=\"flag flag-c0\">Sans compléments</em></li>\n",
                sd);
    }

    @Test
    public void testGetSDefSNominalHTMLInfo() {
        SyntagmeDefinition sd = getNewSDSNominal();
        SyntagmeReplacementDefinition sdr = sd.getReplacementDefinition();
        
        sd.resetChosenFlags(Arrays.asList(
                FLAG_NOM_CTX_NOMBRE_UNKNOWN, FLAG_NOM_CTX_GENRE_UNKNOWN,
                FLAG_NOM_CTX_COMPL_NONE, 
                FLAG_NOM_TYPE_INANIME, FLAG_SNOMINAL_DETERMINANT_DEF));
        sd.update();
        

        assertSDefCtxHTMLInfoEquals(
                " <li>Indications genre : <em class=\"flag flag-cg0\">aucune</em></li>\n" +
                " <li>Indications nombre : <em class=\"flag flag-cn0\">aucune</em></li>\n" +
                " <li><em class=\"flag flag-cc0\">Non suivi de compléments</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Référents potentiels</em> : <em class=\"flag flag-cr0\">aucun</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>De type <em class=\"flag flag-ti\">inanimé</em></li>\n" +
                " <li>Déterminant <em class=\"flag flag-dd\">défini</em></li>\n" +
                " <li><em class=\"flag flag-c1\">Avec</em> ou <em class=\"flag flag-c0\">sans compléments</em></li>\n",
                sd);
        
        sd.selectFlag(FLAG_NOM_CTX_NOMBRE_PLURIEL);
        sd.selectFlag(FLAG_COMMON_CTX_REF_1PS);
        sd.selectFlag(FLAG_COMMON_CTX_REF_2PP);
        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        sdr.selectFlag(FLAG_NOM_TYPE_ABSTRAIT);
        sdr.selectFlag(FLAG_SNOMINAL_DETERMINANT_UNDEF);
        sdr.unselectFlag(FLAG_NOM_COMPL_NONE);
        
        assertSDefCtxHTMLInfoEquals(
                " <li>Indications genre : <em class=\"flag flag-cg0\">aucune</em></li>\n" +
                " <li>Indications nombre : <em class=\"flag flag-cnp\">pluriel</em></li>\n" +
                " <li><em class=\"flag flag-cc0\">Non suivi de compléments</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Référents potentiels</em> : <em class=\"flag flag-cr1s\">1e pers. singulier</em>, <em class=\"flag flag-cr3\">3e pers.</em>/<em class=\"flag flag-cr3pns\">singulier</em>/<em class=\"flag flag-cr3pta\">animé</em>, <em class=\"flag flag-cr2p\">2e pers. pluriel</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>De type <em class=\"flag flag-ti\">inanimé</em> ou <em class=\"flag flag-tabs\">abstrait</em></li>\n" +
                " <li>Déterminant <em class=\"flag flag-di\">indéfini</em> ou <em class=\"flag flag-dd\">défini</em></li>\n" +
                " <li><em class=\"flag flag-c1\">Avec compléments</em></li>\n",
                sd);
    }

    @Test
    public void testGetSDefVerbeHTMLInfo() {
        SyntagmeDefinition sd = getNewSDVerbe();
        SyntagmeReplacementDefinition sdr = sd.getReplacementDefinition();
        
        sd.resetChosenFlags(Arrays.asList(
                FLAG_VERBE_CTX_SUJET_PERS_2S,
                FLAG_VERBE_CTX_COD_NONE,
                FLAG_VERBE_FORME_IMPARFAIT));
        sd.update();
        

        assertSDefCtxHTMLInfoEquals(
                " <li>Sujet : <em class=\"flag flag-cs2s\">2ème personne du singulier</em></li>\n" +
                " <li><em class=\"flag flag-ccod0\">Pas de COD dans le contexte</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Autres référents potentiels</em> : <em class=\"flag flag-cr0\">aucun</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>À l'<em class=\"flag flag-fimpa\">imparfait</em></li>\n" +
                " <li>Sans <em class=\"flag flag-neg0\">marques de négation</em></li>\n" +
                " <li><em class=\"flag flag-cc0\">Sans compléments circonstanciels</em></li>\n",
                sd);
        
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_G_0);
        sd.setText("aaaa aaa");
        sd.selectFlag(FLAG_VERBE_CTX_COD_AFTER);
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_TYPE_ANIME);
        sdr.selectFlag(FLAG_VERBE_FORME_PRESENT);
        sdr.selectFlag(FLAG_VERBE_FORME_PASSECOMPOSE);
        
        assertSDefCtxHTMLInfoEquals(
                " <li>Sujet : <em class=\"flag flag-cs2s\">2ème personne du singulier</em></li>\n" +
                " <li><em class=\"flag flag-ccoda\">Suivi d'un COD</em> de type <em class=\"flag flag-ccodta\">animé</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Autres référents potentiels</em> : <em class=\"flag flag-cr0\">aucun</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>Au <em class=\"flag flag-fpres\">présent</em>, au <em class=\"flag flag-fpco\">passé composé</em> ou à l'<em class=\"flag flag-fimpa\">imparfait</em></li>\n" +
                " <li>Peut <em class=\"flag flag-neg1\">contenir les marques de négation</em></li>\n" +
                " <li><em class=\"flag flag-cc1\">Avec</em> ou <em class=\"flag flag-cc0\">sans compléments circonstanciels</em></li>\n",
                sd);
        
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_G_M);
        sd.selectFlag(FLAG_VERBE_CTX_COD_BEFORE);
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_1S);
        sd.selectFlag(FLAG_COMMON_CTX_REF_2PS);
        sdr.unselectFlag(FLAG_VERBE_FORME_PASSECOMPOSE);
        sdr.unselectFlag(FLAG_VERBE_CC_WITHOUT);
        sdr.unselectFlag(FLAG_VERBE_NEGATION_WITH);
        
        assertSDefCtxHTMLInfoEquals(
                " <li>Sujet : <em class=\"flag flag-cs2s\">2ème personne du singulier</em>, <em class=\"flag flag-csgm\">masculin</em></li>\n" +
                " <li><em class=\"flag flag-ccodb\">COD pronom en amont</em> à la <em class=\"flag flag-ccod1s\">1ère personne du singulier</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Autres référents potentiels</em> : <em class=\"flag flag-cr2s\">2e pers. singulier</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>Au <em class=\"flag flag-fpres\">présent</em> ou à l'<em class=\"flag flag-fimpa\">imparfait</em></li>\n" +
                " <li>Sans <em class=\"flag flag-neg0\">marques de négation</em></li>\n" +
                " <li><em class=\"flag flag-cc1\">Avec compléments circonstanciels</em></li>\n",
                sd);
        
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_G_F);
        sd.selectFlag(FLAG_VERBE_CTX_SUJET_PERS_3P);
        sd.selectFlag(FLAG_VERBE_CTX_PREFLECHI_YES);
        sd.selectFlag(FLAG_VERBE_CTX_CODINFO_PERS_3S);
        sd.selectFlag(FLAG_COMMON_CTX_REF_3P);
        sd.selectFlag(FLAG_COMMON_CTX_REF3P_NB_P);
        sd.selectFlag(FLAG_COMMON_CTX_REF3P_T_I);
        sd.selectFlag(FLAG_COMMON_CTX_REF3P_G_F);
        sdr.unselectFlag(FLAG_VERBE_FORME_IMPARFAIT);
        sdr.unselectFlag(FLAG_VERBE_CC_WITH);
        sdr.selectFlag(FLAG_VERBE_CC_WITHOUT);
        sdr.unselectFlag(FLAG_VERBE_NEGATION_WITHOUT);
        sdr.selectFlag(FLAG_VERBE_NEGATION_WITH);
        
        assertSDefCtxHTMLInfoEquals(
                " <li>Sujet : <em class=\"flag flag-csta\">animé</em> <em class=\"flag flag-csgf\">féminin</em> pluriel</li>\n" +
                " <li><em class=\"flag flag-cpr1\">Pronom réflechi dans le contexte</em></li>\n" +
                " <li><em class=\"flag flag-ccodb\">COD pronom en amont</em> à la <em class=\"flag flag-ccod3s\">3ème personne du singulier</em>, de type <em class=\"flag flag-ccodta\">animé</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Autres référents potentiels</em> : <em class=\"flag flag-cr2s\">2e pers. singulier</em>, <em class=\"flag flag-cr3\">3e pers.</em>/<em class=\"flag flag-cr3pnp\">pluriel</em>/<em class=\"flag flag-cr3pgf\">féminin</em>/<em class=\"flag flag-cr3pti\">inanimé</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>Au <em class=\"flag flag-fpres\">présent</em></li>\n" +
                " <li>Doit <em class=\"flag flag-neg1\">contenir les marques de négation</em></li>\n" +
                " <li><em class=\"flag flag-cc0\">Sans compléments circonstanciels</em></li>\n",
                sd);
        
        sd = getNewSDVerbe();
        sdr = sd.getReplacementDefinition();
        
        sd.resetChosenFlags(Arrays.asList(
                FLAG_VERBE_CTX_SUJET_PERS_3SM,
                FLAG_VERBE_CTX_COD_NONE,
                FLAG_VERBE_CTX_PPASSE_AUX_AVOIR));
        sd.setText("aaa");

        assertSDefCtxHTMLInfoEquals(
                " <li>Sujet : <em class=\"flag flag-cs3sm\">3ème personne indéfini (\"on\")</em></li>\n" +
                " <li><em class=\"flag flag-ccod0\">Pas de COD dans le contexte</em></li>\n" +
                " <li>Contexte participe passé : <em class=\"flag flag-cppaa\">temps composé voix active (aux. avoir)</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Autres référents potentiels</em> : <em class=\"flag flag-cr0\">aucun</em></li>\n",
                sd);
        assertSDefReplHTMLInfoEquals(
                " <li>Au <em class=\"flag flag-fppas\">participe passé</em></li>\n" +
                " <li>Sans <em class=\"flag flag-neg0\">marques de négation</em></li>\n" +
                " <li><em class=\"flag flag-cc0\">Sans compléments circonstanciels</em></li>\n",
                sd);
        
        sd.selectFlag(FLAG_VERBE_CTX_PPASSE_AUX_ETRE);
        assertSDefCtxHTMLInfoEquals(
                " <li>Sujet : <em class=\"flag flag-cs3sm\">3ème personne indéfini (\"on\")</em></li>\n" +
                " <li><em class=\"flag flag-ccod0\">Pas de COD dans le contexte</em></li>\n" +
                " <li>Contexte participe passé : <em class=\"flag flag-cppaepc\">passif agentif</em></li>\n" +
                " <li><em class=\"attr attr-cref\">Autres référents potentiels</em> : <em class=\"flag flag-cr0\">aucun</em></li>\n",
                sd);
    }
    
    protected void assertSDefCtxHTMLInfoEquals(String expectedItems, SyntagmeDefinition sd) {
        String html = frLang.getSDefContextHTMLInfo(sd).outerHtml();
        System.out.println(sd);
        System.out.println(html);
        assertEquals("<ul>\n" + expectedItems + "</ul>", html);
    }
    protected void assertSDefReplHTMLInfoEquals(String expectedItems, SyntagmeDefinition sd) {
        String html = frLang.getSDefReplacementHTMLInfo(sd.getReplacementDefinition()).outerHtml();
        System.out.println(sd.getReplacementDefinition());
        System.out.println(html);
        assertEquals("<ul>\n" + expectedItems + "</ul>", html);
    }
}
