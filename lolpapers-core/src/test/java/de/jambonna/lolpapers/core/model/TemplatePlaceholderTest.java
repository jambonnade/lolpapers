package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.Languages;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import static de.jambonna.lolpapers.core.model.lang.fr.FrLanguage.TYPE_NOM;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.jambonna.lolpapers.core.model.lang.fr.FrLanguage.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TemplatePlaceholderTest {
    
    private static Language frLang;

    @BeforeClass
    public static void setUpClass() {
        frLang = Languages.getInstance().getFr();
    }
    
    public SyntagmeDefinition getNewSDNom() {
        SyntagmeDefinition sd = new SyntagmeDefinition(frLang.getSyntagmeType(TYPE_NOM));
        return sd;
    }

    @Test
    public void testSetDefinitionSD() {
        SyntagmeDefinition sd = getNewSDNom();
        sd.resetChosenFlags(Arrays.asList(FLAG_NOM_CTX_NOMBRE_PLURIEL, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_NONE));
        sd.getReplacementDefinition().resetChosenFlags(Arrays.asList(
                FLAG_NOM_TYPE_INANIME, FLAG_NOM_TYPE_ABSTRAIT,
                FLAG_NOM_COMPL_COMPL));
        String txt = "bla  bla !";
        sd.setText(txt);
        String reprSd = sd.toString();
        
        TemplatePlaceholder tp = new TemplatePlaceholder();
        tp.setLanguage(frLang);
        tp.setDefinitionSD(sd);

        // Check it's the same after reloading
        String serDef = tp.getDefinition();
        String serRepl = tp.getDefinitionReplacement();
        assertEquals("n:cnombre,cdet,ccompl:cnp,cdi,cc0", serDef);
        assertEquals("n:type,compl:ti,tabs,c1", serRepl);
        assertEquals(txt, tp.getOrigText());
        
        tp = new TemplatePlaceholder();
        tp.setLanguage(frLang);
        tp.setDefinition(serDef);
        tp.setDefinitionReplacement(serRepl);
        tp.setOrigText(txt);
        assertEquals(reprSd, tp.getDefinitionSD().toString());
        assertEquals(serDef, tp.getDefinitionSD().serialize());
        assertEquals(serRepl, tp.getDefinitionSD().getReplacementDefinition().serialize());
        assertEquals(txt, tp.getDefinitionSD().getText());
    }
    

    @Test
    public void testSetAndValidateReplacement() throws UserException {
        SyntagmeDefinition sd = getNewSDNom();
        sd.resetChosenFlags(Arrays.asList(FLAG_NOM_CTX_NOMBRE_PLURIEL, 
                FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_NONE));
        sd.getReplacementDefinition().resetChosenFlags(Arrays.asList(
                FLAG_NOM_TYPE_ANIME));
        sd.setText("xxx");
        
        TemplatePlaceholder tp = new TemplatePlaceholder();
        tp.setLanguage(frLang);
        tp.setDefinitionSD(sd);
        
        // No text
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            // Error code for stype unavailability, but stands for invalid text here
            assertEquals("unavailable", ex.getCode());
        }
        
        // Empty text after normalization
        tp.setReplacementText(" ,  ! ");
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("unavailable", ex.getCode());
        }

        tp.setReplacementText("  bbb  ,   zéz ");
        tp.validateReplacement();
        // Also does the normalization
        String txt = "bbb , zéz";
        assertEquals(txt, tp.getReplacementText());
        // Field and sdef text should be the same
        assertEquals(txt, tp.getReplacementSD().getText());
        
        // No additionnal flags
        String strFlags = "";
        tp.setReplacementDefinitionFlags(strFlags);
        tp.validateReplacement();
        assertEquals(strFlags, tp.getReplacementDefinitionFlags());
        Set<String> flagsOn = new HashSet<>(Arrays.asList(
            FLAG_NOM_CTX_NOMBRE_PLURIEL, FLAG_NOM_CTX_GENRE_UNKNOWN,
            FLAG_NOM_CTX_DETERMINANT_UNDEF, FLAG_NOM_CTX_COMPL_NONE,
            FLAG_COMMON_CTX_REF_NONE, FLAG_COMMON_ELISION_OFF, 
            FLAG_COMMON_PHASE_REPL, FLAG_COMMON_REUSABLE_ON
        ));
        assertEquals(flagsOn, tp.getReplacementSD().getFlagsOn());
        assertEquals(txt, tp.getReplacementSD().getText());
        
        // Additional def flags allowed by orig replacement
        List<String> flags = new ArrayList<>();
        flags.add(FLAG_NOM_TYPE_ANIME);
        flags.add(FLAG_NOM_COMPL_COMPL);
        strFlags = String.join(",", flags);
        tp.setReplacementDefinitionFlags(strFlags);
        tp.validateReplacement();
        assertEquals(strFlags, tp.getReplacementDefinitionFlags());
        flagsOn.addAll(flags);
        assertEquals(flagsOn, tp.getReplacementSD().getFlagsOn());
        
        // Inexistant flags ignored
        flags.add("xxx");
        flags.add("zzz");
        flags.add("");
        strFlags = String.join(",", flags);
        tp.setReplacementDefinitionFlags(strFlags);
        tp.validateReplacement();
        assertEquals(strFlags, tp.getReplacementDefinitionFlags());
        assertEquals(flagsOn, tp.getReplacementSD().getFlagsOn());
        assertEquals(txt, tp.getReplacementSD().getText());
        
        // Doesn't validate if this special flag is added manually
        flags.add(FLAG_COMMON_REUSABLE_OFF);
        strFlags = String.join(",", flags);
        tp.setReplacementDefinitionFlags(strFlags);
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("not-reusable", ex.getCode());
        }
        assertEquals(strFlags, tp.getReplacementDefinitionFlags());
        flags.remove(FLAG_COMMON_REUSABLE_OFF);


        // Unallowed def flags by orig repl
        flags.remove(FLAG_NOM_TYPE_ANIME);
        flags.add(FLAG_NOM_TYPE_INANIME);
        strFlags = String.join(",", flags);
        tp.setReplacementDefinitionFlags(strFlags);
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("invalid-repl-flag", ex.getCode());
        }
        flagsOn.remove(FLAG_NOM_TYPE_ANIME);

        
        // Unset required def flags
        flags.remove(FLAG_NOM_TYPE_INANIME);
        tp.setReplacementText(" \"Homer / Simpson\" ");
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("unset-attr-ha", ex.getCode());
        }
        txt = "\"Homer / Simpson\"";
        assertEquals(txt, tp.getReplacementSD().getText());
        
        flags.add(FLAG_COMMON_KEEP_MAJ_ALL);
        flags.add(FLAG_COMMON_HMUET);
        strFlags = String.join(",", flags);
        tp.setReplacementDefinitionFlags(strFlags);
        tp.validateReplacement();
        assertEquals(strFlags, tp.getReplacementDefinitionFlags());
        flagsOn.add(FLAG_COMMON_KEEP_MAJ_ALL);
        flagsOn.add(FLAG_COMMON_HMUET);
        flagsOn.remove(FLAG_COMMON_ELISION_OFF);
        flagsOn.add(FLAG_COMMON_ELISION_ON);
        assertEquals(flagsOn, tp.getReplacementSD().getFlagsOn());
        assertEquals(txt, tp.getReplacementSD().getText());

        // Trying to add context flags (note : we can't check what happens
        // when a context attribute is overwritten because it depends the order
        // of internal Sets, so may or may not override ; this case just adds
        // a flag on a multi attribute)
        flags.add(FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        strFlags = String.join(",", flags);
        tp.setReplacementDefinitionFlags(strFlags);
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("invalid-ctx-repl-flag", ex.getCode());
        }
        
        // Changes on context flags
        tp.getReplacementSD().selectFlag(FLAG_NOM_CTX_GENRE_FEMININ);
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("invalid-ctx-repl-flag", ex.getCode());
        }
        tp.getReplacementSD().selectFlag(FLAG_NOM_CTX_GENRE_UNKNOWN);
        tp.getReplacementSD().selectFlag(FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        try {
            tp.validateReplacement();
            fail();
        } catch (UserException ex) {
            assertEquals("invalid-ctx-repl-flag", ex.getCode());
        }
        tp.getReplacementSD().unselectFlag(FLAG_NOM_CTX_DETERMINANT_ARTDEF);
        tp.validateReplacement();
        
    }
    
}
