package net.bndy.wf.lib;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class _BaseApi<T extends _BaseEntity> {
	
	@Autowired
	_BaseService<T> service;
	
	@RequestMapping(method = RequestMethod.GET) 
	public List<T> get() {
		return this.service.getAll();
	}

	@RequestMapping(value="/", method = RequestMethod.GET) 
	public Page<T> get(@PageableDefault(value = 10, sort = { "lastUpdate" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return this.service.findAll(pageable);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public T get(@PathVariable(name="id") long id) {
		return this.service.get(id);
	}
	
	@RequestMapping(value= "/{id}", method = RequestMethod.PUT) 
	public T put(@PathVariable(name="id") long id, @RequestBody T entity) {
		entity.setId(id);
		return this.service.save(entity);
	}

	@RequestMapping(method = RequestMethod.POST) 
	public T post(@RequestBody T entity) {
		return this.service.save(entity);
	}

	@RequestMapping(method = RequestMethod.DELETE) 
	public void delete(@PathVariable(name="id") long id) {
		this.service.delete(id);
	}
}
