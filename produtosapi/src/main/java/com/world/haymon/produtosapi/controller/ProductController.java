package com.world.haymon.produtosapi.controller;

import com.world.haymon.produtosapi.model.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @PostMapping
    public Product save(@RequestBody Product prod) {
        System.out.println("Produto salvo: " + prod);
        return prod;
    }
}
