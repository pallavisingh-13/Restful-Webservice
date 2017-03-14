package com.user.controller;

import com.user.entity.User;
import com.user.exception.ErrorResponse;
import com.user.exception.UserException;
import com.user.service.UserService;
import com.user.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("${microservice.contextPath}")
public class UserController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/userIds/{userIds}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserInfo(
            @PathVariable(value = "userIds") String userIds) {
        if (StringUtils.isEmpty(userIds)) {
            throw new UserException("'userIds' should not be empty or null");
        }
        return new ResponseEntity(userService.getUserInfo(userIds),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(
            @RequestBody User user,Errors errors) {
        log.info("User create: " );
        userValidator.validate(user, errors);
        List<ObjectError> allErrors = errors.getAllErrors();
        if (null != allErrors && allErrors.size() > 0) {
            return new ResponseEntity(allErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userService
                .createUser(user),HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(
            @RequestBody User user) {
        log.debug("updating user data : "
                + user.getId());

        return new ResponseEntity(userService
                .modifyUser(user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(
            @RequestParam(value = "id", required = true)
                    String id){
        log.debug("deleting user : " + id);
        userService.removeUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(UserException ex) {
        log.info("UserException occured :");
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

}
