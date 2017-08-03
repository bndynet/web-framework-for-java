package net.bndy.wf.modules.cms.services;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.modules.cms.models.*;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
}
