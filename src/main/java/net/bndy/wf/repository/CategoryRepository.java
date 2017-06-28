package net.bndy.wf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.domain.Category;;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
