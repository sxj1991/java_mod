package com.lazzy.neo4j.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node(labels = "Person")
@Data
public class Person {

    @Id
    @GeneratedValue
    Long id;

    @Property(name = "name")
    private String name;
}