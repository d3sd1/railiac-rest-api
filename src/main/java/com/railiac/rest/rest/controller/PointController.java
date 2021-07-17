package com.railiac.rest.rest.controller;

import com.railiac.rest.database.model.PointTransaction;
import com.railiac.rest.database.model.User;
import com.railiac.rest.database.repository.PointTransactionRepository;
import com.railiac.rest.database.repository.UserRepository;
import com.railiac.rest.rest.exception.RailiacException;
import com.railiac.rest.rest.request.RequestUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    private final PointTransactionRepository pointTransactionRepository;
    private final RequestUser requestUser;
    private final UserRepository userRepository;

    public PointController(PointTransactionRepository pointTransactionRepository,
                           RequestUser requestUser,
                           UserRepository userRepository) {
        this.pointTransactionRepository = pointTransactionRepository;
        this.requestUser = requestUser;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/transactions")
    public List<PointTransaction> getTransactions() {
        return this.pointTransactionRepository.findAllByUser(this.requestUser.getUser());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add_points")
    public PointTransaction addRailiacPoints(@RequestParam("amount") Long amount, @RequestParam("user_id") Long userId, @RequestParam("description") String description) throws RailiacException {
        if (amount <= 0) {
            throw new RailiacException("Amount must be positive.");
        }
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RailiacException(String.format("User not found with id %d", userId)));
        user.setPoints(user.getPoints() + amount);
        this.userRepository.save(user);
        PointTransaction pointTransaction = PointTransaction.builder()
                .user(user)
                .amount(amount)
                .description(description)
                .build();

        return this.pointTransactionRepository.save(pointTransaction);
    }

}
