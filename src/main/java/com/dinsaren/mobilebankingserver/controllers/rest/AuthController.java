package com.dinsaren.mobilebankingserver.controllers.rest;

import com.dinsaren.mobilebankingserver.constants.Constants;
import com.dinsaren.mobilebankingserver.exception.TokenRefreshException;
import com.dinsaren.mobilebankingserver.models.*;
import com.dinsaren.mobilebankingserver.payload.request.LogOutReq;
import com.dinsaren.mobilebankingserver.payload.request.LoginReq;
import com.dinsaren.mobilebankingserver.payload.request.RegisterReq;
import com.dinsaren.mobilebankingserver.payload.request.TokenRefreshReq;
import com.dinsaren.mobilebankingserver.payload.response.JwtRes;
import com.dinsaren.mobilebankingserver.payload.response.MessageRes;
import com.dinsaren.mobilebankingserver.payload.response.TokenRefreshRes;
import com.dinsaren.mobilebankingserver.repository.OtpLogRepository;
import com.dinsaren.mobilebankingserver.repository.RoleRepository;
import com.dinsaren.mobilebankingserver.repository.UserAccountRepository;
import com.dinsaren.mobilebankingserver.repository.UserRepository;
import com.dinsaren.mobilebankingserver.security.jwt.JwtUtils;
import com.dinsaren.mobilebankingserver.security.services.RefreshTokenService;
import com.dinsaren.mobilebankingserver.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/oauth")
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final OtpLogRepository otpLogRepository;
    private final UserAccountRepository userAccountRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, RefreshTokenService refreshTokenService, OtpLogRepository otpLogRepository, UserAccountRepository userAccountRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.otpLogRepository = otpLogRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginReq req) {
        log.info("Intercept request oath token {}", req);
        MessageRes messageRes = new MessageRes();
        if (req.getPassword().equals("") ||
                null == req.getPassword() ||
                "".equals(req.getPassword())
                || req.getPhoneNumber().equals("") || null == req.getPhoneNumber()) {
            messageRes.badRequest("Error: Invalid username and password");
            return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getPhoneNumber(), req.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            JwtRes jwtRes = new JwtRes();
            jwtRes.setExpiresIn(86400000);
            jwtRes.setAccessToken(jwt);
            jwtRes.setRefreshToken(refreshToken.getToken());
            jwtRes.setTokenType("bearer");

            return ResponseEntity.ok(jwtRes);
        } finally {
            log.info("While get error request oath token final result {}", messageRes);
        }

    }

    @PostMapping("/open/account")
    public ResponseEntity<MessageRes> registerUser(@RequestBody RegisterReq req) {
        MessageRes messageRes = new MessageRes();
        log.info("Intercept open account req {}", req);
        try {
            if (req.getFirstName().equals("") || req.getLastName().equals("") || req.getPhoneNumber().equals("") || req.getEmail().equals("") || req.getPassword().equals("") || null == req.getPassword() || null == req.getConfirmPassword() || "".equals(req.getPassword()) || "".equals(req.getConfirmPassword()) || req.getNationalId().equals("") || req.getDob().equals("")) {
                messageRes.badRequest("Error: Username is already taken!");
                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            }
            if (!req.getConfirmPassword().equals(req.getPassword())) {
                messageRes.badRequest("Error: Confirm password not match!");
                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            }
            if (userRepository.existsByUsername(req.getUsername())) {
                messageRes.badRequest("Error: Username is already taken!");
                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(req.getEmail())) {
                messageRes.badRequest("Error: Error: Email is already in use!");
                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByPhoneNumber(req.getPhoneNumber())) {
                messageRes.badRequest("Error: Phone is already in use!");
                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByNationalId(req.getNationalId())) {
                messageRes.badRequest("Error: National ID is already in use!");
                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            }

            User user = new User(req.getUsername(), req.getEmail(), encoder.encode(req.getPassword()), req.getPhoneNumber());
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(UserRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            String userAccountId = UUID.randomUUID().toString();
            roles.add(userRole);
            user.setRoles(roles);
            user.setDob(req.getDob());
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setNationalId(req.getNationalId());
            user.setUserAccountId(userAccountId);
            user.setStatus(Constants.ACTIVE_STATUS);
            userRepository.save(user);
            UserAccount userAccount = new UserAccount();
            userAccount.setAccountCcy("USD");
            int int_random = ThreadLocalRandom.current().nextInt();
            String accountNumber = "" + int_random;
            userAccount.setAccountNumber(accountNumber.replace("-", ""));
            userAccount.setCreateAt(new Date());
            userAccount.setCreateBy(userAccountId);
            userAccount.setUserAccountId(userAccountId);
            userAccount.setStatus(Constants.ACTIVE_STATUS);
            userAccount.setAccountDefault("Y");
            userAccount.setTotalBalance(10000.00);
            userAccountRepository.save(userAccount);
            messageRes.setMessageSuccess("User Open Account successfully!");
            return new ResponseEntity<>(messageRes, HttpStatus.OK);
        } catch (Throwable e) {
            log.info("Error open account req ", e);
            messageRes.internalServerError(null);
            return new ResponseEntity<>(messageRes, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            log.info("Open account req final result {}", messageRes);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshReq request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser).map(user -> {
            String token = jwtUtils.generateTokenFromUsername(user.getUsername());
            return ResponseEntity.ok(new TokenRefreshRes(token, requestRefreshToken));
        }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogOutReq req) {
        refreshTokenService.deleteByUserId(req.getUserId());
        return ResponseEntity.ok(new MessageRes("Log out successful!", null));
    }

    public static void main(String[] args) {
        System.out.println(generateAccountNumber());
    }

    private static String generateAccountNumber() {
        String accountNumber = "";
        try {
            Random rand = new Random();
            String card = "00";
            for (int i = 0; i < 10; i++) {
                int n = rand.nextInt(6) + 0;
                card += Integer.toString(n);
            }
            for (int i = 0; i < 12; i++) {
                if (i % 4 == 0)
                    System.out.print("");
                System.out.print(card.charAt(i));
                accountNumber = "" + card.charAt(i);
            }
        } catch (Exception e) {
            accountNumber = "0000000034";
        }


        return accountNumber;
    }

}
