package com.example.springbootwebdemo.mybatis.web;

import com.example.springbootwebdemo.mybatis.domain.Person;
import com.example.springbootwebdemo.mybatis.mapper.PersonMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@Api(value = "MybatisController|mybatis注解的控制器") //用来解释该类的作用
@RequestMapping("mybatis")
@RestController
public class MybatisController {
    @Autowired
    private PersonMapper personMapper;

    @ApiOperation(value="获取用户集合", notes="test: 仅1和2有正确返回") //方法的解释
    /*@ApiImplicitParam(paramType="query", name = "persons", value = "用户集合",
            required = true, dataType = "Integer")*/
    @PostMapping("getPerson")
    public String getPerson(){
        List<Person> persons = personMapper.getPerson();
        return persons.toString();
    }

    @PostMapping("insertPerson")
    public void insertPerson(){
        personMapper.insertPerson("tom",20);
    }
}
