package product_service.service;

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
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private  final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, TagRepository tagRepository){

        super(productRepository);
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable){

        return null;
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

        Page<Product> products;

        if(category != null && tag != null) {
            products = productRepository.findByCategoryAndTagAndKeywordContainingIgnoreCase(categoryId, tagId, keyword, pageable);
        } else if (category != null) {
            products = productRepository.findByCategoryAndKeywordContainingIgnoreCase(categoryId, keyword, pageable);
        } else if (tag != null) {
            products = productRepository.findByTagAndKeywordContainingIgnoreCase(tagId,keyword,pageable);
        }else {
            products = productRepository.findByKeywordContainingIgnoreCase(keyword,pageable);
        }
        return null;
    }



}
