package springmarket.shop.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import springmarket.shop.entities.Product;
import springmarket.shop.repositories.specifications.ProductSpecification;

import java.util.Map;

@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (map.containsKey("min_price") && !map.get("min_price").isEmpty()) {
            int minPrice = Integer.parseInt(map.get("min_price"));
            spec = spec.and(ProductSpecification.priceGreaterThanOrEq(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if (map.containsKey("max_price") && !map.get("max_price").isEmpty()) {
            int maxPrice = Integer.parseInt(map.get("max_price"));
            spec = spec.and(ProductSpecification.priceLesserThanOrEq(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
        if (map.containsKey("category") && !map.get("category").isEmpty()) {
            Long catId = Long.parseLong(map.get("category"));
            spec = spec.and(ProductSpecification.categoryIdEquals(catId));
            filterDefinition.append("&category=").append(catId);
        }
    }
}
