package com.user.validation;

import com.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        log.debug("User validator :"
                + " : validate fields which are required for user creation");
        if (StringUtils.isEmpty(user.getId())) {
            log.debug("User validator : User Id is a required field.");
            errors.rejectValue("id", "400",
                    "User Id is a required field.");
        }
    }
}
