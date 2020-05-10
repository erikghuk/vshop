package com.elg.vshop.validator.password;

import com.elg.vshop.service.security.jwt.dto.MessageClassForJson;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PasswordFormatValidator implements ConstraintValidator<PasswordFormat, String> {
    @Override
    public void initialize(PasswordFormat constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        URL resource = this.getClass().getClassLoader().getResource("messages.properties");
        Properties properties = new Properties();
        if (resource != null) {
            try {
                properties.load(new FileInputStream(resource.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MessageResolver resolver = new PropertiesMessageResolver(properties);
        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
/*
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),*/

                // no whitespace
                new WhitespaceRule()

        ));



        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;

    }
}
