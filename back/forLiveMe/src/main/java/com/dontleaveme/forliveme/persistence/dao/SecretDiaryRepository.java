package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.SecretDiary;
import com.dontleaveme.forliveme.persistence.model.test.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretDiaryRepository extends JpaRepository<SecretDiary, Long> {
    SecretDiary findBySdNum(Long sdNum);

    List<SecretDiary> findByTitleContaining(String keyword);
}
