package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.EmpathyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpathyBoardRepository extends JpaRepository<EmpathyBoard, Long> {
    EmpathyBoard findByEbNum(Long ebNum);

    List<EmpathyBoard> findByEbTitleContaining(String keyword);

    Long countByEbUserEmail(String userEmail);
}
