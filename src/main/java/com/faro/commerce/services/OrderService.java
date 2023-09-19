package com.faro.commerce.services;

import com.faro.commerce.dto.OrderDTO;
import com.faro.commerce.dto.ProductDTO;
import com.faro.commerce.entities.Order;
import com.faro.commerce.entities.Product;
import com.faro.commerce.repositories.OrderRepository;
import com.faro.commerce.repositories.ProductRepository;
import com.faro.commerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Pedido não Encontrado"));
        return new OrderDTO(order);
    }
}
