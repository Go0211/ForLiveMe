package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.SecretDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretDiaryRepository extends JpaRepository<SecretDiary, Long> {
    SecretDiary findBySdNum(Long sdNum);

    List<SecretDiary> findBySdTitleContaining(String keyword);

    Long countBySdUserEmail(String userEmail);

}
