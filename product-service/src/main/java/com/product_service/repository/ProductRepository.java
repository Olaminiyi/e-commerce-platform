package product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import product_service.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryAndTagAndNameContainingIgnoreCase (Long categoryId, Long tagId, String keyword, Pageable pageable);
    Page<Product> findByCategoryAndNameContainingIgnoreCase (Long categoryId, String keyword, Pageable pageable);
    Page<Product> findByTagAndNameContainingIgnoreCase (Long tagId, String keyword, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase (String keyword, Pageable pageable);
}
