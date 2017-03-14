package com.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.cassandra.mapping.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User {

    private String id;
    private String content;
    //private String message;

}
