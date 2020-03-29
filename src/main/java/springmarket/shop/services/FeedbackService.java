package springmarket.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmarket.shop.entities.Feedback;
import springmarket.shop.repositories.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    @Autowired
    public void setFeedbackRepository(FeedbackRepository feedbackRepository) {
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

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);  //TODO выполняется, но не работает
        return;
    }

    public Long findFeedbackByProductId(Long id) {
        return feedbackRepository.findId(id);
    }
}
