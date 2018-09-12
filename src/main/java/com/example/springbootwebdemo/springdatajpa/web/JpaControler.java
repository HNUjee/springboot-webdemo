package com.example.springbootwebdemo.springdatajpa.web;

import com.example.springbootwebdemo.springdatajpa.dao.PersonRepository;
import com.example.springbootwebdemo.springdatajpa.domain.Person;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@ApiIgnore()用于类或者方法上，可以不被swagger显示在页面上
@Api(value = "JpaControler",tags = {"jap控制器"},description = "the pet API")
@RestController
public class JpaControler {

    @Autowired
    private PersonRepository personRepository;
   /* 注解@ApiResponse的包装类，数组结构。即使需要使用一个@ApiResponse注解，
     也需要将@ApiResponse注解包含在注解@ApiResponses内。
     */
   @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
           @ApiResponse(code = 500, message = "服务器内部异常"),
           @ApiResponse(code = 400, message = "权限不足") })
    @ApiOperation(value = "addPerson", notes = "添加用户")
    @ApiImplicitParam(name = "addPerson", type = "insert",required = true)
    @PostMapping(path = "addPerson")
    public int addPerson(Person person) {
        personRepository.save(person);
        return 1;
    }
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常", response = NullPointerException.class),
            @ApiResponse(code = 400, message = "权限不足")})
    @ApiOperation(value = "deletePerson", notes = "删除用户")
    @ApiImplicitParam(name = "id", type = "delete",required = true, dataType = "Long")
    @DeleteMapping(path = "deletePerson")
    public Person deletePerson(Long id) {
        personRepository.deleteById(id);
        return null;
    }

}



















