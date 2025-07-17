package product_service.service;
import org.springframework.stereotype.Service;
import product_service.baseService.BaseServiceImpl;
import product_service.model.Category;
import product_service.repository.CategoryRepository;

@Service
public class CategoryService extends BaseServiceImpl<Category, Long> {
    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }
}
