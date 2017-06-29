package net.bndy.wf.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.domain.Category;
import net.bndy.wf.repository.CategoryRepository;;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepo;

	@RequestMapping(method = RequestMethod.GET)
	public List<Category> get() {
		return categoryRepo.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Category get(@PathVariable long id) {
		return categoryRepo.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Category addCategory(@RequestBody Category category) {
		return categoryRepo.saveAndFlush(category);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Category saveCategory(@RequestBody Category category) {
		return categoryRepo.saveAndFlush(category);
	}
}
