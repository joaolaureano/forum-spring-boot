package com.example.forum.service;

import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.exception.MappedEntityNotFoundException;
import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IPostRepository;
import com.example.forum.repository.IThreadRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService implements IPostService{

    private final IPostRepository postRepository;
    private final IThreadRepository threadRepository;

    PostService(IPostRepository postRepository, IThreadRepository threadRepository){
        this.postRepository = postRepository;
        this.threadRepository = threadRepository;
    }


    @Override
    public PostModel save(PostModel postModel) {
        this.threadRepository.findById(postModel.getThreadModel().getId()).orElseThrow(() -> new MappedEntityNotFoundException(PostModel.class.getSimpleName(), ThreadModel.class.getSimpleName(), postModel.getThreadModel().getId()));
        PostModel responseModel = this.postRepository.save(postModel);

        return responseModel;
    }

    @Override
    public List<PostModel> findByThread(ThreadModel thread) {
        return this.postRepository.findAllByThreadModelIdOrderByCreatedAt(thread.getId());
    }


}
