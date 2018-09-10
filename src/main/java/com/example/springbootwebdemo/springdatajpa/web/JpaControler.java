package com.example.springbootwebdemo.springdatajpa.web;

import com.example.springbootwebdemo.springdatajpa.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@EnableJpaRepositories
@RestController
public class JpaControler {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserServiceImpl userService;
    @GetMapping("users")
    public List findUserList(){
        return userRepository.findAll();
    }
    @PostMapping("users/add")
    public User addUser(@RequestParam("username")String username, @RequestParam("name") String name,
                        @RequestParam("password") String password){
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(password);
        return userRepository.save(user);
    }
    @GetMapping("users/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findOne(id);
    }
    @PutMapping("users/{id}")
    public User updateUserById(@PathVariable Long id, @PathVariable("name") String name){
        User user = userRepository.findOne(id);//先查出来，否则修改的时候会将其他request中没有参数也给覆盖
        user.setName(name);
        return userRepository.save(user);
    }
    @DeleteMapping("users/{id}")
    public ResultBean delUserById(@PathVariable Long id){
        userRepository.delete(id);
        return new SuccessBean();
    }
    @GetMapping("users/username/{username}")
    public List findByUsername(@PathVariable("username") String username){
        return userRepository.findByUsername(username);
    }
    @PostMapping("users/addMore")
    public void addMore(){
        userService.addMoreUsers();
    }
    @PostMapping("users/addList")
    public void addMoreList(){
        userService.addMoreList();
    }
}



















