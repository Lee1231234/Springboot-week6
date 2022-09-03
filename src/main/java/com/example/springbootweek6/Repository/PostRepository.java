package com.example.springbootweek6.Repository;

import com.example.springbootweek6.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
