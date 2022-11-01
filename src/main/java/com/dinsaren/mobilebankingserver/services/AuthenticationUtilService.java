package com.dinsaren.mobilebankingserver.services;

import com.dinsaren.mobilebankingserver.constants.Constants;
import com.dinsaren.mobilebankingserver.models.User;
import com.dinsaren.mobilebankingserver.repository.UserRepository;
import com.dinsaren.mobilebankingserver.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationUtilService {
    private final UserRepository userRepository;

    public AuthenticationUtilService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User checkUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<User> user = userRepository.findByPhoneNumberAndStatus(userDetails.getPhoneNumber(), Constants.ACTIVE_STATUS);
            if (user.isPresent()) {
                return user.get();
            }
        }
        return null;
    }

}
