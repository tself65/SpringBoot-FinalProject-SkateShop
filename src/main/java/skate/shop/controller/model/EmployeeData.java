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
public class EmployeeData {

	private Long employeeId; 
	private String employeeFirstName; 
	private String employeeLastName;
	private String employeeUserName; 
	private String employeePassword;
	private String employeeTitle;
	private Set<ProductInfo> products = new HashSet<>(); 
	
	public EmployeeData(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeeUserName = employee.getEmployeeUserName();
		employeePassword = employee.getEmployeePassword();
		employeeTitle = employee.getEmployeeTitle();
		
		for(Product product : employee.getProducts()) {
			products.add(new ProductInfo(product)); 
		}
	}
	
	@Data
	@NoArgsConstructor
	static public class ProductInfo {
		private Long productId; 
		private String productName; 
		private String size; 
		private BigDecimal price; 
		private String description; 
		private Integer quantity; 
		private Set<String> categories = new HashSet<>(); 
		
		public ProductInfo(Product product) {
			productId = product.getProductId(); 
			productName = product.getProductName();
			size = product.getSize();
			price = product.getPrice();
			description = product.getDescription();
			quantity = product.getQuantity();

			
			for(Category category : product.getCategories()) {
				categories.add(category.getCategoryName());
			}
		}	
	}
}
