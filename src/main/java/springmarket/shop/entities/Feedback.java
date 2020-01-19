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

    @Column(name = "prod_id", insertable = false, updatable = false)
    private Long prod_id;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "evaluation")
    private int evaluation;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "prod_id", referencedColumnName = "id")
    private Product product;

    public Feedback(Long user_id, Long prod_id, String feedback, int evaluation, Product product, User user) {
        this.user_id = user_id;
        this.prod_id = prod_id;
        this.feedback = feedback;
        this.evaluation = evaluation;
        this.product = product;
        this.user = user;
    }
}
