package product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import product_service.baseService.BaseServiceImpl;
import product_service.exception.model.ProductServiceException;
import product_service.model.Category;
import product_service.model.Product;
import product_service.model.Tag;
import product_service.repository.CategoryRepository;
import product_service.repository.ProductRepository;
import product_service.repository.TagRepository;

@Service
public class ProductService extends BaseServiceImpl<Product, Long> {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final TagRepository tagRepository;
    @Autowired
  //  private  final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, TagRepository tagRepository){

        super(productRepository);
      //  this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public Page<Product> search (Long categoryId, Long tagId, String keyword, Pageable pageable){
        Category category = null;
        Tag tag = null;

        if(keyword == null || keyword.trim().isEmpty()){
            keyword = "";
        }

        if (categoryId != null){
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ProductServiceException("Category not found", HttpStatus.NOT_FOUND.value()));
        }

        if (tagId != null){
            tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new ProductServiceException("Tag not found", HttpStatus.NOT_FOUND.value()));
        }

        if(category != null && tag != null) {
            return productRepository
        }

    }

}
