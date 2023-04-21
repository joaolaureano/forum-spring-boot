package com.example.forum.service;

import com.example.forum.exception.ExceptionEnum;
import com.example.forum.exception.MappedEntityNotFoundException;
import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IPostRepository;
import com.example.forum.repository.IThreadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private IPostRepository postRepository;

    @Mock
    private IThreadRepository threadRepository;



    @Test
    void save_persistNewEntity_whenSuccess(){


        LocalDateTime createdAt = LocalDateTime.now();
        Integer id = 1;
        String commentary = "Test Commentary";

        ThreadModel threadModel = ThreadModel.builder().build();

        PostModel postModel = PostModel.builder()
                .id(id)
                .createdAt(createdAt)
                .commentary(commentary)
                .threadModel(threadModel)
                .likeCounter(0)
                .build();

        when(threadRepository.findById(threadModel.getId())).thenReturn(Optional.of(threadModel));

        postService.save(postModel);

        verify(postRepository, times(1)).save(postModel);
        verify(threadRepository, times(1)).findById(threadModel.getId());

    }

    @Test
    void findByThread_shouldReturnEntity_whenSuccess(){

        LocalDateTime createdAt = LocalDateTime.now();
        Integer id = 1;
        String commentary = "Test Commentary";

        ThreadModel threadModel = ThreadModel.builder().id(1).build();

        PostModel postModel = PostModel.builder()
                .id(id)
                .createdAt(createdAt)
                .commentary(commentary)
                .threadModel(threadModel)
                .likeCounter(0)
                .build();

        List<PostModel> expectedList = new ArrayList<>();
        expectedList.add(postModel);

        when(postRepository.findAllByThreadModelIdOrderByCreatedAt(ArgumentMatchers.anyInt()))
                .thenReturn(expectedList);

        List<PostModel> responseList = postService.findByThread(threadModel);

        assertEquals(1, responseList.size());
        verify(postRepository, times(1)).findAllByThreadModelIdOrderByCreatedAt(ArgumentMatchers.anyInt());

    }

    @Test
    void findById_shouldThrowsEntityNotFoundException_whenEntityNotFound() {
        String postModelClassname = PostModel.class.getSimpleName();
        String threadModelClassname = ThreadModel.class.getSimpleName();
        PostModel postModel = PostModel.builder().threadModel(ThreadModel.builder().id(1).build()).build();
        Integer threadModelId = postModel.getThreadModel().getId();
        String expectedMessage = String.format(ExceptionEnum.MAPPED_ENTITY_NOT_FOUND.toString(),postModelClassname, threadModelClassname, threadModelId);

        when(threadRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        MappedEntityNotFoundException thrownException = assertThrows(MappedEntityNotFoundException.class,
                () -> postService.save(postModel));

        assertEquals(thrownException.getMessage(), expectedMessage);
        verify(threadRepository, times(1)).findById(ArgumentMatchers.anyInt());
        verify(postRepository, times(0)).save(new PostModel());

    }

}
