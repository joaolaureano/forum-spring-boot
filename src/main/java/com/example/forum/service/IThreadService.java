package com.example.forum.service;

import com.example.forum.model.ThreadModel;

import java.util.List;

public interface IThreadService{

    void save(ThreadModel threadModel);
    ThreadModel findById(Integer id);
    List<ThreadModel> findAll();
}