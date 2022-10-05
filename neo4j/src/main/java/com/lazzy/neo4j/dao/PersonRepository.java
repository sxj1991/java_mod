package com.lazzy.neo4j.dao;


import com.lazzy.neo4j.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

}