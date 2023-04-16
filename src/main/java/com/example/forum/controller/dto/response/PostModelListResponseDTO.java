package com.example.forum.controller.dto.response;

import com.example.forum.model.PostModel;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostModelListResponseDTO {

    List<PostModelInformation> postModelList;
    Integer threadId;

    public static PostModelListResponseDTO toResponse(List<PostModel> postModelList){
        if(postModelList.isEmpty())
            return null;
        return PostModelListResponseDTO.builder()
                .postModelList(PostModelInformation.toList(postModelList))
                .threadId(postModelList.get(0).getThreadModel().id)
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class PostModelInformation {


        public String commentary;

        public LocalDateTime createdAt;

        public Integer likeCounter;

        private static List<PostModelInformation> toList(List<PostModel> postModelList){
            return postModelList.stream().map(postModel ->
                PostModelInformation
                        .builder()
                        .commentary(postModel.getCommentary())
                        .createdAt(postModel.getCreatedAt())
                        .likeCounter(postModel.getLikeCounter())
                        .build()
            ).collect(Collectors.toList());

        }
    }

}
