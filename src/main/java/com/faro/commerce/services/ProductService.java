package com.faro.commerce.services;

import com.faro.commerce.dto.ProductDTO;
import com.faro.commerce.entities.Product;
import com.faro.commerce.repositories.ProductRepository;
import com.faro.commerce.services.exceptions.DatabaseException;
import com.faro.commerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
           Product product = repository.findById(id).orElseThrow(
                   ()-> new ResourceNotFoundException("Recurso não Encontrado"));
           return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){
        Product entity = new Product();
        copyDTOToEntity(productDTO, entity);
        entity= repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
       try{
           Product entity = repository.getReferenceById(id);
           copyDTOToEntity(productDTO, entity);
           entity= repository.save(entity);
           return new ProductDTO(entity);
       } catch (EntityNotFoundException e){
           throw new ResourceNotFoundException("Recurso Não Encontrado");
       }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
       try {
           repository.deleteById(id);
       } catch (EmptyResultDataAccessException e){
           throw new ResourceNotFoundException("Recurso Não Encontrado");
       } catch (DataIntegrityViolationException e ){
            throw new DatabaseException("Falha de integridade Referencial");
       }
    }

    private void copyDTOToEntity(ProductDTO productDTO, Product entity){
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
    }
}
