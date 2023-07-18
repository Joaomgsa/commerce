package com.faro.commerce.controllers;

import com.faro.commerce.dto.ProductDTO;
import com.faro.commerce.entities.Product;
import com.faro.commerce.repositories.ProductRepository;
import com.faro.commerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id){
       ProductDTO dto = service.findById(id);
       return dto;
    }

}
