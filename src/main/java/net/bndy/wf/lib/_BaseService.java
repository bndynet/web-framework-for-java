/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.service.MailService;

public class _BaseService<T> {

	protected Class<T> domainClass;

	@Autowired
	protected ApplicationConfig applicationConfig;
	@Autowired
	protected MailService mailService;
	@Autowired
	JpaRepository<T, Long> repo;

	@SuppressWarnings("unchecked")
	public _BaseService() {
		this.domainClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public List<T> getAll() {
		return repo.findAll();
	}
	
	public T get(long id) {
		return repo.findOne(id);
	}
	
	public boolean delete(long id) {
		repo.delete(id);
		return true;
	}
	
	public T save(T entity) {
		return repo.saveAndFlush(entity);
	}

	public Page<T> findAll(Pageable pageable) {
		return this.repo.findAll(pageable);
	}
}
