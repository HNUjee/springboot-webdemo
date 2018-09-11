package com.example.springbootwebdemo.springdatajpa.web;

import com.example.springbootwebdemo.springdatajpa.dao.PersonRepository;
import com.example.springbootwebdemo.springdatajpa.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "person")
@RestController
public class JpaControler {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping(path = "addPerson")
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @DeleteMapping(path = "deletePerson")
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}



















