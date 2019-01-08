package com.mysample.security;

import com.mysample.model.ApplicationUser;
import com.mysample.model.Role;
import com.mysample.repository.RoleRepository;
import com.mysample.repository.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thiagofilgueira
 */
public class DefaultSystemAccount {

    public static void bootstrapDefaultSystemUserAndRoles(
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            Environment env
    ) {
        ApplicationUser applicationUser = userRepository.findByUsername(env.getProperty("app.system.user.username"));
        if (applicationUser == null) {
            List<Role> roles = new ArrayList<>();
            Role role_admin = roleRepository.findByName("ROLE_"+SecurityConstants.SUFFIX_NAME_ROLE_ADMIN);
            if (role_admin == null) roles.add(new Role("ROLE_"+SecurityConstants.SUFFIX_NAME_ROLE_ADMIN));
            else roles.add(role_admin);
            Role role_user = roleRepository.findByName("ROLE_"+SecurityConstants.SUFFIX_NAME_ROLE_USER);
            if (role_user == null) roles.add(new Role("ROLE_"+SecurityConstants.SUFFIX_NAME_ROLE_USER));
            else roles.add(role_user);
            userRepository.save(new ApplicationUser(env.getProperty("app.system.user.username"),
                    bCryptPasswordEncoder.encode(env.getProperty("app.system.user.password")), roles));
        }
        SecurityContextHolder.clearContext();
    }

}