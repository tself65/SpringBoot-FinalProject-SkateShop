package skate.shop.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import skate.shop.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmployeeUserName(String employeeUserName);

}
