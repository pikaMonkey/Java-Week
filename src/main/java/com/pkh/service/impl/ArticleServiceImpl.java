package com.pkh.service.impl;

import com.pkh.service.ArticleService;
import com.pkh.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    RedisUtil redisUtil;

    @Override
    public Long readArticle(String articleId) {
        String key = this.generateArticleReadKey(articleId);
        return redisUtil.incur(key);
    }

    @Override
    public Long collectArticle(String articleId, String userId) {
        String key = this.generateArticleCollectKey(articleId);
        return redisUtil.AddSet(key, userId);
    }

    @Override
    public Long likeArticle(String articleId, String userId) {
        String key = this.generateArticleLikeKey(articleId);
        return redisUtil.AddSet(key, userId);
    }

    @Override
    public Long getArticleLikeCount(String articleId) {
        String key = this.generateArticleLikeKey(articleId);
        return redisUtil.CountSet(key);
    }

    @Override
    public Long getArticleCollectCount(String articleId) {
        String key = this.generateArticleCollectKey(articleId);
        return redisUtil.CountSet(key);
    }

    /**
     * 文章阅读量计数，生成对应Redis的Key
     *
     * @param articleId 文章Id
     * @return
     */
    private String generateArticleReadKey(String articleId) {
        return "article:read:count:" + articleId;
    }

    /**
     * 文章收藏记录，生成对应Redis的Key
     * @param articleId 文章Id
     * @return
     */
    private String generateArticleCollectKey(String articleId) {
        return "article:collect:user:count:" + articleId;
    }

    /**
     * 文章点赞记录，生成对应Redis的Key
     * @param articleId
     * @return
     */
    private String generateArticleLikeKey(String articleId) {
        return "article:like:user:count:" + articleId;
    }

}
