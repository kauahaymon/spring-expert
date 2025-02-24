package com.world.haymon.produtosapi.controller;

import com.world.haymon.produtosapi.model.Product;
import com.world.haymon.produtosapi.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product save(@RequestBody Product prod) {
        System.out.println("Produto salvo: " + prod);

        var id = UUID.randomUUID().toString(); // Generate Unique Codes
        prod.setId(id);

        productRepository.save(prod);
        return prod;
    }
}
