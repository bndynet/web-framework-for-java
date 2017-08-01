package net.bndy.wf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.domain.*;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
}
