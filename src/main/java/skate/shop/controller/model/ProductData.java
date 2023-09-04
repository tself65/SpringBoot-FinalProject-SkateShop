package skate.shop.controller.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import skate.shop.entity.Category;
import skate.shop.entity.Employee;
import skate.shop.entity.Product;

@Data
@NoArgsConstructor
public class ProductData {


	private Long productId; 
	private String productName; 
	private String size; 
	private BigDecimal price; 
	private String description; 
	private Integer quantity; 
	private ProductEmployee employee; 
	private Set<String> categories = new HashSet<>(); 
	
	public ProductData(Product product) {
		productId = product.getProductId(); 
		productName = product.getProductName();
		size = product.getSize();
		price = product.getPrice();
		description = product.getDescription();
		quantity = product.getQuantity();
		employee = new ProductEmployee(product.getEmployee()); 
		
		for(Category category : product.getCategories()) {
			categories.add(category.getCategoryName());
		}
	}
	
	
	
	@Data
	@NoArgsConstructor
	public static class ProductEmployee {
		private Long employeeId; 
		private String employeeFirstName; 
		private String employeeLastName;
		private String employeeTitle;
		
		public ProductEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeeTitle = employee.getEmployeeTitle(); 
		}
	}
}
