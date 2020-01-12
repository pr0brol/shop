package springmarket.shop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@NoArgsConstructor
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "prod_id")
    private Long prod_id;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "evaluation")
    private int evaluation;

//    @ManyToOne
//    @JoinColumn(name = "prod_id")
//    private Product product;

    public Feedback(Long prod_id, String feedback, int evaluation) {
        this.prod_id = prod_id;
        this.feedback = feedback;
        this.evaluation = evaluation;
    }
}
