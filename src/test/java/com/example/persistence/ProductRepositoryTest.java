package com.colruyt.springdatajpademo;

import com.colruyt.springdatajpademo.entity.Category;
import com.colruyt.springdatajpademo.entity.Product;
import com.colruyt.springdatajpademo.entity.Tag;
import com.colruyt.springdatajpademo.repository.CategoryRepository;
import com.colruyt.springdatajpademo.repository.ProductRepository;
import com.colruyt.springdatajpademo.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    private Category electronicsCategory;
    private Tag saleTag;

    @BeforeEach
    void setUp() {
        electronicsCategory = categoryRepository.save(new Category("Electronics", "ELEC"));
        saleTag = tagRepository.save(new Tag("On Sale"));

        Product p1 = new Product("Smartphone", new BigDecimal("699.99"), 50, electronicsCategory);
        p1.getTags().add(saleTag);
        saleTag.getProducts().add(p1);

        Product p2 = new Product("Laptop", new BigDecimal("1299.99"), 20, electronicsCategory);

        productRepository.saveAll(List.of(p1, p2));
    }

    @Test
    void testFindByPriceLessThan() {
        List<Product> products = productRepository.findByPriceLessThan(new BigDecimal("1000.00"));
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Smartphone");
    }

    @Test
    void testFindProductsByTagName() {
        List<Product> products = productRepository.findProductsByTagName("On Sale");
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Smartphone");
    }

    @Test
    void testPaginationAndSorting() {
        Page<Product> page = productRepository.findByPriceLessThan(
                new BigDecimal("2000.00"), 
                PageRequest.of(0, 1, Sort.by("price").ascending())
        );
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Smartphone");
    }
}