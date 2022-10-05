package com.lazzy.neo4j.controller;

import com.lazzy.neo4j.dao.PersonRepository;
import com.lazzy.neo4j.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("neo4j")
public class PersonController {
    @Resource
    private PersonRepository personRepository;

    @GetMapping("persons")
    public List<Person> getPersonsInfo(){
        return personRepository.findAll();
    }
}
