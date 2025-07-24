package product_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import product_service.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
