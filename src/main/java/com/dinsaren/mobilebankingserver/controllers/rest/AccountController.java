package com.dinsaren.mobilebankingserver.controllers.rest;

import com.dinsaren.mobilebankingserver.constants.Constants;
import com.dinsaren.mobilebankingserver.models.User;
import com.dinsaren.mobilebankingserver.models.UserAccount;
import com.dinsaren.mobilebankingserver.models.req.UserInfoReq;
import com.dinsaren.mobilebankingserver.payload.response.MessageRes;
import com.dinsaren.mobilebankingserver.repository.UserAccountRepository;
import com.dinsaren.mobilebankingserver.services.AuthenticationUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/account")
@Slf4j
@PreAuthorize("hasRole('USER') or hasRole('CUSTOMER') or hasRole('ADMIN')")
public class AccountController {
    private final UserAccountRepository userAccountRepository;
    private MessageRes messageRes;
    private final AuthenticationUtilService authenticationUtilService;

    public AccountController(UserAccountRepository userAccountRepository, AuthenticationUtilService authenticationUtilService) {
        this.userAccountRepository = userAccountRepository;
        this.authenticationUtilService = authenticationUtilService;
    }

    @PostMapping("/list")
    public ResponseEntity<MessageRes> getUserLoginSession(@RequestBody UserInfoReq req) {
        messageRes = new MessageRes();
        List<UserAccount> userAccount = new ArrayList<>();
        try {
            User user = authenticationUtilService.checkUser();
            log.info("Intercept get user info req {}", req);
            if (null != user) {
                userAccount = userAccountRepository.findAllByUserAccountIdAndStatus(user.getUserAccountId(), Constants.ACTIVE_STATUS);
                messageRes.setMessageSuccess(userAccount);
            }

        } catch (Throwable e) {
            log.info("While error get user info ", e);
        } finally {
            log.info("Final get info response {}", userAccount);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @GetMapping("/{account_no}")
    public ResponseEntity<MessageRes> getAccountByAccountNo(@PathVariable("account_no") String accountNo) {
        messageRes = new MessageRes();
        try {
            UserAccount userAccount = userAccountRepository.findByAccountNumberAndStatus(accountNo, Constants.ACTIVE_STATUS);
            messageRes.setMessageSuccess(userAccount);
        } catch (Throwable e) {
            log.info("While error get user info ", e);
        } finally {
            log.info("Final get info response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageRes> create(@RequestBody UserAccount req) {
        messageRes = new MessageRes();
        try {
//            if (!req.getAccountType().equals(Constants.TERM_DEPOSIT_ACCOUNT) || !req.getAccountType().equals(Constants.SAVING_ACCOUNT)) {
//                messageRes.setMessageSuccess("Invalid Account Type");
//                return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
//            }
            log.info("Intercept get user info req {}", req);
            User user = authenticationUtilService.checkUser();
            req.setAccountCcy("USD");
            int int_random = ThreadLocalRandom.current().nextInt();
            String accountNumber = "" + int_random;
            req.setAccountNumber(accountNumber.replace("-",""));
            req.setCreateAt(new Date());
            req.setAccountType(Constants.SAVING_ACCOUNT);
            req.setCreateBy(user.getUserAccountId());
            req.setUserAccountId(user.getUserAccountId());
            req.setStatus(Constants.ACTIVE_STATUS);
            req.setAccountDefault("N");
            req.setTotalBalance(10000.00);
            userAccountRepository.save(req);
            messageRes.setMessageSuccess(null);
        } catch (Throwable e) {
            log.info("While error get user info ", e);
        } finally {
            log.info("Final get info response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    private static String generateAccountNumber() {
        String cardNumber = "";
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
                card.charAt(i);
                cardNumber = card;
            }


        } catch (Exception e) {
            return "0000000034";
        }
        return cardNumber;

    }

    public static void main(String[] args) {
        int int_random = ThreadLocalRandom.current().nextInt();
        System.out.println( "" + int_random+ "".replace("-",""));
    }

}
