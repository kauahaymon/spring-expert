package com.world.haymon.produtosapi.repository;

import com.world.haymon.produtosapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
