package com.example.forum.service;

import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;

import java.util.List;


public interface IPostService {

    PostModel save(PostModel postModel);

    List<PostModel> findByThread(ThreadModel thread);
}