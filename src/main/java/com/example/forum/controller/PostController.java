package com.example.forum.controller;

import com.example.forum.controller.dto.request.CreatePostModelRequestDTO;
import com.example.forum.controller.dto.response.PostModelListResponseDTO;
import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;
import com.example.forum.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final IPostService postService;

    PostController(IPostService postService){
        this.postService = postService;
    }

    @GetMapping(value = "/{threadId}" )
    public ResponseEntity<PostModelListResponseDTO> getById(
            @PathVariable(value = "threadId", required = true) Integer threadId
    ) throws EntityNotFoundException{
        List<PostModel> postModelList = postService.findByThread(ThreadModel.builder().id(threadId).build());

        return new ResponseEntity<>(PostModelListResponseDTO.toResponse(postModelList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePostModelRequestDTO createPostDTO){
        PostModel postModel   = CreatePostModelRequestDTO.toModel(createPostDTO);
        this.postService.save(postModel);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
