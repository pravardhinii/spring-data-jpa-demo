package com.colruyt.springdatajpademo.repository;

import com.colruyt.springdatajpademo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Keyword Query Derivations
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByCategoryNameIgnoreCase(String categoryName);
    List<Product> findByStockQuantityGreaterThanEqual(Integer stock);

    // 2. Pagination & Sorting variants via keyword derivation
    Page<Product> findByPriceLessThan(BigDecimal maxPrice, Pageable pageable);
    List<Product> findByStockQuantityGreaterThan(Integer minStock, Sort sort);

    // 3. Custom JPQL Queries
    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t.name = :tagName")
    List<Product> findProductsByTagName(@Param("tagName") String tagName);

    // 4. Native SQL Queries
    @Query(value = "SELECT p.name, SUM(oi.quantity) AS total_sold " +
                   "FROM order_items oi JOIN products p ON oi.product_id = p.id " +
                   "GROUP BY p.id, p.name ORDER BY total_sold DESC LIMIT :limit", 
           nativeQuery = true)
    List<Object[]> findTopSellingProductsNative(@Param("limit") int limit);
}