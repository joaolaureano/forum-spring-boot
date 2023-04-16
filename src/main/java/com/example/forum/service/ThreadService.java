package com.example.forum.service;

import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IThreadRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThreadService implements IThreadService{

    private final IThreadRepository threadRepository;

    ThreadService(IThreadRepository threadRepository){
        this.threadRepository = threadRepository;
    }

    @Override
    public void save(ThreadModel threadModel){
        this.threadRepository.save(threadModel);
    }

    @Override
    public ThreadModel findById(Integer id){
        return this.threadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ThreadModel.class.getSimpleName()));
    }

    @Override
    public List<ThreadModel> findAll(){
        return this.threadRepository.findAll();
    }
}
