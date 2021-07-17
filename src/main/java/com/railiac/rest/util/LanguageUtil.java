package com.railiac.rest.util;

import com.railiac.rest.database.model.Language;
import com.railiac.rest.database.model.User;
import com.railiac.rest.database.repository.LanguageRepository;
import com.railiac.rest.rest.exception.LanguageNotFoundException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class LanguageUtil {

    private LanguageRepository languageRepository;

    public LanguageUtil(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language getDefaultLanguage() throws LanguageNotFoundException {
        return languageRepository.findByKeyNameIs("en".toUpperCase())
                .orElseThrow(() -> new LanguageNotFoundException(String.format("Could not find language %s","en")));
    }
}
