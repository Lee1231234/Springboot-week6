package com.example.springbootweek6.Repository;

import com.example.springbootweek6.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LikesRepository extends JpaRepository<Likes,Long> {


    Optional<Likes> findByMemberidAndPostid(Long memberid,Long postid);
}
