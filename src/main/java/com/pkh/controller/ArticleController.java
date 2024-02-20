package com.pkh.controller;

import com.pkh.bean.response.PikaResponse;
import com.pkh.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/article")
@RestController
public class ArticleController {

    @Resource
    ArticleService articleService;

    @PostMapping("/readArticle")
    public PikaResponse<Object> readArticle() {
        Long articleReadCount = articleService.readArticle("1001");
        Map<String, Object> res = new HashMap<>();
        if (articleReadCount > 0) {
            res.put("count", articleReadCount);
        } else {
            res.put("count", -1);
        }
        return new PikaResponse<>(res);
    }

    @PostMapping("/collectArticle")
    public PikaResponse<Object> collectArticle(String articleId, String userId) {
        Long articleCollectCount = articleService.collectArticle(articleId, userId);
        Map<String, Object> res = new HashMap<>();
        if (articleCollectCount > 0) {
            res.put("count", articleCollectCount);
        } else {
            res.put("count", -1);
        }
        return new PikaResponse<>(res);
    }

    @PostMapping("/getArticleCollectCount")
    public PikaResponse<Object> getArticleCollectCount(String articleId) {
        Long articleCollectCount = articleService.getArticleCollectCount(articleId);
        Map<String, Object> res = new HashMap<>();
        res.put("count", articleCollectCount);
        return new PikaResponse<>(res);
    }

    @PostMapping("/likeArticle")
    public PikaResponse<Object> likeArticle(String articleId, String userId) {
        Long articleLikeCount = articleService.likeArticle(articleId, userId);
        Map<String, Object> res = new HashMap<>();
        if (articleLikeCount > 0) {
            res.put("count", articleLikeCount);
        } else {
            res.put("count", -1);
        }
        return new PikaResponse<>(res);
    }

    @PostMapping("/getArticleLikeCount")
    public PikaResponse<Object> getArticleLikeCount(String articleId) {
        Long articleLikeCount = articleService.getArticleLikeCount(articleId);
        Map<String, Object> res = new HashMap<>();
        res.put("count", articleLikeCount);
        return new PikaResponse<>(res);
    }
}

