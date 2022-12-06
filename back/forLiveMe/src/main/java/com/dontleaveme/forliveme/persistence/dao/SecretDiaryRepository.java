package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.SecretDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretDiaryRepository extends JpaRepository<SecretDiary, Long> {
    SecretDiary findBySdNum(Long sdNum);
}
