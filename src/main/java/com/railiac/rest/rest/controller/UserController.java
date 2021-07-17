package com.railiac.rest.rest.controller;

import com.railiac.rest.database.model.User;
import com.railiac.rest.database.repository.UserRepository;
import com.railiac.rest.rest.exception.RailiacException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/fetch/sel_val")
    private User getUserBySelectorAndValidator(@RequestParam("selector") String selector, @RequestParam("validator") String validator) throws RailiacException {
        return this.userRepository.findBySelectorAndValidator(selector, validator ).orElseThrow(() -> new RailiacException("User not found for current criteria"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/fetch/ref_code")
    private User getUserByReferrerCode(@RequestParam("referrer_code") String referrrerCode) throws RailiacException {
        return this.userRepository.findByReferrerCode(referrrerCode).orElseThrow(() -> new RailiacException("User not found by ref code"));
    }
}
