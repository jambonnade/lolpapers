package de.jambonna.lolpapers.core.model.lang;

import de.jambonna.lolpapers.core.model.text.Text;
import de.jambonna.lolpapers.core.model.text.TextRange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Condition evaluated in the "update" process of a SyntagmeDefinition
 */
public interface SyntagmeDefinitionCondition {
    
    public abstract boolean evaluate(SyntagmeDefinition sd);

    
    public static class AllTrue implements SyntagmeDefinitionCondition {
        public static final String TYPE = "alltrue";
        
        private static final Logger logger = LoggerFactory.getLogger(AllTrue.class);

        private final String type;
        private final List<SyntagmeDefinitionCondition> subConditions;

        public AllTrue(List<? extends SyntagmeDefinitionCondition> subConditions) {
            // Type stored as attribute to be added in automatic serialization
            type = TYPE;
            this.subConditions = Collections.unmodifiableList(new ArrayList<>(subConditions));
        }

        @Override
        public boolean evaluate(SyntagmeDefinition sd) {
            int count = 0;
            for (SyntagmeDefinitionCondition c : subConditions) {
                count++;
                if (!c.evaluate(sd)) {
                    logger.debug("Condition {} false, result is false", count);
                    return false;
                }
            }
            logger.debug("No condition is false (over {}), result is true", count);
            return true;
        }
        
    } 
    
    public static class OneTrue implements SyntagmeDefinitionCondition {
        public static final String TYPE = "onetrue";

        private static final Logger logger = LoggerFactory.getLogger(OneTrue.class);

        private final String type;
        private final List<SyntagmeDefinitionCondition> subConditions;

        public OneTrue(List<? extends SyntagmeDefinitionCondition> subConditions) {
            // Type stored as attribute to be added in automatic serialization
            type = TYPE;
            this.subConditions = Collections.unmodifiableList(new ArrayList<>(subConditions));
        }

        @Override
        public boolean evaluate(SyntagmeDefinition sd) {
            int count = 0;
            for (SyntagmeDefinitionCondition c : subConditions) {
                count++;
                if (c.evaluate(sd)) {
                    logger.debug("Condition {} true, result is true", count);
                    return true;
                }
            }
            logger.debug("No condition is true (over {}), result is false", count);
            return false;
        }
        
    }
    
    public static class Flag implements SyntagmeDefinitionCondition {
        public static final String TYPE_DEF = "flagd";
        public static final String TYPE_REPL = "flagr";
        public static final String TYPE_ORIG_DEF = "flagod";
        public static final String TYPE_ORIG_REPL = "flagor";

        private static final Logger logger = LoggerFactory.getLogger(Flag.class);

        private final String type;
        private final String flag;
        private final boolean flagSet;

        public Flag(String flag) {
            this(TYPE_DEF, flag);
        }
        
        public Flag(String type, String flag) {
            this(type, flag, true);
        }
        
        public Flag(String type, String flag, boolean flagSet) {
            this.type = type;
            this.flag = flag;
            this.flagSet = flagSet;
        }
        
        

        @Override
        public boolean evaluate(SyntagmeDefinition sd) {
            boolean result = false;
            SyntagmeDefinitionAbstract theSd = sd;
            switch (type) {
                case TYPE_REPL:
                    theSd = sd.getReplacementDefinition();
                    break;
                case TYPE_ORIG_DEF:
                    theSd = sd.getOrigDefinition();
                    break;
                case TYPE_ORIG_REPL:
                    if (sd.getOrigDefinition() != null) {
                        theSd = sd.getOrigDefinition().getReplacementDefinition();
                    }
                    break;
            }
            if (theSd != null) {
                result = theSd.getFlagState(flag) == SyntagmeFlagState.ON;
            } else {
                logger.warn("No sdef");
            }

            logger.debug("{} {} is {}", type, flag, result ? "on" : "off");

            if (!flagSet) {
                result = !result;
            }
            logger.debug(".. result is : {}", result);
            return result;
        }
        
    }

//    public static class PossibleFlag implements SyntagmeDefinitionCondition {
//        public static final String TYPE = "pflag";
//
//        private static final Logger logger = LoggerFactory.getLogger(PossibleFlag.class);
//
//        private final String type;
//        private final String flag;
//
//        
//        public PossibleFlag(String flag) {
//            this.type = TYPE;
//            this.flag = flag;
//        }
//        
//        @Override
//        public boolean evaluate(SyntagmeDefinition sd) {
//            boolean result = false;
//            
//            logger.debug("Possible flag {} ?", flag);
//
//            SyntagmeFlagState state = sd.getFlagState(flag);
//            switch (state) {
//                case ON:
//                    result = true;
//                    logger.debug(" => Flag On, result {}", result);
//                    break;
//                case OFF:
//                    logger.debug(" => Flag Off, checking if attr set");
//                    SyntagmeAttribute attr = sd.getAttributeByFlagCode(flag);
//                    boolean oneSet = false;
//                    for (SyntagmeFlag f : attr.getFlags()) {
//                        if (sd.isFlagOn(f.getCode())) {
//                            oneSet = true;
//                            break;
//                        }
//                    }
//                    if (!oneSet) {
//                        result = true;
//                        logger.debug(" => Attr not set, result {}", result);
//                    }
//
//                    break;
//                default:
//                    logger.debug(" => Flag can't be possible, result {}", result);
//                    break;
//            }
//
//            return result;
//        }
//        
//    }
    
//    public static class Phase implements SyntagmeDefinitionCondition {
//        public static final String TYPE = "phase";
//        
//        private static final Logger logger = LoggerFactory.getLogger(Phase.class);
//
//        private final String type;
//        private final SyntagmeDefinitionAbstract.Phase phase;
//
//        public Phase(SyntagmeDefinitionAbstract.Phase phase) {
//            // Type stored as attribute to be added in automatic serialization
//            type = TYPE;
//            this.phase = phase;
//        }
//        
//        @Override
//        public boolean evaluate(SyntagmeDefinition sd) {
//            boolean result = sd.getPhase() == phase;
//            logger.debug("Sdef in phase {} ? result: {}", 
//                    phase, result);
//            return result;
//        }
//    }
//    
    public static class TextMatch implements SyntagmeDefinitionCondition {
        public static final String TYPE = "text";
        
        private static final Logger logger = LoggerFactory.getLogger(TextMatch.class);

        private final String type;
        private final Pattern pattern;
        private final boolean caseInsensitive;
        private final boolean withoutDiacritics;

        public TextMatch(String pattern, boolean caseInsensitive, boolean withoutDiacritics) {
            // Type stored as attribute to be added in automatic serialization
            type = TYPE;
            this.caseInsensitive = caseInsensitive;
            this.withoutDiacritics = withoutDiacritics;
            this.pattern = caseInsensitive ? 
                    Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) : 
                    Pattern.compile(pattern);
        }

        public TextMatch(String pattern) {
            this(pattern, false, false);
        }

        
        @Override
        public boolean evaluate(SyntagmeDefinition sd) {
            String text = sd.getText();
            if (text == null) {
                return false;
            }
            if (withoutDiacritics) {
                text = sd.getType().getLanguage().removeTextDiacritricalMarks(text);
            }
            Matcher m = pattern.matcher(text);
            boolean result = m.find();
            logger.debug("\"{}\" vs \"{}\" ? result: {}", 
                    text, pattern, result);
            return result;
        }
    }
    
    public static class PrecedingWords implements SyntagmeDefinitionCondition {
        public static final String TYPE = "pwords";
        
        private static final Logger logger = LoggerFactory.getLogger(PrecedingWords.class);

        private final String type;
        private final List<String> words;

        public PrecedingWords(Collection<String> words) {
            // Type stored as attribute to be added in automatic serialization
            type = TYPE;
            this.words = Collections.unmodifiableList(new ArrayList<>(words));
        }

        
        @Override
        public boolean evaluate(SyntagmeDefinition sd) {
            boolean result = false;
            
            Text ptext = sd.getPrecedingTextText();
            List<TextRange> pwords = ptext.getWords();
            int idxOffset = pwords.size() - words.size();
            if (idxOffset >= 0) {
                result = true;
                for (int n = 0; result && n < words.size(); n++) {
                    String word = words.get(n);
                    TextRange pword = pwords.get(n + idxOffset);
                    String lctext = pword.getLcText();
                    if (!lctext.equals(word)) {
                        result = false;
                    }
                }
            }
            logger.debug("Preceding text {} vs {} ? result: {}", 
                    ptext, words, result);
            return result;
        }

    }
}
