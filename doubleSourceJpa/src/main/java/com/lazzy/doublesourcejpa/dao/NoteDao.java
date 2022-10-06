package com.lazzy.doublesourcejpa.dao;

import com.lazzy.doublesourcejpa.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NoteDao extends JpaRepository<Note,String>, JpaSpecificationExecutor<Note> {

    @Override
    List<Note> findAll();
}
