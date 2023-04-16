package com.example.forum.controller.dto.request;

import com.example.forum.model.ThreadModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateThreadModelRequestDTO {

    String title;

    public static ThreadModel toModel(CreateThreadModelRequestDTO createThreadModelRequestDTO){
        return ThreadModel
                .builder()
                .title(createThreadModelRequestDTO.title)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
    }
}
