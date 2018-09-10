package com.example.springbootwebdemo.springdatajpa.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String name;
    private String password;
}
