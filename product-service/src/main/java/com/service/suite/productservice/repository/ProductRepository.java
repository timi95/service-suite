package com.service.suite.productservice.repository;

import com.service.suite.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String > {
}
