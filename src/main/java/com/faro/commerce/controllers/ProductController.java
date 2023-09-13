package com.faro.commerce.controllers;

import com.faro.commerce.dto.ProductDTO;
import com.faro.commerce.dto.ProductMinDTO;
import com.faro.commerce.entities.Product;
import com.faro.commerce.repositories.ProductRepository;
import com.faro.commerce.services.ProductService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
       ProductDTO dto = service.findById(id);
       return ResponseEntity.ok(dto);
    }
// ajustar código
    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(Pageable pageable){
        Page<ProductMinDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }





    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
