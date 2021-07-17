package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.Language;
import com.railiac.rest.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByKeyNameIs(String keyName);
}