package com.colruyt.springdatajpademo;

import com.colruyt.springdatajpademo.entity.Category;
import com.colruyt.springdatajpademo.entity.CustomerOrder;
import com.colruyt.springdatajpademo.entity.OrderItem;
import com.colruyt.springdatajpademo.entity.Product;
import com.colruyt.springdatajpademo.repository.CategoryRepository;
import com.colruyt.springdatajpademo.repository.CustomerOrderRepository;
import com.colruyt.springdatajpademo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerOrderRepositoryTest {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveAndFindOrderWithItems() {
        Category category = categoryRepository.save(new Category("Books", "BK"));
        Product book = productRepository.save(new Product("Spring Boot in Action", new BigDecimal("45.00"), 100, category));

        CustomerOrder order = new CustomerOrder("COMPLETED", new BigDecimal("90.00"));
        OrderItem item = new OrderItem(2, new BigDecimal("45.00"), book);
        order.addOrderItem(item);

        CustomerOrder savedOrder = customerOrderRepository.save(order);

        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getOrderItems()).hasSize(1);

        List<CustomerOrder> completedOrders = customerOrderRepository.findByStatus("COMPLETED");
        assertThat(completedOrders).hasSize(1);
    }
}