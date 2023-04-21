package com.example.forum.controller;

import com.example.forum.controller.dto.request.CreateThreadModelRequestDTO;
import com.example.forum.exception.EntityNotFoundException;
import com.example.forum.model.ThreadModel;
import com.example.forum.service.IThreadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/thread")
public class ThreadController {

    private final IThreadService threadService;

    ThreadController(IThreadService threadService){
        this.threadService = threadService;
    }

    @GetMapping(value = "/{id}" )
    public ResponseEntity<ThreadModel> getById(
            @PathVariable(value = "id", required = true) Integer id
    ) throws EntityNotFoundException{
        ThreadModel threadModel = threadService.findById(id);

        return new ResponseEntity<>(threadModel, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ThreadModel>> getAll(

    ) {
        List<ThreadModel> threadModelList = threadService.findAll();

        return new ResponseEntity<>(threadModelList, HttpStatus.OK);
    }

    @PostMapping

    public ResponseEntity<Void> create(@Valid @RequestBody CreateThreadModelRequestDTO createThreadDTO){
        ThreadModel threadModel = CreateThreadModelRequestDTO.toModel(createThreadDTO);
        this.threadService.save(threadModel);

        return new ResponseEntity<>( HttpStatus.CREATED);
    }
}
