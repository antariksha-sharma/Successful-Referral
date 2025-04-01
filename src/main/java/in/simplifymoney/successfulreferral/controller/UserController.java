package in.simplifymoney.successfulreferral.controller;

import in.simplifymoney.successfulreferral.model.User;
import in.simplifymoney.successfulreferral.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{idOrEmail}")
    public ResponseEntity<User> getUserByIdOrEmail(@PathVariable String idOrEmail) {
        return ResponseEntity.ok(userService.getUserByIdOrEmail(idOrEmail));
    }
}
