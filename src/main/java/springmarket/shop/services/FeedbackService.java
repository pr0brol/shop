package springmarket.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springmarket.shop.entities.Feedback;
import springmarket.shop.entities.Product;
import springmarket.shop.repositories.FeedbackRepository;
import springmarket.shop.repositories.ProductRepository;

import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    @Autowired
    public void setProductRepository(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }


    public List<Feedback> findAll(){
        return feedbackRepository.findAll();
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).get();
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

}
