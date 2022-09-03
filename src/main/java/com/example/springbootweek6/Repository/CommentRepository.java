package com.example.springbootweek6.Repository;

import com.example.springbootweek6.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
