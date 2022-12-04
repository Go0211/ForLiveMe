package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    Optional<PersistentLogin> findBySeries(final String series);

    List<PersistentLogin> findByUsername(final String username);

}

