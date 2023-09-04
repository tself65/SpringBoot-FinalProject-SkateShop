package skate.shop.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import skate.shop.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

	Set<Category> findAllByCategoryNameIn(Set<String> categories);

}
