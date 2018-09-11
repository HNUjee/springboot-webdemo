package com.example.springbootwebdemo.springdatajpa.dao;


import com.example.springbootwebdemo.springdatajpa.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
