package product_service.service;

import org.springframework.stereotype.Service;
import product_service.baseService.BaseServiceImpl;
import product_service.model.Review;
import product_service.repository.ReviewRepository;

@Service
public class ReviewService extends BaseServiceImpl<Review, Long> {

    public ReviewService(ReviewRepository reviewRepository){
        super(reviewRepository);
    }
}
