package com.mysample.controller;

import com.mysample.error.UserNotFoundException;
import com.mysample.model.ApplicationUser;
import com.mysample.model.Role;
import com.mysample.repository.UserRepository;
import com.mysample.repository.RoleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author thiagofilgueira
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(userRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        Role role = roleRepository.findByName("ROLE_USER");
        if (role != null) user.getRoles().add(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @PostMapping
    public void create(@RequestBody ApplicationUser user) {
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role != null) user.getRoles().add(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable long id, @RequestBody ApplicationUser user) {
        ApplicationUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        existingUser.setUsername(user.getUsername());
        if (user.getPassword() != null) existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(existingUser);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}