package product_service.baseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import product_service.exception.model.ProductServiceException;
//import java.awt.print.Pageable;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID>{

    protected final JpaRepository<T, ID> repository;

    public BaseServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
       return repository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
      return  repository.findById(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T update(ID id, T entity) {
        if(!repository.existsById(id)){
            throw new ProductServiceException("Entity not found", HttpStatus.NOT_FOUND.value());
        }
        return repository.save(entity);
    }
}
