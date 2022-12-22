package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.EmpathyBoard;
import com.dontleaveme.forliveme.persistence.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    Letter findByLeNum(Long leNum);

    List<Letter> findByLeContentContaining(String keyword);

    Long countByLeUserEmail(String userEmail);
}
