package com.example.forum.service;

import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.exception.ExceptionEnum;
import com.example.forum.model.ThreadModel;
import com.example.forum.repository.IThreadRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ThreadServiceTest {

    @InjectMocks
    private ThreadService threadService;

    @Mock
    private IThreadRepository threadRepository;


    @Test
    void save_persistNewEntity_whenSuccess(){


        LocalDateTime createdAt = LocalDateTime.now();
        Integer id = 1;
        String title = "Test Title";

        ThreadModel threadModel = ThreadModel.builder()
                .id(id)
                .createdAt(createdAt)
                .title(title)
                .build();

        threadService.save(threadModel);

        verify(threadRepository, times(1)).save(threadModel);

    }

    @Test
    void findById_shouldReturnEntity_whenSuccess(){
        LocalDateTime createdAt = LocalDateTime.now();
        Integer id = 1;
        String title = "Test Title";

        ThreadModel threadModel = ThreadModel.builder()
                .id(id)
                .createdAt(createdAt)
                .title(title)
                .active(true)
                .build();


        when(threadRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(threadModel));

        ThreadModel returnTest = threadService.findById(1);


        assertEquals(id, returnTest.getId());
        assertEquals(title, returnTest.getTitle());
        assertEquals(createdAt, returnTest.getCreatedAt());
        assertNull(returnTest.getUpdatedAt());
        assertTrue(returnTest.getActive());

        verify(threadRepository, times(1)).findById(ArgumentMatchers.anyInt());

    }

    @Test
    void findById_shouldThrowsEntityNotFoundExcepiton_whenEntityNotFound(){
        String expectedMessage = String.format(ExceptionEnum.ENTITY_NOT_FOUND.toString(), ThreadModel.class.getSimpleName());

        when(threadRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        EntityNotFoundException thrownException = assertThrows(EntityNotFoundException.class,
                () -> threadService.findById(ArgumentMatchers.anyInt()));

        assertEquals(thrownException.getMessage(), expectedMessage);
        verify(threadRepository, times(1)).findById(ArgumentMatchers.anyInt());

    }

    @Test
    void findAll_shouldReturnListOfEntity_whenSuccess(){
        final Integer LIST_SIZE = 3;

        List<ThreadModel> threadModelList = new ArrayList<>();

        threadModelList.add(new ThreadModel());
        threadModelList.add(new ThreadModel());
        threadModelList.add(new ThreadModel());

        when(threadRepository.findAll()).thenReturn(threadModelList);

        List<ThreadModel> responseList =threadService.findAll();

        verify(threadRepository, times(1)).findAll();
        assertEquals(responseList.size(), LIST_SIZE);

    }


}
