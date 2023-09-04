package skate.shop.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId; 
	
	private String employeeFirstName; 
	private String employeeLastName;
	private String employeeUserName; 
	private String employeePassword;
	private String employeeTitle; 
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "employee", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private Set<Product> products = new HashSet<>(); 
}
