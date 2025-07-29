package product_service.mapper;
import product_service.dto.ProductRequest;
import product_service.dto.ProductResponse;
import product_service.model.Category;
import product_service.model.Product;
import product_service.model.Tag;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {
    public  static ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .available(product.isAvailable())
                .description(product.getDescription())
                .categoryName(product.getCategory().getName())
                .specifications(product.getSpecifications())
                .tags(product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .build();
    }

    public  static Product toEntity(ProductRequest request, Category category, Set<Tag> tags){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .specifications(request.getSpecifications())
                .price(request.getPrice())
                .category(category)
                .tags(tags)
                .build();
    }
}
