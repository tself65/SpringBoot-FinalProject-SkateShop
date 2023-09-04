package skate.shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skate.shop.controller.model.CategoryData;
import skate.shop.controller.model.EmployeeData;
import skate.shop.controller.model.ProductData;
import skate.shop.service.SkateShopService;


@RestController
@RequestMapping("/skate_shop")
@Slf4j
public class SkateShopController {

	@Autowired
	private SkateShopService skateShopService; 
	
	@PostMapping("/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData insertEmployee(@RequestBody EmployeeData employeeData) {
		log.info("Creating new Employee {}");
		return skateShopService.saveEmployee(employeeData); 
	}
	
	@PutMapping("/employee/{employeeId}")
	public EmployeeData updateEmployee(@PathVariable Long employeeId, 
			@RequestBody EmployeeData employeeData) {
		employeeData.setEmployeeId(employeeId);
		log.info("Updating employee {}", employeeData); 
		return skateShopService.saveEmployee(employeeData); 
	}
	
	
	@PostMapping("/employee/{employeeId}/product")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProductData insertProduct(@PathVariable Long employeeId, @RequestBody ProductData productData) {
		log.info("Creating new Product {} for employee with ID={}", productData, employeeId); 
		return skateShopService.saveProduct(employeeId, productData); 
	}
	
	@PutMapping("/employee/{employeeId}/product/{productId}")
	public ProductData updateProduct(@PathVariable Long employeeId,
			@PathVariable Long productId,
			@RequestBody ProductData productData) {
		productData.setProductId(productId);
		log.info("Updating product {} by employee with ID={}", productData, employeeId); 
		return skateShopService.saveProduct(employeeId, productData); 
	}
	
	@GetMapping("/product")
	public List<ProductData> retrieveAllProducts() {
		log.info("Retrieving all products.");
		return skateShopService.retrieveAllProducts(); 
	}
	
	@GetMapping("/product/{productId}")
	public ProductData retrieveProductById(@PathVariable Long productId) {
		log.info("Retrieving product with ID={}", productId);
		return skateShopService.retrieveProductById(productId); 
	}
	
	@DeleteMapping("/product")
	public void deleteAllProducts() {
		log.info("Attempting to delete all products...");
		throw new UnsupportedOperationException("Deleting all products is not allowed."); 
	}
	
	@DeleteMapping("/product/{productId}")
	public Map<String, String> deleteProductById(@PathVariable Long productId) {
		log.info("Deleting product with ID={}", productId);
		
		skateShopService.deleteProductById(productId); 
		
		return Map.of("message", "Deletion of product with ID=" + 
		productId + " was successful."); 
	}
	
	@PostMapping("/category")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CategoryData insertCategory(@RequestBody CategoryData categoryData) {
		return skateShopService.saveCategory(categoryData); 
	}

}
