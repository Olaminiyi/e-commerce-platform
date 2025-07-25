package product_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product_service.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
