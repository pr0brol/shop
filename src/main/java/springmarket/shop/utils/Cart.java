package springmarket.shop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import springmarket.shop.entities.Product;
import springmarket.shop.services.ProductService;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private ProductService productService;
    private Map<Product, Integer> cartProducts;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
        cartProducts = new HashMap<>();
    }

    public void add(Long id){
        Product product = productService.findById(id);
        System.out.println(cartProducts.containsKey(product));
        System.out.println(cartProducts);
        if(cartProducts.containsKey(product)){
            cartProducts.put(product, cartProducts.get(product) + 1);
        }else {
            cartProducts.put(product, 1);
        }
    }

    public Map<Product, Integer> allProductsInCart(){
        return cartProducts;
    }

}
