package in.simplifymoney.successfulreferral.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true, name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(unique = true, name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="profile_completed")
    private boolean profileCompleted;

    @Column(name="total_referrals")
    private int totalReferrals;

    @ElementCollection
    @Column(name = "referred_users")
    private List<String> referredUsers;

    @Column(unique = true, updatable = false, name = "referral_code")
    private String referralCode;

    @Column(name = "referred_by")
    private String referredBy;
}
