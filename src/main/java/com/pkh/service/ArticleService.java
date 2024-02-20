package com.pkh.service;

public interface ArticleService {

    Long readArticle(String articleId);

    Long collectArticle(String articleId, String userId);

    Long getArticleCollectCount(String articleId);

    Long likeArticle(String articleId, String userId);

    Long getArticleLikeCount(String articleId);
}
