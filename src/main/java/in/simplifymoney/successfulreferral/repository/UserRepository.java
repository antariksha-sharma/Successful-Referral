package in.simplifymoney.successfulreferral.repository;

import in.simplifymoney.successfulreferral.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByReferralCode(String referralCode);

    Optional<User> findByUserIdOrEmail(String userId, String email);
}
