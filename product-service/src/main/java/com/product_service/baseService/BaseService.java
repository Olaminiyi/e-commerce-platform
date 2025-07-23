package product_service.baseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseService<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    Page<T> findAll(Pageable pageable);
    void deleteById(ID id);
    T update (ID id, T entity);
}
