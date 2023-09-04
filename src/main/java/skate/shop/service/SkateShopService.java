package skate.shop.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import skate.shop.controller.model.CategoryData;
import skate.shop.controller.model.EmployeeData;
import skate.shop.controller.model.ProductData;
import skate.shop.dao.CategoryDao;
import skate.shop.dao.EmployeeDao;
import skate.shop.dao.ProductDao;
import skate.shop.entity.Category;
import skate.shop.entity.Employee;
import skate.shop.entity.Product;

@Service
public class SkateShopService {

	@Autowired
	private ProductDao productDao; 
	
	@Autowired
	private EmployeeDao employeeDao; 
	
	@Autowired
	private CategoryDao categoryDao;
	

	
	public EmployeeData saveEmployee(EmployeeData employeeData) {
		Long employeeId = employeeData.getEmployeeId(); 
		Employee employee = findOrCreateEmployee(employeeId, employeeData.getEmployeeUserName());  
		
		copyEmployeeFields(employee, employeeData); 
		return new EmployeeData(employeeDao.save(employee)); 
	} 
	
	private void copyEmployeeFields(Employee employee, EmployeeData employeeData) {
		employee.setEmployeeFirstName(employeeData.getEmployeeFirstName());
		employee.setEmployeeLastName(employeeData.getEmployeeLastName());
		employee.setEmployeeUserName(employeeData.getEmployeeUserName());
		employee.setEmployeePassword(employeeData.getEmployeePassword());
		employee.setEmployeeTitle(employeeData.getEmployeeTitle());
		
	}

	private Employee findOrCreateEmployee(Long employeeId, String employeeUserName) {
		Employee employee; 
		
		if(Objects.isNull(employeeId)) {
			   
			 Optional<Employee> optionalEmployee = employeeDao.findByEmployeeUserName(employeeUserName); 
			 
			 if(optionalEmployee.isPresent()) {
				 
				 throw new DuplicateKeyException("Employee with UserName " + employeeUserName + " already exists.");
			 }
			 
			   employee = new Employee(); 
		   }
		   else {
			   employee = findEmployeeById(employeeId); 
		   }
		   
		   return employee; 
		
		
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow( () -> new NoSuchElementException(
						"Employee with ID=" + employeeId + " was not found."));
	}

	@Transactional(readOnly = false)
	public ProductData saveProduct(Long employeeId, ProductData productData) {
		Employee employee = findEmployeeById(employeeId); 
		Long productId = productData.getProductId(); 
		Set<Category> categories = categoryDao.findAllByCategoryNameIn(productData.getCategories());
		
		Product product = findOrCreateProduct(productId); 
		
		
		copyProductFields(product, productData); 
		
		product.setEmployee(employee);
		employee.getProducts().add(product); 
		
		for(Category category : categories) {
			category.getProducts().add(product); 
			product.getCategories().add(category); 
			
		}
		
		return new ProductData(productDao.save(product)); 
	}


	private void copyProductFields(Product product, ProductData productData) {
		product.setProductName(productData.getProductName());
		product.setSize(productData.getSize());
		product.setPrice(productData.getPrice());
		product.setDescription(productData.getDescription());
		product.setQuantity(productData.getQuantity());
		
	}

	private Product findOrCreateProduct(Long productId) {
		Product product; 
		
		if(Objects.isNull(productId)) {
			product = new Product(); 
		}
		else {
			product = findProductById(productId); 
		}
		return product;
	}

	private Product findProductById(Long productId) {
		return productDao.findById(productId)
				.orElseThrow( () -> new NoSuchElementException(
						"Product with ID=" + productId + " was not found."));
	}

	public CategoryData saveCategory(CategoryData categoryData) {
		Long categoryId = categoryData.getCategoryId();
		Category category = findOrCreateCategory(categoryId); 
		
		copyCategoryFields(category, categoryData); 
		return new CategoryData(categoryDao.save(category)); 
	}

	private void copyCategoryFields(Category category, CategoryData categoryData) {
		
		category.setCategoryName(categoryData.getCategoryName());
		
	}

	private Category findOrCreateCategory(Long categoryId) {
		Category category; 
		
		if(Objects.isNull(categoryId)) {
			category = new Category(); 
		}
		else {
			category = findCategoryById(categoryId); 
		}
		return category;
	}

	private Category findCategoryById(Long categoryId) {
		return categoryDao.findById(categoryId)
				.orElseThrow( () -> new NoSuchElementException(
						"Category with ID=" + categoryId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<ProductData> retrieveAllProducts() {
		//@formatter:off
		return productDao.findAll().stream().map(cont -> new ProductData(cont)).toList();
		//@formatter:on 
	}

	@Transactional(readOnly = true)
	public ProductData retrieveProductById(Long productId) {
		Product product = findProductById(productId); 
		return new ProductData(product);
	}

	@Transactional(readOnly = false)
	public void deleteProductById(Long productId) {
		Product product = findProductById(productId); 
		productDao.delete(product);
		
	}

	
}
