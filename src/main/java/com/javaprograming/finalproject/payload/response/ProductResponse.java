package com.javaprograming.finalproject.payload.response;

import com.javaprograming.finalproject.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse extends Product {
    private String link;
    public ProductResponse(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setImage(product.getImage());
        this.link = "/product?id=" + product.getId();
    }
}
