package springmarket.shop.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmarket.shop.entities.Product;
import springmarket.shop.repositories.ProductRepository;
import springmarket.shop.soap.catalog.ProductDto;
import springmarket.shop.soap.catalog.ProductsList;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class SoapCatalogService {

    private List<ProductDto> productDtos;

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        this.productDtos = new ArrayList<>();
        for (Product product: productRepository.findAll()) {
            productDtos.add(productToProductDto(product));
        }
    }

    public ProductsList getAllProductsList() {
        ProductsList productsList = new ProductsList();
        productsList.getProduct().addAll(productDtos);
        return productsList;
    }

    public ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setTitle(product.getTitle());
        productDto.setCategory(product.getCategory().toString());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice().doubleValue());
        return productDto;
    }
}
