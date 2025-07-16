package product_service.exception.handler;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import product_service.exception.model.ExceptionDetails;
import product_service.exception.model.ProductServiceException;
//import org.springframework.context.MessageSourceAware;


@ControllerAdvice
public class ProductServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<Object> handleProductServiceException (ProductServiceException e){
        ExceptionDetails details = ExceptionDetails.builder()
                .message(e.getMessage())
                .status(e.getStatus())
                .build();
        return  new ResponseEntity<>(details, HttpStatusCode.valueOf(e.getStatus()));
    }
}
