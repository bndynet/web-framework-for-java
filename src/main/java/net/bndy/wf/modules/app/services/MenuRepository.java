package net.bndy.wf.modules.app.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.bndy.wf.modules.app.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	@Query(value="select * from menu t where t.is_hidden=1", nativeQuery=true)
	List<Menu> GetUserMenus();
}
