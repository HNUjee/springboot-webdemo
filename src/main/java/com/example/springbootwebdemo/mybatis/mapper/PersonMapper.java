package com.example.springbootwebdemo.mybatis.mapper;

import com.example.springbootwebdemo.mybatis.domain.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PersonMapper {

    List<Person> getPerson();

    @Insert("insert into person" +
            "(name, age)" +
            "values" +
            "(#{name}, #{age})")
    void insertPerson(@Param(value = "name") String name, @Param(value = "age") int age);
}
