package product_service.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ProductServiceException extends RuntimeException{

    private final int status;

    public ProductServiceException(String message, int status){
        super(message);
        this.status = status;
    }

}
