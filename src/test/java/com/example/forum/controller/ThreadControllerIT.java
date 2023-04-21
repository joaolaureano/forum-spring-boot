package com.example.forum.controller;

import com.example.forum.controller.dto.request.CreateThreadModelRequestDTO;
import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.exception.ExceptionEnum;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IThreadRepository;
import com.example.forum.service.IThreadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ThreadControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private IThreadRepository threadRepository;
    @SpyBean
    private IThreadService threadService;


    @BeforeEach
    void setup(){
        this.threadRepository.deleteAllInBatch();
        clearInvocations(threadService,threadRepository);
    }


    @Test
    void get_returnsThread_whenSuccess() throws Exception{

        String title = "Test title";

        CreateThreadModelRequestDTO request = CreateThreadModelRequestDTO.builder()
                .title(title)
                .build();

        ThreadModel model = threadService.save(CreateThreadModelRequestDTO.toModel(request));

        ResultActions response = mockMvc.perform(
                get("/thread/{id}", String.valueOf(model.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        );

    response.andExpect(status().isOk());
        verify(threadRepository, times(1)).findById(model.getId());
        verify(threadService, times(1)).findById(model.getId());

    }

    @Test
    void get_throwEntityNotFoundException_whenThreadIdNotExists() throws Exception {

        Integer inexistentId = -1;
        String expectedMessage = String.format(ExceptionEnum.ENTITY_NOT_FOUND.toString(), ThreadModel.class.getSimpleName());

        ResultActions response = mockMvc.perform(
                get("/thread/{id}", String.valueOf(inexistentId))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), expectedMessage));

        verify(threadRepository, times(1)).findById(inexistentId);
        verify(threadService, times(1)).findById(inexistentId);

    }

    @Test
    void get_returnsThreadListEmpty_whenSuccess() throws Exception{

        ResultActions response = mockMvc.perform(
                get("/thread/").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$", hasSize(0)));

        verify(threadRepository, times(1)).findAll();
        verify(threadService, times(1)).findAll();

    }

    @Test
    void get_returnsThreadList_whenSuccess() throws Exception{

        String title = "Test title";

        CreateThreadModelRequestDTO request = CreateThreadModelRequestDTO.builder()
                .title(title)
                .build();

        threadService.save(CreateThreadModelRequestDTO.toModel(request));
        threadService.save(CreateThreadModelRequestDTO.toModel(request));

        ResultActions response = mockMvc.perform(
                get("/thread/").contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$", hasSize(2)));

        verify(threadRepository, times(1)).findAll();
        verify(threadService, times(1)).findAll();

    }

    @Test
    void create_returnsCreatedThreadModel_whenSuccess() throws Exception{
        String title = "Test title";

        CreateThreadModelRequestDTO request = CreateThreadModelRequestDTO.builder()
                .title(title)
                .build();

        ResultActions response = mockMvc.perform(
                post("/thread/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());

        verify(threadService, times(1)).save(ArgumentMatchers.any(ThreadModel.class));
        verify(threadRepository, times(1)).save(ArgumentMatchers.any(ThreadModel.class));

    }

    @Test
    void create_throwsException_whenInvalidTitle() throws Exception{
        String title = "";

        CreateThreadModelRequestDTO request = CreateThreadModelRequestDTO.builder()
                .title(title)
                .build();

        ResultActions response = mockMvc.perform(
                post("/thread/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest());
        response.andExpect(result ->
                assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        verify(threadService, times(0)).save(ArgumentMatchers.any());
        verify(threadRepository, times(0)).save(ArgumentMatchers.any());

    }

    @Test
    void create_throwsException_whenNullTitle() throws Exception{
        String title = null;

        CreateThreadModelRequestDTO request = CreateThreadModelRequestDTO.builder()
                .title(title)
                .build();

        ResultActions response = mockMvc.perform(
                post("/thread/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest());
        response.andExpect(result ->
                assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        verify(threadService, times(0)).save(ArgumentMatchers.any());
        verify(threadRepository, times(0)).save(ArgumentMatchers.any());

    }

}
