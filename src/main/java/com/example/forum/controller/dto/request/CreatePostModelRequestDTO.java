package com.example.forum.controller.dto.request;

import com.example.forum.model.PostModel;
import com.example.forum.model.ThreadModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostModelRequestDTO {

    @Size(min= 3, max = 255 )
    @NotNull
    String commentary;

    @NotNull
    Integer threadId;

    public static PostModel toModel(CreatePostModelRequestDTO createPostModelRequestDTO){
        return PostModel
                .builder()
                .threadModel(ThreadModel.builder().id(createPostModelRequestDTO.threadId).build())
                .likeCounter(0)
                .commentary(createPostModelRequestDTO.commentary)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
