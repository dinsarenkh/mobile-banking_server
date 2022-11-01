package com.dinsaren.mobilebankingserver.controllers.rest;

import com.dinsaren.mobilebankingserver.constants.Constants;
import com.dinsaren.mobilebankingserver.models.Category;
import com.dinsaren.mobilebankingserver.models.res.CategoryRes;
import com.dinsaren.mobilebankingserver.payload.response.MessageRes;
import com.dinsaren.mobilebankingserver.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/category")
@Slf4j
@PreAuthorize("hasRole('USER') or hasRole('CUSTOMER') or hasRole('ADMIN')")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private MessageRes messageRes;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<MessageRes> getList() {
        messageRes = new MessageRes();
        CategoryRes categoryRes = new CategoryRes();
        try {
            log.info("Intercept get all category");
            List<Category> menus = categoryRepository.findAllByCategoryCodeAndStatusOrderByIndexAsc(
                    Constants.MENU,
                    Constants.ACTIVE_STATUS);
            List<Category> promotions = categoryRepository.findAllByCategoryCodeAndStatusOrderByIndexAsc(
                    Constants.PROMOTION,
                    Constants.ACTIVE_STATUS);
            List<Category> applications = categoryRepository.findAllByCategoryCodeAndStatusOrderByIndexAsc(
                    Constants.APPLICATION,
                    Constants.ACTIVE_STATUS);
            categoryRes.setMenuList(menus);
            categoryRes.setApplicationList(applications);
            categoryRes.setPromotionList(promotions);
            messageRes.setMessageSuccess(categoryRes);
        } catch (Throwable e) {
            log.info("While error get all category ", e);
        } finally {
            log.info("Final get info response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageRes> getList(@PathVariable("id") Integer id) {
        messageRes = new MessageRes();
        try {
            log.info("Intercept get all category");
            Category category = categoryRepository.findByIdAndStatus(id, Constants.ACTIVE_STATUS);
            messageRes.setMessageSuccess(category);
        } catch (Throwable e) {
            log.info("While error get all category ", e);
        } finally {
            log.info("Final get info response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageRes> create(@RequestBody Category req) {
        messageRes = new MessageRes();
        try {
            log.info("Intercept create category {}", req);
            req.setStatus(Constants.ACTIVE_STATUS);
            req.setId(0);
            categoryRepository.save(req);
            messageRes.setMessageSuccess("Create Success");
        } catch (Throwable e) {
            log.info("While error create category ", e);
        } finally {
            log.info("Final create category response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageRes> update(@RequestBody Category req) {
        messageRes = new MessageRes();
        try {

            if (checkRequestBody(req)) return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);

            log.info("Intercept update category {}", req);
            req.setStatus(Constants.ACTIVE_STATUS);
            categoryRepository.save(req);
            messageRes.setMessageSuccess("update Success");
        } catch (Throwable e) {
            log.info("While error update category ", e);
        } finally {
            log.info("Final update category response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<MessageRes> delete(@RequestBody Category req) {
        messageRes = new MessageRes();
        try {
            if (checkRequestBody(req)) return new ResponseEntity<>(messageRes, HttpStatus.BAD_REQUEST);
            log.info("Intercept delete category {}", req);
            req.setStatus(Constants.ACTIVE_STATUS);
            categoryRepository.save(req);
            messageRes.setMessageSuccess("delete Success");
        } catch (Throwable e) {
            log.info("While error delete category ", e);
        } finally {
            log.info("Final delete category response {}", messageRes);
        }
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    private boolean checkRequestBody(@RequestBody Category req) {
        if (req.getId() < 0 || req.getName().equals("") || null == req.getName()) {
            messageRes.badRequest("Error: Invalid request!");
            return true;
        }
        Category category = categoryRepository.findByIdAndStatus(req.getId(), Constants.ACTIVE_STATUS);
        if (null == category) {
            messageRes.badRequest("Error: Data not found!");
            return true;
        }
        return false;
    }


}
