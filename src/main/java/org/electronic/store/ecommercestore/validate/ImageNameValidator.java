package org.electronic.store.ecommercestore.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {
    private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public void initialize(ImageNameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.endsWith(".jpg") || s.endsWith(".jpeg") || s.endsWith(".png") || s.endsWith(".gif")){
            logger.info("Image name is valid");
            return true;
        }
        logger.error("Image name is invalid");
        return false;
    }
}
