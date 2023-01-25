package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.Terms;
import com.dontleaveme.forliveme.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms, String> {
    Terms findByEmail(String email);
}
