package in.simplifymoney.successfulreferral.service;

import in.simplifymoney.successfulreferral.model.User;

public interface UserService {
    User saveUser(User user);

    User getUserByIdOrEmail(String idOrEmail);
}
