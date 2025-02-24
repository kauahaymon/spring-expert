package com.world.haymon.produtosapi.controller;

import com.world.haymon.produtosapi.model.Product;
import com.world.haymon.produtosapi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
        var id = UUID.randomUUID().toString(); // Generate Unique Codes
        prod.setId(id);

        productRepository.save(prod);
        return prod;
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") String id) {
        return productRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id,
                       @RequestBody Product product) {
        product.setId(id);
        productRepository.save(product);
    }
}
