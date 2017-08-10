package net.bndy.wf.lib;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public class _BaseService<T> {

	@Autowired
	JpaRepository<T, Long> repo;
	
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
