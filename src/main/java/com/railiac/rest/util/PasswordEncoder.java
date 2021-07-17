package com.railiac.rest.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordEncoder {
    private final byte passwordStrength = 10;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoder() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(this.passwordStrength, new SecureRandom());
    }

    public String encodePassword(String inputPassword) {
        return bCryptPasswordEncoder.encode(inputPassword);
    }

    public boolean verifyPassword(String inputPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(inputPassword, encodedPassword);
    }

}
