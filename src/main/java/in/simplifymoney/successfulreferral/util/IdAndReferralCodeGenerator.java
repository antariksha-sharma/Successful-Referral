package in.simplifymoney.successfulreferral.util;

import java.util.UUID;

public class IdAndReferralCodeGenerator {

    public static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateReferralCode() {
        return "REF" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
