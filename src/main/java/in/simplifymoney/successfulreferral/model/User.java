package in.simplifymoney.successfulreferral.model;

import in.simplifymoney.successfulreferral.enums.Gender;
import in.simplifymoney.successfulreferral.enums.ReferredStatus;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "referred_status")
    @Enumerated(EnumType.STRING)
    private ReferredStatus referredStatus;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
