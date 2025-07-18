package product_service.service;
import org.springframework.stereotype.Service;
import product_service.baseService.BaseServiceImpl;
import product_service.model.Product;
import product_service.repository.ProductRepository;

@Service
public class ProductService extends BaseServiceImpl<Product, Long> {

    public ProductService(ProductRepository productRepository){
        super(productRepository);
    }
}
