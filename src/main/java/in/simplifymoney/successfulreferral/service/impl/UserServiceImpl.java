package in.simplifymoney.successfulreferral.service.impl;

import in.simplifymoney.successfulreferral.model.User;
import in.simplifymoney.successfulreferral.repository.UserRepository;
import in.simplifymoney.successfulreferral.service.UserService;
import in.simplifymoney.successfulreferral.util.IdAndReferralCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        user.setUserId(IdAndReferralCodeGenerator.generateId());
        user.setReferralCode(IdAndReferralCodeGenerator.generateReferralCode());

        if(user.getReferredBy() != null) {
            User userWhoReferred = userRepository.findByReferralCode(user.getReferredBy()).orElseThrow(() -> new RuntimeException("User not found"));
            userWhoReferred.setTotalReferrals(userWhoReferred.getTotalReferrals() + 1);
            List<String> referredUsers = userWhoReferred.getReferredUsers();
            referredUsers.add(user.getUserId());
            userWhoReferred.setReferredUsers(referredUsers);
            userRepository.save(userWhoReferred);
        }

        return userRepository.save(user);
    }
}
