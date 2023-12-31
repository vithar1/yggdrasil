package com.dev.yggdrasil.repos;

import com.dev.yggdrasil.domain.Article;
import com.dev.yggdrasil.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
