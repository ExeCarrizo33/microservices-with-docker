package com.backend.products_service.controllers;

import com.backend.products_service.models.dtos.ProductRequest;
import com.backend.products_service.models.dtos.ProductResponse;
import com.backend.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createProduct(@RequestBody ProductRequest productRequest) {
        this.productService.addProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ProductResponse> getAllProducts(){
        return this.productService.getAllProducts();
    }

    

}
