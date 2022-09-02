package com.example.springbootweek6.Repository;

import com.example.springbootweek6.domain.Member;
import com.example.springbootweek6.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMember(Member member);
}
