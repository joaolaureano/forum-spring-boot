package com.example.forum.controller;

import com.example.forum.controller.dto.request.CreatePostModelRequestDTO;
import com.example.forum.exception.ExceptionEnum;
import com.example.forum.exception.MappedEntityNotFoundException;
import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IPostRepository;
import com.example.forum.repository.IThreadRepository;
import com.example.forum.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class PostControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private IThreadRepository threadRepository;

    @SpyBean
    private IPostRepository postRepository;
    @SpyBean
    private IPostService postService;


    @BeforeEach
    void setup(){
        this.postRepository.deleteAllInBatch();
        this.threadRepository.deleteAllInBatch();
        clearInvocations(postRepository,threadRepository);
        clearInvocations(postService);
    }


    @Test
    void get_returnsPostList_whenSuccess() throws Exception{

        ThreadModel threadModel = threadRepository.save(ThreadModel.builder().id(1).build());

        String commentary = "Test title";

        CreatePostModelRequestDTO request = CreatePostModelRequestDTO.builder()
                .commentary(commentary)
                .threadId(threadModel.getId())
                .build();

        postService.save(CreatePostModelRequestDTO.toModel(request));

        ResultActions response = mockMvc.perform(
                get("/post/{threadId}", String.valueOf(threadModel.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("postModelList", hasSize(1)));


        verify(postRepository, times(1)).findAllByThreadModelIdOrderByCreatedAt(threadModel.getId());
        verify(postService, times(1)).findByThread(ArgumentMatchers.any(ThreadModel.class));

    }

    @Test
    void get_returnsThreadListEmpty_whenSuccess() throws Exception{
        ThreadModel threadModel = threadRepository.save(ThreadModel.builder().id(1).build());

        ResultActions response = mockMvc.perform(
                get("/post/{threadId}", String.valueOf(threadModel.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("postModelList", hasSize(0)));


        verify(postRepository, times(1)).findAllByThreadModelIdOrderByCreatedAt(threadModel.getId());
        verify(postService, times(1)).findByThread(ArgumentMatchers.any(ThreadModel.class));

    }

    @Test
    void create_returnsCreatedThreadModel_whenSuccess() throws Exception{
        ThreadModel threadModel = threadRepository.save(ThreadModel.builder().id(1).build());

        String commentary = "Test title";

        CreatePostModelRequestDTO request = CreatePostModelRequestDTO.builder()
                .commentary(commentary)
                .threadId(threadModel.getId())
                .build();

        ResultActions response = mockMvc.perform(
                post("/post/").content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());

        verify(postRepository, times(1)).save(ArgumentMatchers.any(PostModel.class));
        verify(postService, times(1)).save(ArgumentMatchers.any(PostModel.class));


    }

    @Test
    void create_throwsMappedEntityNotFoundException_whenThreadModelNotExists() throws Exception{
        Integer threadId = -1;
        String commentary = "Test title";
        String expectedMessage = String.format(ExceptionEnum.MAPPED_ENTITY_NOT_FOUND.toString(),PostModel.class.getSimpleName(), ThreadModel.class.getSimpleName(), threadId);

        CreatePostModelRequestDTO request = CreatePostModelRequestDTO.builder()
                .commentary(commentary)
                .threadId(threadId)
                .build();

        ResultActions response = mockMvc.perform(
                post("/post/").content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest());
        response.andExpect(result ->
                assertTrue(result.getResolvedException() instanceof MappedEntityNotFoundException));
        response.andExpect(result -> assertEquals(result.getResolvedException().getMessage(), expectedMessage));

        verify(postService, times(1)).save(ArgumentMatchers.any(PostModel.class));


    }
}
