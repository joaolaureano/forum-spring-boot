package com.example.forum.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "post")

public class PostModel {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public Integer id;

    @NotNull
    public String commentary;

    @NotNull
    @CreatedDate
    public LocalDateTime createdAt;

    @Min(value = 0)
    public Integer likeCounter;

    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    ThreadModel threadModel;
}
