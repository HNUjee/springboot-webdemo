package com.example.springbootwebdemo.springdatajpa.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ApiModel(value = "Persons", description = "用户实体类")//用于类
public class Person {
    @ApiModelProperty(value = "id", required = true,dataType = "Long") //用于方法，字段； 表示对model属性的说明或者数据操作更改
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = true, length = 20)
    private String name;

    @Column(name = "age", nullable = true, length = 4)
    private int age;
}
