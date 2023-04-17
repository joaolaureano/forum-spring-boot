package com.example.forum.service;

import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IThreadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
        Optional<ThreadModel> threadModelOptional = this.threadRepository.findById(id);
        if(threadModelOptional.isEmpty())
            throw new EntityNotFoundException(ThreadModel.class.getSimpleName());
        return threadModelOptional.get();
    }

    @Override
    public List<ThreadModel> findAll(){
        return this.threadRepository.findAll();
    }
}
