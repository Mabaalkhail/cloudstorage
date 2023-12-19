package com.udacity.jwdnd.course1.cloudstorage.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserEntity {

    private Integer userId;

    private String username;

    private String salt;

    private String password;

    private String firstName;

    private String lastName;

}
