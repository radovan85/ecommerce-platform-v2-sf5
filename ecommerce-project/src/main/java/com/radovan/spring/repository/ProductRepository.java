package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	@Modifying
	@Query(value = "delete from products where category_id = :categoryId",nativeQuery = true)
	void deleteAllByCategoryId(@Param("categoryId") Integer categoryId);

	
	@Query(value="select distinct p.* from products as p inner join product_categories as c on\n"
			+ "p.category_Id = c.id where p.product_name ilike CONCAT('%', :keyword, '%') or p.product_brand ilike CONCAT('%', :keyword, '%') or p.product_model ilike CONCAT('%', :keyword, '%') or c.name ilike CONCAT('%', :keyword, '%')",nativeQuery = true)
	List<ProductEntity> findAllByKeyword(@Param ("keyword") String keyword);
	
}

	
