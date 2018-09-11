package com.example.springbootwebdemo.mybatis.web;

import com.example.springbootwebdemo.mybatis.domain.Person;
import com.example.springbootwebdemo.mybatis.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("mybatis")
@RestController
public class MybatisController {
    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("getPerson")
    public String getPerson(){
        List<Person> persons = personMapper.getPerson();
        return persons.toString();
    }

    @RequestMapping("insertPerson")
    public void insertPerson(){
        personMapper.insertPerson("tom",20);
    }
}
