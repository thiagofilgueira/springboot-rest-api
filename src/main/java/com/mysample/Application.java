package com.mysample;

import com.mysample.repository.UserRepository;
import com.mysample.repository.RoleRepository;
import com.mysample.security.DefaultSystemAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

/**
 * @author thiagofilgueira
 */
@SpringBootApplication
public class Application {

	@Autowired
	Environment environment;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Pre-load the system with user and roles.
	 */
	public @PostConstruct
	void init() {
		DefaultSystemAccount.bootstrapDefaultSystemUserAndRoles(
				userRepository,
				roleRepository,
				bCryptPasswordEncoder,
				environment
		);
	}

}

