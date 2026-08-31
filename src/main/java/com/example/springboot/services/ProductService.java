package com.example.springboot.services;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel saveProduct(ProductRecordDto productRecordeDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordeDto, productModel);
        return productRepository.save(productModel);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getOneProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    public ProductModel updateProduct(UUID id, @Valid ProductRecordDto productRecordDto) {
        var product0 = productRepository.findById(id);
        if (product0.isEmpty()) {
            throw new ResourceNotFoundException("Product not found!");
        }

        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);

        var updateProduct = productRepository.save(productModel);
        return updateProduct;
    }

    public void deleteProduct(UUID id) {
        var product0 = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        productRepository.delete(product0);
    }

}
