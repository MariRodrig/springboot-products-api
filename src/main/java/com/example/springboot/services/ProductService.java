package com.example.springboot.services;

import com.example.springboot.dtos.ProductRecordDto;
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

    public Optional<ProductModel> getOneProduct(UUID id) {
        return productRepository.findById(id);
    }

    public Optional<ProductModel> updateProduct(UUID id, @Valid ProductRecordDto productRecordDto) {
        var product0 = productRepository.findById(id);
        if (product0.isEmpty()){
            return Optional.empty();
        }

        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);

        var updateProduct = productRepository.save(productModel);
        return Optional.of(updateProduct);
    }

    public boolean deleteProduct(UUID id){
        var product0 = productRepository.findById(id);

        if (product0.isEmpty()){
            return false;
        }
        var productModel = product0.get();
        productRepository.delete(productModel);
        return true;
    }

}
