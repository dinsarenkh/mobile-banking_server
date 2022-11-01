package com.dinsaren.mobilebankingserver.controllers.rest;

import com.dinsaren.mobilebankingserver.constants.Constants;
import com.dinsaren.mobilebankingserver.models.User;
import com.dinsaren.mobilebankingserver.models.UserAccount;
import com.dinsaren.mobilebankingserver.models.req.UserInfoReq;
import com.dinsaren.mobilebankingserver.models.res.UserInfoRes;
import com.dinsaren.mobilebankingserver.payload.response.MessageRes;
import com.dinsaren.mobilebankingserver.repository.UserAccountRepository;
import com.dinsaren.mobilebankingserver.repository.UserRepository;
import com.dinsaren.mobilebankingserver.services.AuthenticationUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/user")
@Slf4j
@PreAuthorize("hasRole('USER') or hasRole('CUSTOMER') or hasRole('ADMIN')")
public class UserController {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private MessageRes messageRes;
    private final AuthenticationUtilService authenticationUtilService;
    public UserController(UserRepository userRepository, UserAccountRepository userAccountRepository, AuthenticationUtilService authenticationUtilService) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
        this.authenticationUtilService = authenticationUtilService;
    }
    @PostMapping("/info")
    public ResponseEntity<MessageRes> getUserLoginSession(@RequestBody UserInfoReq req){
        messageRes = new MessageRes();
        UserInfoRes userInfoRes = new UserInfoRes();
        try{
            User user = authenticationUtilService.checkUser();
            log.info("Intercept get user info req {}", req);
            if(null != user){
                userInfoRes.setUser(user);
                List<UserAccount> userAccount = userAccountRepository.findAllByUserAccountIdAndStatus(user.getUserAccountId(), Constants.ACTIVE_STATUS);
                if(null != userAccount){
                    userInfoRes.setUserAccounts(userAccount);
                }
                user.setPassword("***********");
                messageRes.setMessageSuccess(userInfoRes);
            }

        }catch (Throwable e){
            log.info("While error get user info ", e);
        }finally {
            log.info("Final get info response {}", userInfoRes);
        }
        return  new ResponseEntity<>(messageRes, HttpStatus.OK);
    }
}
