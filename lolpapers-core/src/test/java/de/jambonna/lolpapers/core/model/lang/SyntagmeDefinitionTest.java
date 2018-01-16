package de.jambonna.lolpapers.core.model.lang;

import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.core.model.lang.fr.FrLanguage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class SyntagmeDefinitionTest {

    private static Language frLang;

    @BeforeClass
    public static void setUpClass() throws Exception {
        frLang = new FrLanguage();
    }
    
    public SyntagmeDefinition getNewSDNom() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(FrLanguage.TYPE_NOM));
        return sd;
    }
    public SyntagmeDefinition getNewSDSNominal() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(FrLanguage.TYPE_SNOMINAL));
        return sd;
    }

    @Test
    public void testSelectFlagGeneral() {
        final SyntagmeFlagState stOn = SyntagmeFlagState.ON;
        final SyntagmeFlagState stOff = SyntagmeFlagState.OFF;
        final SyntagmeFlagState stNa = SyntagmeFlagState.DISABLED;
        
        final String attrNCtxNb = FrLanguage.ATTR_NOM_CTX_NOMBRE;
        final String attrNCtxDet = FrLanguage.ATTR_NOM_CTX_DETERMINANT;
        final String attrNCtxRef = FrLanguage.ATTR_COMMON_CTX_REF;
        final String attrNCtxRef3Nb = FrLanguage.ATTR_COMMON_CTX_REF3P_NB;
        final String attrNCtxC = FrLanguage.ATTR_NOM_CTX_COMPL;
        final String attrNT = FrLanguage.ATTR_NOM_TYPE;
        final String attrNC = FrLanguage.ATTR_NOM_COMPL;
        final String attrCPh = FrLanguage.ATTR_COMMON_PHASE;
        
        final String flagNCtxNS = FrLanguage.FLAG_NOM_CTX_NOMBRE_SINGULIER;
        final String flagNCtxNP = FrLanguage.FLAG_NOM_CTX_NOMBRE_PLURIEL;
        final String flagNCtxG0 = FrLanguage.FLAG_NOM_CTX_GENRE_UNKNOWN;
        final String flagNCtxGM = FrLanguage.FLAG_NOM_CTX_GENRE_MASCULIN;
        final String flagNCtxGF = FrLanguage.FLAG_NOM_CTX_GENRE_FEMININ;
        final String flagNCtxGX = FrLanguage.FLAG_NOM_CTX_GENRE_EPICENE;
        final String flagNCtxDU = FrLanguage.FLAG_NOM_CTX_DETERMINANT_UNDEF;
        final String flagNCtxDD = FrLanguage.FLAG_NOM_CTX_DETERMINANT_ARTDEF;
        final String flagNCtxDP = FrLanguage.FLAG_NOM_CTX_DETERMINANT_POSS;
        final String flagCCtxR0 = FrLanguage.FLAG_COMMON_CTX_REF_NONE;
        final String flagCCtxR1S = FrLanguage.FLAG_COMMON_CTX_REF_1PS;
        final String flagCCtxR3 = FrLanguage.FLAG_COMMON_CTX_REF_3P;
        final String flagCCtx3PS = FrLanguage.FLAG_COMMON_CTX_REF3P_NB_S;
        final String flagCCtx3PP = FrLanguage.FLAG_COMMON_CTX_REF3P_NB_P;
        final String flagNTI = FrLanguage.FLAG_NOM_TYPE_INANIME;
        final String flagNTA = FrLanguage.FLAG_NOM_TYPE_ANIME;
        final String flagNTABS = FrLanguage.FLAG_NOM_TYPE_ABSTRAIT;
        final String flagNC0 = FrLanguage.FLAG_NOM_COMPL_NONE;
        final String flagNC1 = FrLanguage.FLAG_NOM_COMPL_COMPL;
        final String flagCPhD = FrLanguage.FLAG_COMMON_PHASE_DEF;
        final String flagCPhR = FrLanguage.FLAG_COMMON_PHASE_REPL;
        
        SyntagmeDefinition sd = getNewSDNom();
        SyntagmeDefinitionAbstract sdr = sd.getReplacementDefinition();        
//        SyntagmeDefinition sd2 = getNewSDNom();
//        SyntagmeDefinitionAbstract sdr2 = sd2.getReplacementDefinition();        
        

        // A first update is needed to get default state
//        sd.setText("xxx xxx");
        sd.update();
//        sd2.update();

        assertEquals(stOff, sd.getFlagState(flagNCtxNS));
        assertEquals(stOff, sd.getFlagState(flagNCtxNP));
        
        // No attribute chosen
        assertChosenAttributesEqual(sd);
        assertChosenFlagsEqual(sd);
        
        // Selecting sets the flag
        sd.selectFlag(flagNCtxNS);
        assertEquals(stOn, sd.getFlagState(flagNCtxNS));
        assertEquals(stOff, sd.getFlagState(flagNCtxNP));
        
        // Flag is chosen
        assertChosenAttributesEqual(sd, attrNCtxNb);
        assertChosenFlagsEqual(sd, flagNCtxNS);
        // Other flags on but not chosen
        assertEquals(stOn, sd.getFlagState(flagCCtxR0));

        // Flags are exclusive by default in context
        sd.selectFlag(flagNCtxNP);
        assertEquals(stOff, sd.getFlagState(flagNCtxNS));
        assertEquals(stOn, sd.getFlagState(flagNCtxNP));
        // Selecting again does nothing
        sd.selectFlag(flagNCtxNP);
        assertEquals(stOff, sd.getFlagState(flagNCtxNS));
        assertEquals(stOn, sd.getFlagState(flagNCtxNP));
        // Unselecting unsets the flag
        sd.unselectFlag(flagNCtxNP);
        assertEquals(stOff, sd.getFlagState(flagNCtxNS));
        assertEquals(stOff, sd.getFlagState(flagNCtxNP));
        // Unselecting again does nothing
        sd.unselectFlag(flagNCtxNP);
        assertEquals(stOff, sd.getFlagState(flagNCtxNS));
        assertEquals(stOff, sd.getFlagState(flagNCtxNP));
        
        // Attribute still chosen but not the flag
        assertChosenAttributesEqual(sd, attrNCtxNb);
        assertChosenFlagsEqual(sd);
        
        // This context attribute can be multiple
        assertEquals(stOff, sd.getFlagState(flagNCtxDU));
        assertEquals(stOff, sd.getFlagState(flagNCtxDD));
        assertEquals(stOff, sd.getFlagState(flagNCtxDP));
        sd.selectFlag(flagNCtxDU);
        assertEquals(stOn, sd.getFlagState(flagNCtxDU));
        assertEquals(stOff, sd.getFlagState(flagNCtxDD));
        assertEquals(stOff, sd.getFlagState(flagNCtxDP));
        sd.selectFlag(flagNCtxDD);
        assertEquals(stOn, sd.getFlagState(flagNCtxDU));
        assertEquals(stOn, sd.getFlagState(flagNCtxDD));
        assertEquals(stOff, sd.getFlagState(flagNCtxDP));
        sd.unselectFlag(flagNCtxDU);
        assertEquals(stOff, sd.getFlagState(flagNCtxDU));
        assertEquals(stOn, sd.getFlagState(flagNCtxDD));
        assertEquals(stOff, sd.getFlagState(flagNCtxDP));

                
        // Setting a disabled flag does nothing
        assertEquals(stNa, sd.getFlagState(flagCCtx3PP));
        sd.selectFlag(flagCCtx3PP);
        assertEquals(stNa, sd.getFlagState(flagCCtx3PP));
        
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet);
        assertChosenFlagsEqual(sd, flagNCtxDD);

        // Setting this flag now enables the previous ones
        assertEquals(stOn, sd.getFlagState(flagCCtxR0));
        assertEquals(stOff, sd.getFlagState(flagCCtxR1S));
        assertEquals(stOff, sd.getFlagState(flagCCtxR3));
        sd.selectFlag(flagCCtxR3);
        assertEquals(stOff, sd.getFlagState(flagCCtxR0));
        assertEquals(stOff, sd.getFlagState(flagCCtxR1S));
        assertEquals(stOn, sd.getFlagState(flagCCtxR3));
        // Previous attempt when flag was disabled didn't took it in count
        assertEquals(stOn, sd.getFlagState(flagCCtx3PS));
        assertEquals(stOff, sd.getFlagState(flagCCtx3PP));
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet, attrNCtxRef);
        assertChosenFlagsEqual(sd, flagNCtxDD, flagCCtxR3);
        
        sd.selectFlag(flagCCtx3PP);
        assertEquals(stOff, sd.getFlagState(flagCCtx3PS));
        assertEquals(stOn, sd.getFlagState(flagCCtx3PP));
        
        sd.selectFlag(flagCCtxR0);
        // Dependant flags disabled but still chosen
        assertEquals(stNa, sd.getFlagState(flagCCtx3PS));
        assertEquals(stNa, sd.getFlagState(flagCCtx3PP));
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet, attrNCtxRef, attrNCtxRef3Nb);
        assertChosenFlagsEqual(sd, flagNCtxDD, flagCCtxR0, flagCCtx3PP);

        // Reactiving this flag restores the previous choice on dependant flags
        sd.selectFlag(flagCCtxR3);
        assertEquals(stOff, sd.getFlagState(flagCCtx3PS));
        assertEquals(stOn, sd.getFlagState(flagCCtx3PP));
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet, attrNCtxRef, attrNCtxRef3Nb);
        assertChosenFlagsEqual(sd, flagNCtxDD, flagCCtxR3, flagCCtx3PP);

        // Definition flags are single value
        // These also impair replacement flags
        // (Need multiple words to have the two values)
        sd.setText("xxx xxx");
        assertEquals(stOff, sd.getFlagState(flagNC0));
        assertEquals(stOff, sd.getFlagState(flagNC1));
        assertEquals(stOn, sdr.getFlagState(flagNC0));
        assertEquals(stOn, sdr.getFlagState(flagNC1));
        sd.selectFlag(flagNC0);
        assertEquals(stOn, sd.getFlagState(flagNC0));
        assertEquals(stOff, sd.getFlagState(flagNC1));
        assertEquals(stOn, sdr.getFlagState(flagNC0));
        assertEquals(stOff, sdr.getFlagState(flagNC1));
        sd.selectFlag(flagNC1);
        assertEquals(stOff, sd.getFlagState(flagNC0));
        assertEquals(stOn, sd.getFlagState(flagNC1));
        assertEquals(stOff, sdr.getFlagState(flagNC0));
        assertEquals(stOn, sdr.getFlagState(flagNC1));
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet, attrNCtxRef, attrNCtxRef3Nb, attrNC);
        assertChosenFlagsEqual(sd, flagNCtxDD, flagCCtxR3, flagCCtx3PP, flagNC1);
        assertChosenAttributesEqual(sdr);
        assertChosenFlagsEqual(sdr);

        
        // Replacement flags are multi value
        // Unselecting a default flag sets the attribute as chosen and is no more set automatically
        sdr.unselectFlag(flagNC1);
        assertEquals(stOff, sd.getFlagState(flagNC0));
        assertEquals(stOn, sd.getFlagState(flagNC1));
        assertEquals(stOff, sdr.getFlagState(flagNC0));
        assertEquals(stOff, sdr.getFlagState(flagNC1));
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet, attrNCtxRef, attrNCtxRef3Nb, attrNC);
        assertChosenFlagsEqual(sd, flagNCtxDD, flagCCtxR3, flagCCtx3PP, flagNC1);
        assertChosenAttributesEqual(sdr, attrNC);
        assertChosenFlagsEqual(sdr);
        sd.unselectFlag(flagNC1);
        assertEquals(stOff, sd.getFlagState(flagNC0));
        assertEquals(stOff, sd.getFlagState(flagNC1));
        assertEquals(stOff, sdr.getFlagState(flagNC0));
        assertEquals(stOff, sdr.getFlagState(flagNC1));
        
        sdr.selectFlag(flagNC0);
        assertEquals(stOn, sdr.getFlagState(flagNC0));
        assertEquals(stOff, sdr.getFlagState(flagNC1));
        sdr.selectFlag(flagNC1);
        assertEquals(stOn, sdr.getFlagState(flagNC0));
        assertEquals(stOn, sdr.getFlagState(flagNC1));
        
        assertChosenAttributesEqual(sd, attrNCtxNb, attrNCtxDet, attrNCtxRef, attrNCtxRef3Nb, attrNC);
        assertChosenFlagsEqual(sd, flagNCtxDD, flagCCtxR3, flagCCtx3PP);
        assertChosenAttributesEqual(sdr, attrNC);
        assertChosenFlagsEqual(sdr, flagNC0, flagNC1);
        
        // Replacement set first : never takes in count definition
        assertEquals(stOff, sd.getFlagState(flagNTI));
        assertEquals(stOff, sd.getFlagState(flagNTA));
        assertEquals(stOff, sd.getFlagState(flagNTABS));
        assertEquals(stOff, sdr.getFlagState(flagNTI));
        assertEquals(stOff, sdr.getFlagState(flagNTA));
        assertEquals(stOff, sdr.getFlagState(flagNTABS));
        sdr.selectFlag(flagNTA);
        assertEquals(stOff, sd.getFlagState(flagNTI));
        assertEquals(stOff, sd.getFlagState(flagNTA));
        assertEquals(stOff, sd.getFlagState(flagNTABS));
        assertEquals(stOff, sdr.getFlagState(flagNTI));
        assertEquals(stOn, sdr.getFlagState(flagNTA));
        assertEquals(stOff, sdr.getFlagState(flagNTABS));
        sd.selectFlag(flagNTI);
        assertEquals(stOn, sd.getFlagState(flagNTI));
        assertEquals(stOff, sd.getFlagState(flagNTA));
        assertEquals(stOff, sd.getFlagState(flagNTABS));
        assertEquals(stOff, sdr.getFlagState(flagNTI));
        assertEquals(stOn, sdr.getFlagState(flagNTA));
        assertEquals(stOff, sdr.getFlagState(flagNTABS));

        // Possible to select vritual flags
        assertEquals(stOn, sd.getFlagState(flagCPhD));
        assertEquals(stOff, sd.getFlagState(flagCPhR));
        sd.selectFlag(flagCPhR);
        assertEquals(stOff, sd.getFlagState(flagCPhD));
        assertEquals(stOn, sd.getFlagState(flagCPhR));
        
        // Invalid flag throws exception
        try {
            sd.selectFlag("xxx");
            fail();
        } catch (IllegalArgumentException ex) {
        }
        try {
            sd.unselectFlag("xxx");
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        // Can't set context flags in repl
        try {
            sdr.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF_1PS);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        // Can set definition only flags
        sd.setText("Chien Chaud");
        assertEquals(stOff, sd.getFlagState(FrLanguage.FLAG_COMMON_KEEP_MAJ_INSIDE));
        sd.selectFlag(FrLanguage.FLAG_COMMON_KEEP_MAJ_INSIDE);
        assertEquals(stOn, sd.getFlagState(FrLanguage.FLAG_COMMON_KEEP_MAJ_INSIDE));
        assertEquals(stOff, sdr.getFlagState(FrLanguage.FLAG_COMMON_KEEP_MAJ_INSIDE));
        // Not in repl
        try {
            sdr.selectFlag(FrLanguage.FLAG_COMMON_KEEP_MAJ_INSIDE);
            fail();
        } catch (IllegalArgumentException ex) {
        }

    }
    
    protected void assertChosenAttributesEqual(SyntagmeDefinitionAbstract sd, String... attrs) {
        Set<String> attrSet = new HashSet<>(Arrays.asList(attrs));
        assertEquals(attrSet, sd.getChosenAttributes());
    }
    protected void assertChosenFlagsEqual(SyntagmeDefinitionAbstract sd, String... flags) {
        Set<String> flagsSet = new HashSet<>(Arrays.asList(flags));
        assertEquals(flagsSet, sd.getChosenFlags());
    }
    
    
    @Test
    public void testValidate() throws UserException {
        SyntagmeDefinition sd = getNewSDNom();
        SyntagmeDefinitionAbstract sdr = sd.getReplacementDefinition();
        sd.setText("zzz");
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cnombre", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_NOMBRE_SINGULIER);
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cgenre", ex.getCode());
        }

        // Automatic flags can set the attribute
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_NOMBRE_PLURIEL);
        assertTrue(sd.isFlagOn(FrLanguage.FLAG_NOM_CTX_GENRE_UNKNOWN));

        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cdet", ex.getCode());
        }

        // When unselecting, the attribute is still not set
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        sd.unselectFlag(FrLanguage.FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        assertFalse(sd.isFlagOn(FrLanguage.FLAG_NOM_CTX_DETERMINANT_ARTDEF));
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cdet", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_DETERMINANT_ARTDEF);

        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("repl-unset-attr-type", ex.getCode());
        }
        
        // When a selected flag becomes unavailable, but still "chosen" internally
        // consider the attribut not set
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF_1PP);
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF_NONE);
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_DETERMINANT_POSS);
        assertFalse(sd.isFlagEnabled(FrLanguage.FLAG_COMMON_CTX_REF_NONE));
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cref", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF_3P);
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("repl-unset-attr-type", ex.getCode());
        }

        // Unselecting a default flag makes the the attribute unset
        sd.unselectFlag(FrLanguage.FLAG_COMMON_CTX_REF3P_G_0);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cref3pg", ex.getCode());
        }

        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF3P_G_F);

        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("repl-unset-attr-type", ex.getCode());
        }
        
        // A default flag in repl can set the attribute
        sd.selectFlag(FrLanguage.FLAG_NOM_TYPE_INANIME);

        sd.validate();
        
        // Unselecting one of the 2 active flags is still valid
        sdr.unselectFlag(FrLanguage.FLAG_NOM_COMPL_COMPL);
        assertTrue(sdr.isFlagOn(FrLanguage.FLAG_NOM_COMPL_NONE));
        
        sd.validate();

        // But not the 2
        sdr.unselectFlag(FrLanguage.FLAG_NOM_COMPL_NONE);
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("repl-unset-attr-compl", ex.getCode());
        }
        
        // Changing phase is the same
        sd.selectFlag(FrLanguage.FLAG_COMMON_PHASE_REPL);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("repl-unset-attr-compl", ex.getCode());
        }

        sdr.selectFlag(FrLanguage.FLAG_NOM_COMPL_NONE);
        sd.validate();
    }
    
    @Test
    public void testValidateDefOnlyFlags() throws UserException {
        SyntagmeDefinition sd = getNewSDSNominal();
        sd.setText("zzzz");
        sd.getReplacementDefinition().selectFlag(FrLanguage.FLAG_NOM_TYPE_ANIME);
        
        sd.validate();
        
        sd.setText("Zzzz");
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-kmj", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_COMMON_REUSABLE_OFF);
        // Doesn't ask for definition flags anymore
        sd.validate();
        
        sd.setText("Huître");

        sd.validate();
        
        sd.selectFlag(FrLanguage.FLAG_COMMON_REUSABLE_ON);
        sd.selectFlag(FrLanguage.FLAG_COMMON_KEEP_MAJ_ALL);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-ha", ex.getCode());
        }

    }

    @Test
    public void testResetChosenFlags() throws UserException {
        SyntagmeDefinition sd = getNewSDNom();
        SyntagmeDefinitionAbstract sdr = sd.getReplacementDefinition();
        
        sd.resetChosenFlags(Arrays.asList(
                // Attr set and flag set too
                FrLanguage.ATTR_NOM_CTX_NOMBRE,
                // Attr set and no flag set, overriding the default
                FrLanguage.ATTR_COMMON_CTX_REF3P_G,
                // Ignored invalid attribute
                "chips",
                // Flag in definition
                FrLanguage.ATTR_NOM_COMPL,
                // Virtual attr ignored
                FrLanguage.ATTR_COMMON_PHASE
            ),
            Arrays.asList(
                FrLanguage.FLAG_NOM_CTX_NOMBRE_PLURIEL,
                // Invalid flags ignored safely,
                "zzz",
                // A chosen flag this actually unavailable
                FrLanguage.FLAG_COMMON_CTX_REF_NONE,
                // A flag that makes it unavailable + a flag without its attribute set
                FrLanguage.FLAG_NOM_CTX_DETERMINANT_POSS,
                // Multiple flags for this attribute is ok
                FrLanguage.FLAG_NOM_CTX_DETERMINANT_UNDEF,
                // Flag set for an unavailable flag
                FrLanguage.FLAG_COMMON_CTX_REF3P_NB_P,
                
                // Virtual flag ignored
                FrLanguage.FLAG_COMMON_ELISION_ON,
                
                // Multiple values for a single value attribute : keeps the last
                FrLanguage.FLAG_NOM_TYPE_INANIME,
                FrLanguage.FLAG_NOM_TYPE_ANIME,
                
                FrLanguage.FLAG_NOM_CTX_COMPL_NONE,
                FrLanguage.FLAG_NOM_CTX_COMPL_COMPL
            ));
        sdr.resetChosenFlags(Arrays.asList(
                // Invalid attributes ignored safely
                "xxx",
                FrLanguage.ATTR_NOM_CTX_GENRE,
                
                // Attr set overriding default from def
                FrLanguage.ATTR_NOM_TYPE
            ),
            Arrays.asList(
                "yyy",
                FrLanguage.FLAG_NOM_CTX_NOMBRE_PLURIEL,

                // Overrides default from context
                FrLanguage.FLAG_NOM_COMPL_COMPL
            ));
        
        // Needs an update
        sd.setText("zzz");        
        assertEquals("cnombre= cns_ cnp* cgenre: cg0+ cgf_ cgm_ cgx_ cdet= cdi* cdpa_ cdd_ cdpo* cref= cr0% cr1s_ cr2s_ cr3_ cr1p_ cr2p_ cref3pn= cr3pns# cr3pnp% cref3pg= cr3pg0# cr3pgf# cr3pgm# cref3pt: cr3pta# cr3pti# ccompl= cc0_ cc1* type= ta* ti_ tabs_ compl= c0_ c1# phase: def+ repl_ reu: reu0_ reu1+ kmj: kmj# kimj# nomj# ha: ha# hm# eli: eli0+ eli1_  [R] type= ta_ ti_ tabs_ compl= c0_ c1* ", sd.toString());
        
        // Unavailable chosen flag in an attribute makes it unset
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cref", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF_3P);
        assertEquals("cnombre= cns_ cnp* cgenre: cg0+ cgf_ cgm_ cgx_ cdet= cdi* cdpa_ cdd_ cdpo* cref= cr0# cr1s_ cr2s_ cr3* cr1p_ cr2p_ cref3pn= cr3pns_ cr3pnp* cref3pg= cr3pg0_ cr3pgf_ cr3pgm_ cref3pt: cr3pta+ cr3pti_ ccompl= cc0_ cc1* type= ta* ti_ tabs_ compl= c0_ c1# phase: def+ repl_ reu: reu0_ reu1+ kmj: kmj# kimj# nomj# ha: ha# hm# eli: eli0+ eli1_  [R] type= ta_ ti_ tabs_ compl= c0_ c1* ", sd.toString());

        // Missing flags in newly activated flags
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cref3pg", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF3P_G_0);
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("repl-unset-attr-type", ex.getCode());
        }
        
        sdr.selectFlag(FrLanguage.FLAG_NOM_TYPE_ANIME);
        sdr.selectFlag(FrLanguage.FLAG_NOM_TYPE_INANIME);
        
        sd.validate();
        
    }
    
    @Test
    public void testSerialize() throws SyntagmeUnserializeException, UserException {
        SyntagmeDefinition sd = getNewSDNom();
        sd.update();
        
        String repr = sd.toString();
        String ser = sd.serialize();
        String rser = sd.getReplacementDefinition().serialize();
        assertEquals("n::", ser);
        assertEquals("n::", rser);
        
        sd = SyntagmeDefinition.unserialize(ser, rser, frLang);
        sd.update();
        assertEquals(repr, sd.toString());
        
        sd.setText("zzz");
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_NOMBRE_SINGULIER);
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_DETERMINANT_POSS);
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF_3P);
        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF3P_NB_P);
        sd.unselectFlag(FrLanguage.FLAG_COMMON_CTX_REF3P_T_A);
        sd.selectFlag(FrLanguage.FLAG_NOM_TYPE_ANIME);
        sd.getReplacementDefinition().unselectFlag(FrLanguage.FLAG_NOM_COMPL_COMPL);
        
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cgenre", ex.getCode());
        }
        
        // We can serialize / unserialize unfinished/invalid configurations
        repr = sd.toString();
        ser = sd.serialize();
        rser = sd.getReplacementDefinition().serialize();
        
        // Note : virtual flags not serialized
        assertEquals("n:cnombre,cdet,cref,cref3pn,cref3pt,type:cns,cdd,cdpo,cr3,cr3pnp,ta", ser);
        assertEquals("n:compl:c0", rser);

        sd = SyntagmeDefinition.unserialize(ser, rser, frLang);
        sd.setText("zzz");
        assertEquals(repr, sd.toString());

        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cgenre", ex.getCode());
        }
        
        sd.selectFlag(FrLanguage.FLAG_NOM_CTX_NOMBRE_PLURIEL);
        try {
            sd.validate();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-cref3pt", ex.getCode());
        }

        sd.selectFlag(FrLanguage.FLAG_COMMON_CTX_REF3P_T_I);

        sd.validate();
        
        sd.getReplacementDefinition().selectFlag(FrLanguage.FLAG_NOM_TYPE_ABSTRAIT);
        
        // We can serialize / unserialize finished/valid configurations
        repr = sd.toString();
        ser = sd.serialize();
        rser = sd.getReplacementDefinition().serialize();
        
        assertEquals("n:cnombre,cdet,cref,cref3pn,cref3pt,type:cnp,cdd,cdpo,cr3,cr3pnp,cr3pti,ta", ser);
        assertEquals("n:type,compl:ta,tabs,c0", rser);

        sd = SyntagmeDefinition.unserialize(ser, rser, frLang);
        sd.setText("zzz");
        assertEquals(repr, sd.toString());
        
        sd.validate();

        // Invalid flags is ok
        sd = SyntagmeDefinition.unserialize(
                "n:cnombre,zzz,cdet,cref,cref3pn,cref3pt,type,xxx:aaa,cnp,cdd,cdpo,bb,cr3,cr3pnp,cr3pti,ta,ccc", 
                "n:type,x,compl,zz:ta,tabs,zzz,c0", frLang);
        sd.setText("zzz");
        assertEquals(repr, sd.toString());
        
        sd.validate();

    }
}
