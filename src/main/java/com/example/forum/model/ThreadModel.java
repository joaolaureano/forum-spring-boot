package com.example.forum.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "thread")
public class ThreadModel {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public Integer id;

    @Size(min = 3, max = 255)
    public String title;


    public Boolean active = true;

    @CreatedDate
    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;


}
