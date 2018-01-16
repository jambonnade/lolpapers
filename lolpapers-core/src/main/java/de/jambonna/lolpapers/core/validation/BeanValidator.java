package de.jambonna.lolpapers.core.validation;

import java.util.Set;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;


/**
 * Encapsulates a bean Validator and provide tools for bean validation.
 */
public class BeanValidator {
    private static BeanValidator instance;
    private static ValidatorFactory vf;
    private Validator validator;

    
    public static synchronized ValidatorFactory getValidatorFactory() {
        if (vf == null) {
            Configuration c = Validation.byDefaultProvider().configure();
            CustomMessageInterpolator mi = new CustomMessageInterpolator();
            mi.setBaseMi(c.getDefaultMessageInterpolator());
            c.messageInterpolator(mi);
            vf = c.buildValidatorFactory();
        }
        return vf;
    }
    
    public static Validator getNewValidator() {
        return getValidatorFactory().getValidator();
    }
    
    public static BeanValidator getNewBeanValidator() {
        BeanValidator bv = new BeanValidator();
        bv.setValidator(getNewValidator());
        return bv;
    }
    
    public static BeanValidator getInstance() {
        if (instance == null) {
            instance = getNewBeanValidator();
        }
        return instance;
    }
    
    
    public <T> void validatePropertyValue(Class<T> cls, String prop, Object value)
            throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = 
                validator.validateValue(cls, prop, value);
        if (violations.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation cv : violations) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(prop);
                sb.append(": ");
                sb.append(cv.getMessage());
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }
    
    public Integer getPropertyMaxLength(Class<?> cls, String prop) {
        BeanDescriptor bd = validator.getConstraintsForClass(cls);
        PropertyDescriptor pd = bd.getConstraintsForProperty(prop);
        if (pd != null) {
            Set<ConstraintDescriptor<?>> cdSet = pd.getConstraintDescriptors();
            for (ConstraintDescriptor cd : cdSet) {
                if (cd.getAnnotation() instanceof javax.validation.constraints.Size) {
                    return (Integer)cd.getAttributes().get("max");
                }
            }
        }
        return null;
    }
    
    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

}
