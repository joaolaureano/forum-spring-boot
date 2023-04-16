package com.example.forum.controller.dto.request;

import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostModelRequestDTO {

    String title;
    Integer threadId;

    public static PostModel toModel(CreatePostModelRequestDTO createPostModelRequestDTO){
        return PostModel
                .builder()
                .threadModel(ThreadModel.builder().id(createPostModelRequestDTO.threadId).build())
                .likeCounter(0)
                .commentary(createPostModelRequestDTO.title)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
