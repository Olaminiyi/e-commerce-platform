package product_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import product_service.baseService.BaseServiceImpl;
import product_service.dto.ProductRequest;
import product_service.dto.ProductResponse;
import product_service.exception.model.ProductServiceException;
import product_service.mapper.ProductMapper;
import product_service.model.Category;
import product_service.model.Product;
import product_service.model.Tag;
import product_service.repository.CategoryRepository;
import product_service.repository.ProductRepository;
import product_service.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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


    public Page <ProductResponse> getAllProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::toResponse);
    }

    public ProductResponse CreateProduct(ProductRequest request){
       Category category = categoryRepository.findById(request.getCategoryId())
               .orElseThrow(() -> new ProductServiceException("Category not found", HttpStatus.NOT_FOUND.value()));

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));

        Product newproduct = Product.builder()
                .name(request.getName())
                .specifications(request.getSpecifications())
                .category(category)
                .tags(tags)
                .price(request.getPrice())
                .description(request.getDescription())
                .available(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        productRepository.save(newproduct);
        return ProductMapper.toResponse(newproduct);
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
