package com.lazzy.doublesourcejpa.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "note")
@ToString
@Data
public class Note implements Serializable {
    @Id
    @Column(columnDefinition = "note_id")
    private String noteId;

    @Column(columnDefinition = "note_title")
    private String noteTitle;

    @Column(columnDefinition = "note_content")
    private String noteContent;
}
