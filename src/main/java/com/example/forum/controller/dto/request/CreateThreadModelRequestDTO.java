package com.example.forum.controller.dto.request;

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
public class CreateThreadModelRequestDTO {

    @Size(min = 3, max = 255)
    @NotNull
    private String title;

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
