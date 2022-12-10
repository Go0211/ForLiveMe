package com.dontleaveme.forliveme.persistence.dao.test;

import com.dontleaveme.forliveme.persistence.model.test.Board;
import com.dontleaveme.forliveme.persistence.model.test.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleContaining(String keyword);

    Board findByUser(User user);
}