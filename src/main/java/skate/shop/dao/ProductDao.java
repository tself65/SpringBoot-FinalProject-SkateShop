package skate.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import skate.shop.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

}
