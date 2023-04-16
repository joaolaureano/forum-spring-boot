package com.example.forum.repository;

import com.example.forum.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<PostModel, Integer> {
    List<PostModel> findAllByThreadModelIdOrderByCreatedAt(Integer id);
}
