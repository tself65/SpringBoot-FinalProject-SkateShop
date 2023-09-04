package skate.shop.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import skate.shop.entity.Category;
import skate.shop.entity.Product;

@Data
@NoArgsConstructor
public class CategoryData {
	
	private Long categoryId; 
	private String categoryName; 
	private Set<String> products = new HashSet<>(); 
	
	public CategoryData(Category category) {
		categoryId = category.getCategoryId();
		categoryName = category.getCategoryName();
		
		for(Product product : category.getProducts()) {
			products.add(product.getProductName()); 
		}
	}
}
