package springmarket.shop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product") //TODO попробовать убрать fetch cascade
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductImage> images;

    @Override
    public int hashCode() {
        String str = this.getId().toString();
        return Integer.parseInt(str);
    }

    @Override
    public boolean equals(Object object) {
        if((!(object instanceof Product)) || (object == null)){
            return false;
        }
        Product tempProduct = (Product) object;
        if(this.getId() == tempProduct.getId()){
            return true;
        }
        return false;
    }
}
