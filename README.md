# E-Commerce Order & Product Inventory Tracker (`spring-data-jpa-demo`)

A data-layer Spring Boot 3 application built on Java 17 that persists products, categories, tags, and customer orders to an H2 relational database using Hibernate and Spring Data JPA.

## Key Technical Concepts Demonstrated
- **JPA / Hibernate Entity Mapping:** Annotated domain entities (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`) modeling an e-commerce schema.
- **Relational Mappings:**
  - `@OneToMany` & `@ManyToOne`: Bidirectional relationships between `Category` $\leftrightarrow$ `Product` and `CustomerOrder` $\leftrightarrow$ `OrderItem`.
  - `@ManyToMany`: Join-table association between `Product` and `Tag` (`product_tags`).
- **Spring Data Repositories (`JpaRepository`):**
  - **Keyword Query Derivation:** Method-name queries such as `findByPriceLessThan`, `findByCategoryNameIgnoreCase`, and `findByStockQuantityGreaterThanEqual`.
  - **Custom JPQL Queries (`@Query`):** Object-oriented entity queries using `JOIN` clauses (`findProductsByTagName`).
  - **Native SQL Queries (`nativeQuery = true`):** Raw database aggregation queries for reporting (`findTopSellingProductsNative`).
  - **Pagination & Sorting:** Dynamic paging and ordering using `Pageable`, `PageRequest`, `Page`, and `Sort`.
- **Persistence Layer Testing:** Slice integration tests using `@DataJpaTest`, JUnit 5, and AssertJ.

## 🛠️ Tech Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.2.5 (Spring Data JPA)
- **ORM:** Hibernate
- **Database:** H2 In-Memory Database
- **Build Tool:** Apache Maven
- **Testing:** JUnit 5 & AssertJ

