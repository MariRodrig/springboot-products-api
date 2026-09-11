package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(
        name = "Products",
        description = "Operations for creating, retrieving, updating, and deleting products"
)
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Creates and stores a new product in the system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            )
    })
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productRecordeDto));
    }


    @Operation(
            summary = "List all products",
            description = "Returns a paginated list of all registered products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully"
            )
    })
    @GetMapping("/products")
    public ResponseEntity<Page> getAllProducts(@ParameterObject Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(pageable));
    }


    @Operation(
            summary = "Find a product by ID",
            description = "Returns a product according to the provided ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        var product0 = productService.getOneProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(product0);
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product according to the provided ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        var product0 = productService.updateProduct(id, productRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(product0);
    }


    @Operation(
            summary = "Delete a product",
            description = "Deletes an existing product according to the provided ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product delete successfully.");
    }
}

