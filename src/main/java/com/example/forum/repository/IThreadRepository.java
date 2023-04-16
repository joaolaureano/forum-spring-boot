package com.example.forum.repository;

import com.example.forum.model.ThreadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IThreadRepository extends JpaRepository<ThreadModel, Integer> {
}
