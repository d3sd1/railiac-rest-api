package com.railiac.rest.rest.controller;

import com.railiac.rest.database.model.User;
import com.railiac.rest.database.model.UserLogin;
import com.railiac.rest.database.repository.UserLoginRepository;
import com.railiac.rest.database.repository.UserRepository;
import com.railiac.rest.rest.dto.ErrorListDto;
import com.railiac.rest.rest.dto.UserLoginDto;
import com.railiac.rest.rest.dto.UserRegisterDto;
import com.railiac.rest.rest.exception.LanguageNotFoundException;
import com.railiac.rest.rest.exception.RailiacException;
import com.railiac.rest.util.JwtUtil;
import com.railiac.rest.util.LanguageUtil;
import com.railiac.rest.util.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/session")
public class LoginController {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LanguageUtil languageUtil;

    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserLoginRepository userLoginRepository, LanguageUtil languageUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userLoginRepository = userLoginRepository;
        this.languageUtil = languageUtil;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public UserLogin doLogin(@RequestBody UserLoginDto userLoginDto) {
        assert userLoginDto != null;
        Optional<User> userOptional = this.userRepository.findByEmail(userLoginDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorListDto.INVALID_LOGIN.toString());
        }

        User user = userOptional.get();
        if (!this.passwordEncoder.verifyPassword(userLoginDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorListDto.INVALID_LOGIN.toString());
        }
        if (user.isBlocked() || !user.isEnabled()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorListDto.USER_BLOCKED.toString());
        }
        this.invalidateUserSessions(user);
        String generatedJwt = this.jwtUtil.generate(user, userLoginDto.isRememberMe());


        return this.generateUserSession(user, generatedJwt);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/validate")
    public User doValidate(@RequestParam("confirm_code") String confirmCode) {
        Optional<User> userOptional = this.userRepository.findByEmailCode(confirmCode);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorListDto.INVALID_LOGIN.toString());
        }

        User user = userOptional.get();
        if(!user.getEmailCode().equals(confirmCode)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorListDto.INVALID_CONFIRM_CODE.toString());
        }
        user.setEnabled(true);
        user.setBlocked(false);
        this.userRepository.save(user);

        return user;
    }

    private void invalidateUserSessions(User user) {
        List<UserLogin> userLogins = this.userLoginRepository.findByUserAndEnabledIsTrue(user);
        for (UserLogin userLogin : userLogins) {
            userLogin.setEnabled(false);
            this.userLoginRepository.save(userLogin);
        }
    }

    private UserLogin generateUserSession(User user, String jwt) {
        return this.userLoginRepository.save(UserLogin.builder()
                .creationDate(LocalDateTime.now())
                .enabled(true)
                .jwt(jwt)
                .user(user)
                .build());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public User doRegister(@RequestBody UserRegisterDto userRegisterDto) throws RailiacException, LanguageNotFoundException {
        Optional<User> isUserRegistered =this.userRepository.findByEmail(userRegisterDto.getEmail());
        if(isUserRegistered.isPresent()) {
            throw new RailiacException("User email already picked.");
        }
        Random random = new Random();

        String confirmCode = random.ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(7)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


        return this.userRepository.save(
                User
                        .builder()
                        .email(userRegisterDto.getEmail())
                        .language(this.languageUtil.getDefaultLanguage())
                        .blocked(false)
                        .enabled(false)
                        .needsOnboard(true)
                        .referrerCode(userRegisterDto.getReferrercod())
                        .password(this.passwordEncoder.encodePassword(userRegisterDto.getPass()))
                        .name(userRegisterDto.getFirstname())
                        .surnames(userRegisterDto.getSurname())
                        .emailCode(confirmCode)
                        .points(0L)
                        .build()
        );
    }

}
